package com.example.wasla.Models.MapModel;

import java.io.Serializable;

public class Duration implements Serializable {

    private String text;
    private int value;

    public Duration() {
    }

    public Duration(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
