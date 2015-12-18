package org.opencompare;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.stream.JsonReader;

import javax.xml.crypto.Data;


/**
 * Created by Quentin on 04/11/2015.
 */
public class Param {
    private Collection<DataStyle> dataStyleParam;
    private String orderType;
    private String orderColumn;
    private boolean showBottomNameFeatures;
    private boolean reversePCM;
    private boolean showPCMname;

    /**
     * Create an object Param based on a JSon file
     * @param json
     */
    public Param(String json) {
        try {
            this.dataStyleParam = new ArrayList<DataStyle>();
            JsonReader reader = new JsonReader(new FileReader("pcms\\"+json));

            reader.beginObject();

            while (reader.hasNext()) {
                String paramname = reader.nextName();
                if (paramname.equals("orderType")) {
                    this.orderType = reader.nextString();
                } else if (paramname.equals("orderColumn")) {
                    this.orderColumn = reader.nextString();
                }  else if (paramname.equals("showBottomNameFeatures")) {
                    this.showBottomNameFeatures = reader.nextBoolean();
                }  else if (paramname.equals("reversePCM")) {
                    this.reversePCM = reader.nextBoolean();
                } else if (paramname.equals("showPCMname")) {
                    this.showPCMname = reader.nextBoolean();
                } else if (paramname.equals("dataStyle")) {
                    // read array
                    reader.beginArray();
                    while (reader.hasNext()) {
                        String name = "";
                        String bgcolor = "";
                        String txtcolor = "";
                        int value = 0;
                        int borneinf = 0;
                        int bornesup = 0;

                        reader.beginObject();
                        int width = 0;
                        while (reader.hasNext()) {
                            String paramcolorname = reader.nextName();
                            if (paramcolorname.equals("name")) {
                                name = reader.nextString();
                            } else if (paramcolorname.equals("bgcolor")) {
                               bgcolor = reader.nextString();
                            } else if (paramcolorname.equals("txtcolor")) {
                               txtcolor = reader.nextString();
                            } else if (paramcolorname.equals("value")) {
                               value = reader.nextInt();
                            } else if (paramcolorname.equals("borneinf")) {
                               borneinf = reader.nextInt();
                            } else if (paramcolorname.equals("bornesup")) {
                               bornesup = reader.nextInt();
                            } else if (paramcolorname.equals("width")) {
                               width = reader.nextInt();
                            } else {
                                reader.skipValue();
                            }
                        }
                        DataStyle dataStyle = new DataStyle(name, bgcolor, txtcolor, value, borneinf, bornesup, width);
                        this.dataStyleParam.add(dataStyle);
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

    /**
     * @return dataStyleParam
     * the collection of all datastyle
     */
    public Collection<DataStyle> getDataStyleParam() {
        return dataStyleParam;
    }

    /**
     * @return orderType
     *
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @return orderColumn
     *
     */
    public String getOrderColumn() {
        return orderColumn;
    }

    /**
     * @return showBottomNameFeatures
     * boolean indicated if the features's name has to me show at the bottom
     */
    public boolean isShowBottomNameFeatures() {
        return showBottomNameFeatures;
    }

    /**
     * @return reversePCM
     * boolean indicated if the PCM has to be reverse
     */
    public boolean isReversePCM() {
        return reversePCM;
    }

    /**
     * @return showPCMname
     * boolean which defines if the name of the PCM has to be show
     */
    public boolean isShowPCMname() {
        return showPCMname;
    }

    /**
     *
     * @param arg
     */
    public static void main (String [] arg){
        //test
        Param p = new Param("params1.json");
        Collection<DataStyle> col;
        col = p.getDataStyleParam();
        System.out.println(col);
        System.out.println(p.getOrderColumn());
        System.out.println(p.getOrderType());
        System.out.println(p.isReversePCM());
        System.out.println(p.isShowBottomNameFeatures());
        System.out.println(p.isShowPCMname());
    }
}
