package org.opencompare;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;


/**
 *
 *  Created by Quentin on 18/11/2015.
 */
public class TestExport {
    @Test
    public  void testExport1() throws IOException {
        //TESTff
        // Path of Params
        String p = "TEST\\params1.json";

        // Load PCM
        File pcmFile = new File("pcms/TEST/tesssvtttt369852147.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Generate CSS
        CSSExporter css = new CSSExporter(p);
        css.generate();

        // Tester le rendu du CSS
        File testcss = new File("pcms/TEST/TESTCSS/style1.css");
        File generatecss = new File("src/style.css");
        assertNotNull(testcss);
        assertNotNull(generatecss);

        BufferedReader br = new BufferedReader(new FileReader(testcss));
        String ligne = null;
        String testcss_s="";
        while ((ligne=br.readLine())!=null){
            testcss_s+=ligne+"\n";
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(generatecss));
        String ligne2 = null;
        String generatecss_s="";
        while ((ligne2=br2.readLine())!=null){
            generatecss_s+=ligne2+"\n";
        }
        br2.close();

        assertTrue(testcss_s.equals(generatecss_s));

        // ExportToHTML
        HTMLExporterCustom exporter = new HTMLExporterCustom(p);
        exporter.generateHTMLFile(pcm);
        // Tester le rendu du HTML

        File testhtml = new File("pcms/TEST/index1.html");
        File generatehtml = new File("src/HTMLGenerated.html");
        assertNotNull(testhtml);
        assertNotNull(generatehtml);

        BufferedReader br3 = new BufferedReader(new FileReader(testcss));
        String ligne3 = null;
        String testhtml_s="";
        while ((ligne3=br3.readLine())!=null){
            testhtml_s+=ligne3+"\n";
        }
        br3.close();

        BufferedReader br4 = new BufferedReader(new FileReader(generatecss));
        String ligne4 = null;
        String generatehtml_s="";
        while ((ligne4=br4.readLine())!=null){
            generatehtml_s+=ligne4+"\n";
        }
        br4.close();

        assertTrue(testhtml_s.equals(generatehtml_s));

    }
    @Test
    public  void testExport2() throws IOException {
        //TESTff
        // Path of Params
        String p = "TEST\\params2.json";

        // Load PCM
        File pcmFile = new File("pcms/TEST/tesssvtttt369852147.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Generate CSS
        CSSExporter css = new CSSExporter(p);
        css.generate();

        // Tester le rendu du CSS
        File testcss = new File("pcms/TEST/TESTCSS/style2.css");
        File generatecss = new File("src/style.css");
        assertNotNull(testcss);
        assertNotNull(generatecss);

        BufferedReader br = new BufferedReader(new FileReader(testcss));
        String ligne = null;
        String testcss_s="";
        while ((ligne=br.readLine())!=null){
            testcss_s+=ligne+"\n";
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(generatecss));
        String ligne2 = null;
        String generatecss_s="";
        while ((ligne2=br2.readLine())!=null){
            generatecss_s+=ligne2+"\n";
        }
        br2.close();

        assertTrue(testcss_s.equals(generatecss_s));

        // ExportToHTML
        HTMLExporterCustom exporter = new HTMLExporterCustom(p);
        exporter.toHTML(pcm);
        // Tester le rendu du HTML
    }
    @Test
    public  void testExport3() throws IOException {
        //TESTff
        // Path of Params
        String p = "TEST\\params3.json";

        // Load PCM
        File pcmFile = new File("pcms/TEST/tesssvtttt369852147.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Generate CSS
        CSSExporter css = new CSSExporter(p);
        css.generate();

        // Tester le rendu du CSS
        File testcss = new File("pcms/TEST/TESTCSS/style3.css");
        File generatecss = new File("src/style.css");
        assertNotNull(testcss);
        assertNotNull(generatecss);

        BufferedReader br = new BufferedReader(new FileReader(testcss));
        String ligne = null;
        String testcss_s="";
        while ((ligne=br.readLine())!=null){
            testcss_s+=ligne+"\n";
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(generatecss));
        String ligne2 = null;
        String generatecss_s="";
        while ((ligne2=br2.readLine())!=null){
            generatecss_s+=ligne2+"\n";
        }
        br2.close();

        assertTrue(testcss_s.equals(generatecss_s));

        // ExportToHTML
        HTMLExporterCustom exporter = new HTMLExporterCustom(p);
        exporter.toHTML(pcm);
        // Tester le rendu du HTML
    }
    @Test
    public  void testExport4() throws IOException {
        //TESTff
        // Path of Params
        String p = "TEST\\params4.json";

        // Load PCM
        File pcmFile = new File("pcms/TEST/tesssvtttt369852147.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Generate CSS
        CSSExporter css = new CSSExporter(p);
        css.generate();

        // Tester le rendu du CSS
        File testcss = new File("pcms/TEST/TESTCSS/style4.css");
        File generatecss = new File("src/style.css");
        assertNotNull(testcss);
        assertNotNull(generatecss);

        BufferedReader br = new BufferedReader(new FileReader(testcss));
        String ligne = null;
        String testcss_s="";
        while ((ligne=br.readLine())!=null){
            testcss_s+=ligne+"\n";
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(generatecss));
        String ligne2 = null;
        String generatecss_s="";
        while ((ligne2=br2.readLine())!=null){
            generatecss_s+=ligne2+"\n";
        }
        br2.close();

        assertTrue(testcss_s.equals(generatecss_s));

        // ExportToHTML
        HTMLExporterCustom exporter = new HTMLExporterCustom(p);
        exporter.toHTML(pcm);
        // Tester le rendu du HTML
    }
}
