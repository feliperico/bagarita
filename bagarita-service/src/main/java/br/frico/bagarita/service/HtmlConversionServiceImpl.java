package br.frico.bagarita.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.math.CTOMath;
import org.docx4j.math.CTOMathPara;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import uk.ac.ed.ph.snuggletex.*;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Felipe Rico on 8/18/2016.
 */
public class HtmlConversionServiceImpl {
    private static final Logger LOG = LogManager.getLogger();

    private static final String CONFIG_FILE = "services-config.properties";
    private static final String DEFAULT_ENCODING_CONF_KEY = "HTML_INPUT_PROCESSOR.DEFAUL_ENCODING";
    private static final String REPLACER_PREFIX_CONF_KEY = "HTML_INPUT_PROCESSOR.REPLACER_PREFIX";
    private static final String MATH_XSLT_CONF_KEY = "HTML_INPUT_PROCESSOR.MATH_XSLT";
    private static final String BASE_URI_CONF_KEY = "HTML_INPUT_PROCESSOR.BASE_URI";
    private static final String MATHML_ENTITIES_PROPERTIES = "mathml-entities.properties";

    private String defaultEncoding = "UTF-8";
    private String replacerPrefix = "#BAG-";
    private Transformer _transformer;
    private String mathML2OpenMathmlXSLTFile = "MML2OMML.XSL";
    private String baseURI = "";
    private Properties _mathMLEntities;

