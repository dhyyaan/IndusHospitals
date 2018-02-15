package com.indushospitals.model;

import java.io.Serializable;

/**
 * Created by think360 on 06/05/17.
 */

public class NotificationItem implements Serializable {
    private String id;
    private String comments;
    private String doctor_name;
    private String gender;
    private String appointment_request;
    private String read_unread;
    private String address;
    private String patient_name;
    private String sn;
    private String apointment_time;
    private String apointment_date;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }




    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getApointment_time() {
        return apointment_time;
    }

    public void setApointment_time(String apointment_time) {
        this.apointment_time = apointment_time;
    }

    public String getApointment_date() {
        return apointment_date;
    }

    public void setApointment_date(String apointment_date) {
        this.apointment_date = apointment_date;
    }

    public String getAppointment_request() {
        return appointment_request;
    }

    public void setAppointment_request(String appointment_request) {
        this.appointment_request = appointment_request;
    }
    public String getRead_unread() {
        return read_unread;
    }

    public void setRead_unread(String read_unread) {
        this.read_unread = read_unread;
    }
}
