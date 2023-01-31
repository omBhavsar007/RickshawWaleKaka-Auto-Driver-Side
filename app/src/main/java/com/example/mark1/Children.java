package com.example.mark1;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Children {

    String childName;
    String parentName;
    String parentNo;
    String ProfilePhoto;



    String id;


    public Children() {
    }

    public Children(String childName, String parentName, String parentNo, String profilePhoto, String id) {
        this.childName = childName;
        this.parentName = parentName;
        this.parentNo = parentNo;
        ProfilePhoto = profilePhoto;
        this.id = id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
