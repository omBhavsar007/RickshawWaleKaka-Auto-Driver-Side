package com.example.mark1;

public class Upload {

    String imageUri;
    String Name;

    public Upload() {

    }

    public Upload(String imageUri, String name) {
        this.imageUri = imageUri;
        Name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getName() {
        return Name;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setName(String name) {
        Name = name;
    }
}
