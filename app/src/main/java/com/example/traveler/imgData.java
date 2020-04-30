package com.example.traveler;

public class imgData {
    private String title;
    private String location;
    private  String imgId;

    public imgData() {

    }

    //Get image title
    public String getTitle() {
        return title;
    }

    //Set the image title
    public void setTitle(String title) {
        this.title = title;
    }

    //Get image location
    public String getLocation() {
        return location;
    }

    //Set the image location
    public void setLocation(String location) {
        this.location = location;
    }

    //Get image id
    public String getImgId() {
        return imgId;
    }

    //Set image id (image id is the image URI)
    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
