package com.indushospitals.model;

/**
 * Created by think360 on 03/05/17.
 */

public class PackagesItem {

    private String id;
    private String title;
    private String price;
    private String description;
    private String mobile;
    public String getContact_no() {
        return contact_no;
    }

    private String contact_no;

    public String getAddress() {
        return address;
    }

    private String address;
    public PackagesItem( String title,String address,String contact_no){

        this.title = title;
        this.address = address;
        this.contact_no = contact_no;

    }
   public PackagesItem(String id , String title,String price,String description,String mobile){
       this.id = id;
       this.title = title;
       this.price = price;
       this.mobile = mobile;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String icon) {
        this.price = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String description) {
        this.mobile = mobile;
    }
}