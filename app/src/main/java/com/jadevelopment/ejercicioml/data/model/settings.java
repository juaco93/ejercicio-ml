package com.jadevelopment.ejercicioml.data.model;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class settings {
    bin bin;
    card_number card_number;
    security_code security_code;

    public settings(com.jadevelopment.ejercicioml.data.model.bin bin, com.jadevelopment.ejercicioml.data.model.card_number card_number, com.jadevelopment.ejercicioml.data.model.security_code security_code) {
        this.bin = bin;
        this.card_number = card_number;
        this.security_code = security_code;
    }
}
