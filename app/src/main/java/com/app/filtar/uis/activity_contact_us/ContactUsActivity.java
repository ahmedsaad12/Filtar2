package com.app.filtar.uis.activity_contact_us;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.filtar.R;
import com.app.filtar.databinding.ActivityContactUsBinding;
import com.app.filtar.model.ContactUsModel;
import com.app.filtar.model.UserModel;
import com.app.filtar.mvvm.ActivityContactUsMvvm;
import com.app.filtar.preferences.Preferences;
import com.app.filtar.uis.activity_base.BaseActivity;


public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ContactUsModel contactUsModel;
    private ActivityContactUsMvvm contactusActivityMvvm;
    private UserModel userModel;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        contactusActivityMvvm = ViewModelProviders.of(this).get(ActivityContactUsMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setModel(userModel);
        contactUsModel = new ContactUsModel();
//        if (userModel != null) {
//            contactUsModel.setName(userModel.getData().getUser().getName());
//            if(userModel.getData().getUser().getEmail()!=null) {
//                contactUsModel.setEmail(userModel.getData().getUser().getEmail());
//            }
//        }



        binding.setContactModel(contactUsModel);


        binding.btnSend.setOnClickListener(view -> {
            if (contactUsModel.isDataValid(this)) {
                if(getUserModel().getData().getStore_name()==null){
                contactusActivityMvvm.contactUs(this, contactUsModel,getUserModel().getData().getId(),null);
            }
            else {
                    contactusActivityMvvm.contactUs(this, contactUsModel,null,getUserModel().getData().getId());

                }
            }
        });
        contactusActivityMvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
        binding.toolbar.llBack.setOnClickListener(view -> finish());
    }


}