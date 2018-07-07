package com.sarangcode.projecttama;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

        @SerializedName("name")
        @Expose
        private String name;

        /*@SerializedName("url")
        @Expose
        private String imageUrl;*/

        /*@SerializedName("age")
        @Expose
        private Integer age;*/

        @SerializedName("contact")
        @Expose
        private String contact;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
