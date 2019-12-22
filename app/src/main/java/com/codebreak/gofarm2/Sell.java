package com.codebreak.gofarm2;

public class Sell {

    private String name;
    private String city;
    private String crop;
    private String amt;
    private String contact;
    private String dp;

    public Sell(String name, String city, String crop, String amt, String contact, String dp) {
        this.name = name;
        this.city = city;
        this.crop = crop;
        this.amt = amt;
        this.contact = contact;
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

}
