package org.opencompare;

import jdk.nashorn.internal.parser.JSONParser;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import static org.junit.Assert.*;

import org.opencompare.api.java.io.PCMLoader;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by gbecan on 02/02/15.
 */
public class MyPCMPrinterTest {

    @Test
    public  void testMyPCMPrinter() throws IOException {
            // Load a PCM
            File pcmFile = new File("pcms/tesssvtttt369852147.pcm");
            File paramFile = new File("json/param1.json");
            // read the json file
            PCMLoader loader = new KMFJSONLoader();
            PCM pcm = loader.load(pcmFile).get(0).getPcm();
            assertNotNull(pcm);

            // Execute the printer
            MyPCMPrinter myPrinter = new MyPCMPrinter();
            myPrinter.print(pcm);
    }


}
