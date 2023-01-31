package com.example.mark1;

public class Requests {

    String parentname;
    String childname;
    String schoolname;
    String parentno;
    String id;
    String profilePhoto;

    public Requests(String parentname, String childname, String schoolname, String parentno, String id, String profilePhoto) {
        this.parentname = parentname;
        this.childname = childname;
        this.schoolname = schoolname;
        this.parentno = parentno;
        this.id = id;
        this.profilePhoto = profilePhoto;
    }


    public Requests() {

    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getParentno() {
        return parentno;
    }

    public void setParentno(String parentno) {
        this.parentno = parentno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
