package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Quentin on 18/11/2015.
 */
public class TestExport {
    @Test
    public  void testExport() throws IOException {
        // Path of Params
        String p = "PCM1\\params1.json";

        // Load PCM
        File pcmFile = new File("PCM1\\example.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

       // ExportToHTML
        HTMLExporterCustom exporter = new HTMLExporterCustom(p);
        exporter.toHTML(pcm);
    }
}
