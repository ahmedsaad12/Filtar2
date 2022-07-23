package com.app.filtar.model;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.app.filtar.BR;
import com.app.filtar.R;

import java.io.Serializable;


public class ContactUsModel extends BaseObservable implements Serializable {

    private String message;
    private boolean valid;
    public ObservableField<String> error_message = new ObservableField<>();

    public boolean isDataValid(Context context) {

        if (
                !message.isEmpty()

        ) {


            error_message.set(null);


            return true;

        } else {


            if (message.isEmpty()) {
                error_message.set(context.getString(R.string.field_required));
            } else {
                error_message.set(null);

            }

            return false;

        }

    }

    public ContactUsModel() {

        message = "";
    }


    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);

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
