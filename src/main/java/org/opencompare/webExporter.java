package org.opencompare;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opencompare.api.java.util.PCMVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Cheisda on 21/10/2015.
 */
public abstract class webExporter implements PCMVisitor{
    private JSONObject params;
    public void setParams(File jsonparams) {
        // Get params from JSON File
        this.params = null;
        try {
            FileReader reader = new FileReader(jsonparams);
            JSONParser jsonParser = new JSONParser();
            this.params = (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
