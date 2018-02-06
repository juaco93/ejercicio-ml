package com.jadevelopment.ejercicioml.data.model;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class card_number {
    String length;
    String validation;

    public card_number(String length, String validation) {
        this.length = length;
        this.validation = validation;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
