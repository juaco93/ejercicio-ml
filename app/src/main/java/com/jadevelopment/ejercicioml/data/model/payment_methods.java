package com.jadevelopment.ejercicioml.data.model;

import java.util.List;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class payment_methods {
    String id;
    String name;
    String payment_type_id;
    String status;
    String secure_thumbnail;
    String thumbnail;
    String deferred_capture;
    List <settings> settings;
    List <String> additional_info_needed;
    Number min_allowed_amount;
    Number max_allowed_amount;
    Number acreditation_time;
    List <financial_institutions> financial_institutions;

    public payment_methods(String id, String name, String payment_type_id, String status, String secure_thumbnail, String thumbnail, String deferred_capture, List<com.jadevelopment.ejercicioml.data.model.settings> settings, List<String> additional_info_needed, Number min_allowed_amount, Number max_allowed_amount, Number acreditation_time, List<com.jadevelopment.ejercicioml.data.model.financial_institutions> financial_institutions) {
        this.id = id;
        this.name = name;
        this.payment_type_id = payment_type_id;
        this.status = status;
        this.secure_thumbnail = secure_thumbnail;
        this.thumbnail = thumbnail;
        this.deferred_capture = deferred_capture;
        this.settings = settings;
        this.additional_info_needed = additional_info_needed;
        this.min_allowed_amount = min_allowed_amount;
        this.max_allowed_amount = max_allowed_amount;
        this.acreditation_time = acreditation_time;
        this.financial_institutions = financial_institutions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecure_thumbnail() {
        return secure_thumbnail;
    }

    public void setSecure_thumbnail(String secure_thumbnail) {
        this.secure_thumbnail = secure_thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDeferred_capture() {
        return deferred_capture;
    }

    public void setDeferred_capture(String deferred_capture) {
        this.deferred_capture = deferred_capture;
    }

    public List<com.jadevelopment.ejercicioml.data.model.settings> getSettings() {
        return settings;
    }

    public void setSettings(List<com.jadevelopment.ejercicioml.data.model.settings> settings) {
        this.settings = settings;
    }

    public List<String> getAdditional_info_needed() {
        return additional_info_needed;
    }

    public void setAdditional_info_needed(List<String> additional_info_needed) {
        this.additional_info_needed = additional_info_needed;
    }

    public Number getMin_allowed_amount() {
        return min_allowed_amount;
    }

    public void setMin_allowed_amount(Number min_allowed_amount) {
        this.min_allowed_amount = min_allowed_amount;
    }

    public Number getMax_allowed_amount() {
        return max_allowed_amount;
    }

    public void setMax_allowed_amount(Number max_allowed_amount) {
        this.max_allowed_amount = max_allowed_amount;
    }

    public Number getAcreditation_time() {
        return acreditation_time;
    }

    public void setAcreditation_time(Number acreditation_time) {
        this.acreditation_time = acreditation_time;
    }

    public List<com.jadevelopment.ejercicioml.data.model.financial_institutions> getFinancial_institutions() {
        return financial_institutions;
    }

    public void setFinancial_institutions(List<com.jadevelopment.ejercicioml.data.model.financial_institutions> financial_institutions) {
        this.financial_institutions = financial_institutions;
    }
}
