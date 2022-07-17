package com.filtar.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.filtar.BR;


public class SignUpMarketModel extends BaseObservable {
    private String image_uri;
    private String image_commercial_uri;
    private String image_id_uri;
    private String image_tax_uri;
    private String phone_code;
    private String phone;
    private String first_name;
    private String last_name;
    private String address;
    private String store_name;
    private String commercial_num;
    private String tax_num;
    private boolean valid;

    public void isDataValid() {
        if (!first_name.trim().isEmpty() &&
                !last_name.trim().isEmpty() &&
                !store_name.trim().isEmpty() &&
                !commercial_num.trim().isEmpty() &&
                !tax_num.trim().isEmpty() &&
                !image_commercial_uri.trim().isEmpty() &&
                !image_id_uri.trim().isEmpty() &&
                !image_tax_uri.trim().isEmpty() &&
                !address.trim().isEmpty()

        ) {
            setValid(true);
        } else {
            setValid(false);


        }
    }

    public SignUpMarketModel() {
        image_uri = "";
        image_commercial_uri = "";
        image_id_uri="";
        image_tax_uri="";
        tax_num="";
        commercial_num="";
        store_name="";
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

    public String getImage_commercial_uri() {
        return image_commercial_uri;
    }

    public void setImage_commercial_uri(String image_commercial_uri) {
        this.image_commercial_uri = image_commercial_uri;
    }

    public String getImage_id_uri() {
        return image_id_uri;
    }

    public void setImage_id_uri(String image_id_uri) {
        this.image_id_uri = image_id_uri;
    }

    public String getImage_tax_uri() {
        return image_tax_uri;
    }

    public void setImage_tax_uri(String image_tax_uri) {
        this.image_tax_uri = image_tax_uri;
    }
    @Bindable
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
        notifyPropertyChanged(BR.store_name);
    }
    @Bindable
    public String getCommercial_num() {
        return commercial_num;
    }

    public void setCommercial_num(String commercial_num) {
        this.commercial_num = commercial_num;
        notifyPropertyChanged(BR.commercial_num);

    }
@Bindable
    public String getTax_num() {
        return tax_num;
    }

    public void setTax_num(String tax_num) {
        this.tax_num = tax_num;
        notifyPropertyChanged(BR.tax_num);

    }
}