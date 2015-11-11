package org.opencompare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.stream.JsonReader;


/**
 * Created by Quentin on 04/11/2015.
 */
public class Param {
    private HashMap<String, String> dataColor;
    private String orderType;
    private String orderColumn;
    private boolean showBottomFeatures;
    private boolean reversePCM;
    private boolean showPCMname;

    private static final String folderPath = "C:\\Users\\Rom\\.babun\\cygwin\\home\\Rom\\babunProject\\GettingStarted\\src\\test\\java\\org\\opencompare\\";

    public Param(String json) {
        try {
            this.dataColor = new HashMap<String, String>();
            // Ouverture du JSON
            String path = folderPath+json;
            System.out.println(path);
            //FileReader reader = new FileReader(path);
            //JSONParser jsonParser = new JSONParser();
            JsonReader reader = new JsonReader(new FileReader(path));

            reader.beginObject();

            while (reader.hasNext()) {
                String paramname = reader.nextName();
                if (paramname.equals("orderType")) {
                    this.orderType = reader.nextString();
                } else if (paramname.equals("orderColumn")) {
                    this.orderColumn = reader.nextString();
                }  else if (paramname.equals("showBottomFeatures")) {
                    this.showBottomFeatures = reader.nextBoolean();
                }  else if (paramname.equals("reversePCM")) {
                    this.reversePCM = reader.nextBoolean();
                } else if (paramname.equals("showPCMname")) {
                    this.showPCMname = reader.nextBoolean();
                } else if (paramname.equals("dataColor")) {
                    // read array
                    reader.beginArray();
                    int i = 1;
                    while (reader.hasNext()) {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String paramcolorname = reader.nextName();
                            if (paramcolorname.equals("bgcolor")) {
                               this.dataColor.put(i+"bgcolor", reader.nextString());
                            } else if (paramcolorname.equals("txtcolor")) {
                                this.dataColor.put(i+"txtcolor", reader.nextString());
                            } else if (paramcolorname.equals("values")) {
                                this.dataColor.put(i+"values", reader.nextString());
                            } else {
                                reader.skipValue();
                            }
                        }
                        i++;
                        reader.endObject();
                    }
                    reader.endArray();
                } else {
                    reader.skipValue(); //avoid some unhandle events
                }
            }
            reader.endObject();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
