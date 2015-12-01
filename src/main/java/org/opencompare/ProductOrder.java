package org.opencompare;

import org.opencompare.api.java.Cell;

import java.util.List;

/**
 * Created by Rom on 11/29/2015.
 */
public class ProductOrder {
    private String name;
    private List cells;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getCells() {
        return cells;
    }

    public void setCells(List cells) {
        this.cells = cells;
    }

    public ProductOrder(String s, List l){
        this.setName(s);
        this.setCells(l);
    }
}
