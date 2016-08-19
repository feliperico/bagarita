package br.frico.bagarita.service;

import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Felipe Rico on 8/19/2016.
 */
public class HtmConversionServiceImplTest {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("test_input.html");
        String htmlInput = IOUtils.toString(inputStream);

        HtmlConversionServiceImpl htmlConversionService = new HtmlConversionServiceImpl();

        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.createPackage();
        htmlConversionService.addHTMLInput2Docx(htmlInput, wmlPackage);

        File file = new File("C:\\Users\\lc43092\\Documents\\test.docx");
        wmlPackage.save(file);
    }
}
