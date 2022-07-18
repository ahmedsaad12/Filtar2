package com.app.filtar.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.filtar.BR;


public class SignUpModel extends BaseObservable {
    private String image_uri;
    private String phone_code;
    private String phone;
    private String first_name;
    private String last_name;
    private String address;

    private boolean valid;

    public void isDataValid() {
        if (!first_name.trim().isEmpty() &&
                !last_name.trim().isEmpty() &&
                !address.trim().isEmpty()

        ) {
            setValid(true);
        } else {
            setValid(false);


        }
    }

    public SignUpModel() {
        image_uri = "";
        phone_code = "";
        phone = "";
        first_name = "";
        last_name = "";
        address = "";


    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        notifyPropertyChanged(BR.first_name);
        isDataValid();
    }

    @Bindable
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
        notifyPropertyChanged(BR.last_name);
        isDataValid();
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
        isDataValid();
    }



    @Bindable
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        notifyPropertyChanged(BR.valid);
    }



}