package com.indushospitals.model;

import java.util.List;

/**
 * Created by think360 on 20/05/17.
 */

public class SearchResultItem {

    private String id;
    private String doctor_name;
    private String city;
    private String speciality;
    private String hospital_id;
    private String hospital;
    private String evening;
    private String moning;
    private List<String> list;
    private String image ;



    public SearchResultItem(String id, String doctor_name, String city, String speciality, String hospital_id, String hospital, String evening, String moning, List<String>listA, String image) {
        this.id = id;
        this.doctor_name = doctor_name;
        this.city = city;
        this.speciality = speciality;
        this.hospital_id = hospital_id;
        this.hospital = hospital;
        this.evening = evening;
        this.moning = moning;
        this.list = listA;
        this.image = image;
    }

    public String getImage() {
        return image;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public String getMoning() {
        return moning;
    }

    public void setMoning(String moning) {
        this.moning = moning;
    }
    public String getHospitalId() {
        return hospital_id;
    }

    public void setHospitalId(String hospital_id) {
        this.hospital_id = hospital_id;
    }
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
