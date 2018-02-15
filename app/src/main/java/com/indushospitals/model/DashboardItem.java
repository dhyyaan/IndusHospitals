package com.indushospitals.model;

import java.io.Serializable;

/**
 * Created by think360 on 06/05/17.
 */

public class DashboardItem implements Serializable{
    private String id;
    private String patient_name;
    private String sn;
    private String hospital;
    private String name;
    private String created_on;
    private String gender;
    private String age;
    private String comments;
    private String doctor_name;

    public String getRefered_doctor() {
        return doctor_name;
    }

    public void setRefered_doctor(String refered_doctor) {
        this.doctor_name = refered_doctor;
    }




    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return ", Age:- "+age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getComments() {
        return comments;
    }
}
