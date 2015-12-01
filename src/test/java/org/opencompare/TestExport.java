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
    public  void testExport() throws IOException {
        //TEST
        // Path of Params
        String p = "PCM1\\params1.json";

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
        exporter.toHTML(pcm);
        // Tester le rendu du HTML
    }
}