    /**
     * Inicializa o serviço com dados parametrizados se os mesmos existirem.
     */
    @PostConstruct
    public void init() {
        InputStream confStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE);
        if (confStream != null) {
            Properties config = new Properties();
            try {
                config.load(confStream);
                defaultEncoding = config.getProperty(DEFAULT_ENCODING_CONF_KEY, defaultEncoding);
                replacerPrefix = config.getProperty(REPLACER_PREFIX_CONF_KEY, replacerPrefix);
                mathML2OpenMathmlXSLTFile = config.getProperty(MATH_XSLT_CONF_KEY, mathML2OpenMathmlXSLTFile);
                baseURI = config.getProperty(BASE_URI_CONF_KEY, baseURI);
            } catch (IOException e) {
                LOG.error(String.format("Error while trying to load config file '%s'", CONFIG_FILE), e);
            }

        }
    }

    public void addHTMLInput2Docx(String htmlInput, WordprocessingMLPackage wordMLPackage) {
        if (StringUtils.isNotBlank(htmlInput)) {
            // Parsea o input HTML
            Document htmlDoc = Jsoup.parse(htmlInput, baseURI);
            Elements mathElems = htmlDoc.select(".math-tex");
            boolean hasMath = !mathElems.isEmpty();
            Map<String, String> mathMap = null;

            // tem equação ?
            if (hasMath) {
                mathMap = new HashMap<String, String>();
                // substituir as equações do input por replacers (identificar via
                // replacer se é uma equação inline ou parágrafo)
                for (Element mathElement : mathElems) {
                    String mathExp = mathElement.text();
                    boolean inlineMath = !mathElement.text().equals(mathElement.parent().text());
                    String replacer = generateUniqueReplacer(inlineMath);
                    // Passa tex para mathML
                    mathExp = laTEXFormulaToMathML(mathExp);
                    // parsear as equações em MathML para OMML (crítico)
                    mathExp = mathMLToOMath(mathExp);
                    // armazenar as equações num formato chave-valor sendo a chave o
                    // replacer utilizado
                    mathMap.put(replacer, mathExp);
                    mathElement.text(replacer);
                }
            }

            // parsear o html para OXML (crítico)
            String OOXML = null;
            try {
                XHTMLImporter xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
                List<Object> parsedHTML = xhtmlImporter.convert(StringEscapeUtils.unescapeHtml4(htmlDoc.html()),
                        baseURI);
                MainDocumentPart mainDoc = wordMLPackage.getMainDocumentPart();
                mainDoc.getContent().addAll(parsedHTML);

                // tem equação?
                if (hasMath) {
                    // substituir as equações em OMML no OXML
                    replaceMathOnMainDocument(mainDoc.getContent(), mathMap);
                }
            } catch (Docx4JException e) {
                LOG.error(e.getMessage(), e);
                throw new RuntimeException("Error while parsing HTML input to OfficeOpenXML format.", e);
            }

        }
    }

    /**
     * Substitui os marcadores de fórmula matemática para a própria fórmula no
     * conteúdo do documento docx.
     *
     * @param mainDocContent
     *            conteúdo principal do documento
     * @param mathMap
     *            mapa contendo as fórmulas matemáticas e seus marcadores
     *            correspondentes.
     */
    @SuppressWarnings("restriction")
    private void replaceMathOnMainDocument(List<Object> mainDocContent, Map<String, String> mathMap) {
        org.docx4j.math.ObjectFactory mathFactory = new org.docx4j.math.ObjectFactory();

        for (Object elem : mainDocContent) {
            if (elem instanceof P) {
                P paragraph = (P) elem;
                Map<String, R> replacements = new HashMap<String, R>();
                for (Object pElem : paragraph.getContent()) {
                    if (pElem instanceof R) {
                        R run = (R) pElem;
                        for (Object rElem : run.getContent()) {
                            if (rElem instanceof Text) {
                                String value = ((Text) rElem).getValue();
                                if (StringUtils.isNotBlank(value) && value.startsWith(replacerPrefix)) {
                                    replacements.put(value, run);
                                }
                            }
                        }
                    }
                }
                // Faz as substituições
                if (!replacements.isEmpty()) {
                    for (Map.Entry<String, R> entry : replacements.entrySet()) {
                        String math = mathMap.get(entry.getKey());
                        Object oMath;
                        try {
                            oMath = XmlUtils.unmarshalString(math, Context.jc, CTOMath.class);
                            Object replacement = null;
                            if (entry.getKey().contains("INLINE")) {
                                // quando for equação inline: substituir o w:r pai do replacer por um o:math
                                replacement = mathFactory.createOMath((CTOMath) oMath);
                            } else {
                                // quando for parágrafo: substituir o w:r pai do replacer por um m:oMathPara
                                CTOMathPara oMathPara = mathFactory.createCTOMathPara();
                                oMathPara.getOMath().add((CTOMath) oMath);
                                replacement = mathFactory.createOMathPara(oMathPara);
                            }

                            paragraph.replaceElement(entry.getValue(), Arrays.asList(replacement));
                        } catch (JAXBException e) {
                            String message = "Error while parsing OMath input!";
                            LOG.error(message);
                            throw new RuntimeException(message, e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Converte uma fórmula em formato LaTEX para MathML
     *
     * @param laTEXFormula
     *            fórmula em formato LaTEX
     * @return {@code String} contendo a fórmula em MathML
     */
    private String laTEXFormulaToMathML(String laTEXFormula) {
        if (StringUtils.isNotBlank(laTEXFormula)) {
            /* Cria vanilla SnuggleEngine e nova SnuggleSession */
            SnuggleEngine engine = new SnuggleEngine();
            SnuggleSession sSession = engine.createSession();

            /* faz o parse da fórmula */
            SnuggleInput input = new SnuggleInput(laTEXFormula);
            try {
                sSession.parseInput(input);
                /*
                 * Converte os resultados para uma String XML com as opções
                 * desejadas.
                 */
                XMLStringOutputOptions options = new XMLStringOutputOptions();
                options.setSerializationMethod(SerializationMethod.XHTML);
                options.setIndenting(true);
                options.setEncoding(defaultEncoding);
                options.setUsingNamedEntities(true);
                String xmlString = sSession.buildXMLString(options);

                // Substitui as entities do MathML por sua correspondência em
                // unicode (o word não é capaz de identificar as entities do
                // formato MathML)
                return removeNamedEntites(xmlString);
            } catch (IOException e) {
                String message = "Error while trying to parse latex formula input";
                LOG.error(message);
                throw new RuntimeException(message, e);
            }

        }
        return null;
    }

    /**
     * Remove as entities nomeadas de uma fórmula em MathML para sua
     * correspondência em unicode.
     *
     * @param mathML
     * @return MathML sem namedEntities
     */
    private String removeNamedEntites(String mathML) {
        Pattern entPattern = Pattern.compile("&(\\S+?);");
        Matcher matcher = entPattern.matcher(mathML);
        Map<String, String> replacements = new HashMap<String, String>();
        try {
            Properties props = getMathMLEntitiesProperties();
            while (matcher.find()) {
                if (!replacements.containsKey(matcher.group())) {
                    replacements.put(matcher.group(), props.getProperty(matcher.group(1)));
                }
            }

            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                mathML = mathML.replaceAll(entry.getKey(), entry.getValue());
            }

        } catch (IOException e) {
            String message = String.format("Error while loading MathML named entities properties file '%s'",
                    MATHML_ENTITIES_PROPERTIES);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }

        return mathML;
    }

    private Properties getMathMLEntitiesProperties() throws IOException {
        if (_mathMLEntities == null) {
            InputStream is = ClassLoader.getSystemResourceAsStream(MATHML_ENTITIES_PROPERTIES);
            _mathMLEntities = new Properties();
            _mathMLEntities.load(is);
        }
        return _mathMLEntities;
    }

    /**
     * Gera um substituto único para fórmulas matemáticas.
     *
     * @param inlineMath
     * @return {@code String} contendo o substituto único gerado.
     */
    private String generateUniqueReplacer(boolean inlineMath) {
        final String inlineFlag = "INLINE-";
        UUID uuid = UUID.randomUUID();
        return replacerPrefix + (inlineMath ? inlineFlag : "") + uuid.toString();
    }

    private String mathMLToOMath(String mathML) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Result transformResult = new StreamResult(baos);
        String oMath = null;
        try {
            InputStream mathMLStream = new ByteArrayInputStream(mathML.getBytes(defaultEncoding));
            getTransformer().transform(new StreamSource(mathMLStream), transformResult);
            oMath = new String(baos.toByteArray(), defaultEncoding);
        } catch (UnsupportedEncodingException e) {
            String message = String.format("Encoding '%s' not supported!", defaultEncoding);
            LOG.error(message);
            throw new RuntimeException(message, e);
        } catch (TransformerConfigurationException e) {
            String message = "Problem with javax.xml.transform.Transformer configuration!";
            LOG.error(message);
            throw new RuntimeException(message, e);
        } catch (TransformerException e) {
            String message = "Not well-formed MathML input to be transformed!";
            LOG.error(message);
            throw new RuntimeException(message, e);
        }

        return oMath;
    }

    public String extractHTMLText(String hmtlInput) {
        if (StringUtils.isNotBlank(hmtlInput)) {
            Document htmlDoc = Jsoup.parse(hmtlInput);
            return htmlDoc.text();
        }
        return null;
    }

    private Transformer getTransformer() throws TransformerConfigurationException {
        if (_transformer == null) {
            InputStream styleStream = ClassLoader.getSystemResourceAsStream(mathML2OpenMathmlXSLTFile);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            StreamSource styleSrc = new StreamSource(styleStream);
            _transformer = transformerFactory.newTransformer(styleSrc);
        }
        return _transformer;
    }
}
