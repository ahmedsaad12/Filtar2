package com.filtar.uis.activity_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.filtar.R;
import com.filtar.adapter.CountryCodeAdapter;
import com.filtar.databinding.ActivityLoginBinding;
import com.filtar.databinding.DailogVerificationCodeBinding;
import com.filtar.model.CountryCodeModel;
import com.filtar.model.LoginModel;
import com.filtar.model.UserModel;
import com.filtar.mvvm.ActivityLoginMvvm;
import com.filtar.share.Common;
import com.filtar.uis.activity_base.BaseActivity;
import com.filtar.uis.activity_home.HomeActivity;
import com.filtar.uis.activity_user_type.ChooseUserTypeActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String phone_code = "";
    private String phone = "";
    private LoginModel model;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityLoginMvvm mvvm;
    private DailogVerificationCodeBinding dailogVerificationCodeBinding;
    private AlertDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        binding.setLang(getLang());
        // setUpToolbar(binding.toolbar, getString(R.string.login), R.color.white, R.color.black);
        model = new LoginModel();
        binding.setModel(model);
        setUpSpinner();
        mvvm.getSmsCode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dailogVerificationCodeBinding.edtCode.setText(s);
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });


        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            createVerificationCodeDialog();
            mvvm.sendSmsCode(model, this);
        });

        mvvm.getTime().observe(this, time -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvTime.setText(time);

            }
        });

        mvvm.canResend().observe(this, canResend -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvResend.setEnabled(canResend);
                if (canResend) {
                    dailogVerificationCodeBinding.tvResend.setText(getString(R.string.resend));

                }
            }

        });
        mvvm.getUserData().observe(this, userModel -> {
            if (builder != null) {
                builder.dismiss();
            }
            if (userModel != null) {
                setUserModel(userModel);
                navigteToHomeActivity();
            } else {
                navigateToSignUpActivity();
            }
        });

    }

    private void setUpSpinner() {
        List<CountryCodeModel> list = new ArrayList<>();
        list.add(new CountryCodeModel(R.drawable.egypt_flag, getString(R.string.egypt), "+20"));

        list.add(new CountryCodeModel(R.drawable.saudi_arabia, getString(R.string.saudi_arabia), "+966"));

        CountryCodeAdapter adapter = new CountryCodeAdapter(this);
        adapter.updateList(list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryCodeModel model = (CountryCodeModel) parent.getAdapter().getItem(position);
                LoginActivity.this.model.setPhone_code(model.getCode());
                binding.setModel(LoginActivity.this.model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void createVerificationCodeDialog() {
        builder = new AlertDialog.Builder(this)
                .create();
        builder.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dailogVerificationCodeBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dailog_verification_code, null, false);
        dailogVerificationCodeBinding.setModel(model);
        builder.setView(dailogVerificationCodeBinding.getRoot());
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        dailogVerificationCodeBinding.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dailogVerificationCodeBinding.btnVerify.setEnabled(s.toString().length() == 6);

            }
        });
        dailogVerificationCodeBinding.tvResend.setOnClickListener(v -> mvvm.reSendSmsCode(model, this));
        dailogVerificationCodeBinding.btnVerify.setOnClickListener(v -> {
            String code = dailogVerificationCodeBinding.edtCode.getText().toString();
            if (!code.isEmpty()) {
                mvvm.checkValidCode(code, this, model);
                Common.CloseKeyBoard(this, dailogVerificationCodeBinding.edtCode);

            }

        });

        builder.show();

        builder.setOnCancelListener(dialog -> mvvm.stopTimer());

    }

    private void navigateToSignUpActivity() {
        Intent intent = new Intent(this, ChooseUserTypeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        startActivity(intent);
        finish();
    }
    private void navigteToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}