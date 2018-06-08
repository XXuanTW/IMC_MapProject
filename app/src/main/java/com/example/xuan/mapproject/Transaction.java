package com.example.xuan.mapproject;

/**
 * Created by Xuan on 2018/2/27.
 */

public class Transaction {

    String e_mobile;
    String e_position;
    String e_comp_web;
    String e_photo;
    String sql_title4;
    String m_lat;
    String m_lng;
    String m_type;
    String e_comp_name;
    String e_name;
    String m_address;

    public Transaction(String e_mobile, String e_position, String e_comp_web, String e_photo, String sql_title4, String m_lat, String m_lng, String m_type, String e_comp_name, String e_name, String m_address) {
        this.e_mobile = e_mobile;
        this.e_position = e_position;
        this.e_comp_web = e_comp_web;
        this.e_photo = e_photo;
        this.sql_title4 = sql_title4;
        this.m_lat = m_lat;
        this.m_lng = m_lng;
        this.m_type = m_type;
        this.e_comp_name = e_comp_name;
        this.e_name = e_name;
        this.m_address = m_address;
    }

    public String getE_mobile() {
        return e_mobile;
    }

    public String getE_position() {
        return e_position;
    }

    public String getE_comp_web() {
        return e_comp_web;
    }

    public String getE_photo() {
        return e_photo;
    }

    public String getSql_title4() {
        return sql_title4;
    }

    public String getM_lat() {
        return m_lat;
    }

    public String getM_lng() {
        return m_lng;
    }

    public String getM_type() {
        return m_type;
    }

    public String getE_comp_name() {
        return e_comp_name;
    }

    public String getE_name() {
        return e_name;
    }

    public String getM_address() {
        return m_address;
    }

    public void setE_mobile(String e_mobile) {
        this.e_mobile = e_mobile;
    }

    public void setE_position(String e_position) {
        this.e_position = e_position;
    }

    public void setE_comp_web(String e_comp_web) {
        this.e_comp_web = e_comp_web;
    }

    public void setE_photo(String e_photo) {
        this.e_photo = e_photo;
    }

    public void setSql_title4(String sql_title4) {
        this.sql_title4 = sql_title4;
    }

    public void setM_lat(String m_lat) {
        this.m_lat = m_lat;
    }

    public void setM_lng(String m_lng) {
        this.m_lng = m_lng;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public void setE_comp_name(String e_comp_name) {
        this.e_comp_name = e_comp_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }
}

