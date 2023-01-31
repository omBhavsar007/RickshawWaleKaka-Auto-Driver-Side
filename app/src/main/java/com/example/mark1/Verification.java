package com.example.mark1;

public class Verification {

    String LicenseNo;
    String LincensePhoto;
    String AadharNo;
    String AadharPhoto;

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }

    public void setLincensePhoto(String lincensePhoto) {
        LincensePhoto = lincensePhoto;
    }

    public void setAadharNo(String aadharNo) {
        AadharNo = aadharNo;
    }

    public void setAadharPhoto(String aadharPhoto) {
        AadharPhoto = aadharPhoto;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public String getLincensePhoto() {
        return LincensePhoto;
    }

    public String getAadharNo() {
        return AadharNo;
    }

    public String getAadharPhoto() {
        return AadharPhoto;
    }

    public Verification() {
    }

    public Verification(String licenseNo, String lincensePhoto, String aadharNo, String aadharPhoto) {
        LicenseNo = licenseNo;
        LincensePhoto = lincensePhoto;
        AadharNo = aadharNo;
        AadharPhoto = aadharPhoto;
    }
}
