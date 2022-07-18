package com.app.filtar.uis.activity_user_type;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.app.filtar.R;
import com.app.filtar.databinding.ActivityChooseUserBinding;
import com.app.filtar.databinding.ActivityLoginBinding;
import com.app.filtar.databinding.DailogVerificationCodeBinding;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_market_sign_up.SignUpMarketActivity;
import com.app.filtar.uis.activity_sign_up.SignUpUserActivity;

public class ChooseUserTypeActivity extends BaseActivity {
    private ActivityChooseUserBinding binding;
    private int type;
    private String phone_code, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_user);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");

    }

    private void initView() {
        String title = getString(R.string.sign_up);
        setUpToolbar(binding.toolbar, title, R.color.white, R.color.black);

        binding.setLang(getLang());
        type = 1;
        binding.fluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                binding.fluser.setBackground(getResources().getDrawable(R.drawable.rounded_white_stroke_primary));
                binding.flMarket.setBackground(getResources().getDrawable(R.drawable.rounded_white_stroke_grey7));
            }
        });
        binding.flMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                binding.flMarket.setBackground(getResources().getDrawable(R.drawable.rounded_white_stroke_primary));
                binding.fluser.setBackground(getResources().getDrawable(R.drawable.rounded_white_stroke_grey7));
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    navigateToUserSignUpActivity();
                } else {
                    navigateToMarketSignUpActivity();
                }
            }
        });
    }


    private void navigateToUserSignUpActivity() {
        Intent intent = new Intent(this, SignUpUserActivity.class);
        intent.putExtra("phone_code", phone_code);
        intent.putExtra("phone", phone);
        startActivity(intent);
        finish();
    }

    private void navigateToMarketSignUpActivity() {
        Intent intent = new Intent(this, SignUpMarketActivity.class);
        intent.putExtra("phone_code", phone_code);
        intent.putExtra("phone", phone);
        startActivity(intent);
        finish();
    }
}