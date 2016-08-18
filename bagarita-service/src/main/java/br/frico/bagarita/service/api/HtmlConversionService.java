package br.frico.bagarita.service.api;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 * Service responsible for converting html inputs to a different format.
 *
 * Created by Felipe Rico on 8/18/2016.
 */
public interface HtmlConversionService {

    /**
     * Converte um html para o formato Office Open XML para posterior adição no
     * arquivo docx. Abrange a conversão de fórmulas em formato LaTEX no meio do
     * HTML para o formato OpenMathXML.
     *
     * @param htmlInput
     * @param wordMLPackage
     */
    void addHTMLInput2Docx(String htmlInput, WordprocessingMLPackage wordMLPackage);

    /**
     * Extrai o texto de um input HTML, desconsiderando imagens e fórmulas no
     * formato LaTEX.
     *
     * @param hmtlInput
     * @return {@link String} com o texto extraído.
     */
    String extractHTMLText(String hmtlInput);
}
