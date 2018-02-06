package com.jadevelopment.ejercicioml.data.model;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class financial_institutions {
    Integer id;
    String description;

    public financial_institutions(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
