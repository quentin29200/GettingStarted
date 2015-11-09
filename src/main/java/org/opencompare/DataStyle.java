package org.opencompare;

/**
 * Created by Quentin on 09/11/2015.
 */
public class DataStyle {
    private String name;
    private String bgcolor;
    private String txtcolor;
    private String values;
    private int width;

    // Constructor
    public DataStyle(String name, String bgcolor, String txtcolor, String values, int width) {
        this.name = name;
        this.bgcolor = bgcolor;
        this.txtcolor = txtcolor;
        this.values = values;
        this.width = width;
    }

    // Getter and Setter
    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getTxtcolor() {
        return txtcolor;
    }

    public void setTxtcolor(String txtcolor) {
        this.txtcolor = txtcolor;
    }

    public String toString() {
        return "DataStyle{" +
                "name='" + name + '\'' +
                ", bgcolor='" + bgcolor + '\'' +
                ", txtcolor='" + txtcolor + '\'' +
                ", values='" + values + '\'' +
                ", width=" + width +
                '}';
    }


}
