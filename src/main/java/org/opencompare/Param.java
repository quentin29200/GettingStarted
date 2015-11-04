package org.opencompare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Quentin on 04/11/2015.
 */
public class Param {
    private HashMap dataColor;
    private String orderType;
    private String orderColumn;
    private boolean showBottomFeatures;
    private boolean reversePCM;
    private boolean showPCMname;

    private static final String folderPath = "D:\\MIAGE\\PDL\\GettingStarted\\src\\test\\java\\org\\opencompare\\";

    public Param(String json) {
        try {
            // Ouverture du JSON
            FileReader reader = new FileReader(folderPath+json);
            JSONParser jsonParser = new JSONParser();

            // Lecture du JSON dans un objet JSON
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            /* Instanciation de l'objet param */

            // Récupération des paramètres de couleur selon les valeurs
            JSONArray dataColorArray = (JSONArray) jsonObject.get("dataColor");
            for (int i = 0; i < dataColorArray.size(); ++i) {
                JSONObject dataColorObject = (JSONObject) dataColorArray.get(i);
                this.dataColor.put("values",dataColorObject.get("value").toString());
                this.dataColor.put("bgcolor",dataColorObject.get("bgcolor").toString());
                this.dataColor.put("txtcolor",dataColorObject.get("txtcolor").toString());
            }

            // Récupération des paramètres de tri sur colonne
            JSONObject order = (JSONObject) jsonObject.get("order");
            this.orderType = order.get("type").toString();
            this.orderColumn = order.get("column").toString();

            // Récupération des booléen
            this.showBottomFeatures = (boolean) jsonObject.get("showBottomFeatures");
            this.reversePCM = (boolean) jsonObject.get("reversePCM");
            this.showPCMname = (boolean) jsonObject.get("showPCMname");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void main (String [] arg){
        //test
        Param p = new Param("params1.json");
        System.out.println(p.getDataColor());
        System.out.println(p.getOrderColumn());
        System.out.println(p.getOrderType());
        System.out.println(p.isReversePCM());
        System.out.println(p.isShowBottomFeatures());
        System.out.println(p.isShowPCMname());
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public HashMap getDataColor() {
        return dataColor;
    }

    public boolean isShowBottomFeatures() {
        return showBottomFeatures;
    }

    public boolean isReversePCM() {
        return reversePCM;
    }

    public boolean isShowPCMname() {
        return showPCMname;
    }
}
