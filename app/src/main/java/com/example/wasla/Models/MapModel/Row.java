package com.example.wasla.Models.MapModel;

import java.io.Serializable;
import java.util.List;

public class Row implements Serializable {

    private List<Element> elements;


    public Row() {
    }

    public Row(List<Element> elements) {
        this.elements = elements;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
