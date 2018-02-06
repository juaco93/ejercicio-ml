package com.jadevelopment.ejercicioml.data.model;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class security_code {
    String mode;
    Number length;
    String card_location;

    public security_code(String mode, Number length, String card_location) {
        this.mode = mode;
        this.length = length;
        this.card_location = card_location;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Number getLength() {
        return length;
    }

    public void setLength(Number length) {
        this.length = length;
    }

    public String getCard_location() {
        return card_location;
    }

    public void setCard_location(String card_location) {
        this.card_location = card_location;
    }
}
