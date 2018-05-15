package com.sarangcode.projecttama;

public class ItemData {
    String text;
    Integer imageId;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImageId(){
        return imageId;

    }
}
