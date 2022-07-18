package com.app.filtar.uis.activity_add_filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.filtar.R;
import com.app.filtar.databinding.ActivityAddFilterBinding;
import com.app.filtar.model.AddFlterModel;
import com.app.filtar.model.FilterCandleModel;
import com.app.filtar.mvvm.ActivityAddFilterMvvm;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_home.HomeActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddFilterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private ActivityAddFilterBinding binding;

    private AddFlterModel addFlterModel;
    private List<FilterCandleModel> filterCandleModelList;
    private List<String> watertype;
    private int datetype;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private ActivityAddFilterMvvm activityAddFilterMvvm;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_filter);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);


    }

    private void initView() {
        if(type==0){
            binding.tvSkip.setVisibility(View.GONE);
        }
        String title = getString(R.string.addfilter);
        setUpToolbar(binding.toolbar, title, R.color.white, R.color.black);
        binding.setLang(getLang());
         activityAddFilterMvvm = ViewModelProviders.of(this).get(ActivityAddFilterMvvm.class);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        addFlterModel = new AddFlterModel();
        activityAddFilterMvvm.save.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(AddFilterActivity.this, getResources().getString(R.string.filter_suc),Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        filterCandleModelList = new ArrayList<>();
        addFlterModel.setWater_type("medium");
        addFlterModel.setCandle_number(3);
        thirdFilter();
        watertype = new ArrayList<>();
        watertype.add("bad");
        watertype.add("medium");
        watertype.add("good");
binding.fldatefirst.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        datetype=1;
        datePickerDialog.show(getSupportFragmentManager(), "");
    }
});
        binding.fldateseconed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=2;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.fldateth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=3;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.fldatefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=4;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.flfifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=5;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.fldatesix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=6;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.fldateseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=7;
                datePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        binding.arrowhighwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (watertype.indexOf(addFlterModel.getWater_type()) == watertype.size() - 1) {
                    addFlterModel.setWater_type(watertype.get(0));
                    binding.tvWaterType.setText(getResources().getString(R.string.low));

                } else {
                    addFlterModel.setWater_type(watertype.get(watertype.indexOf(addFlterModel.getWater_type()) + 1));
                    if (addFlterModel.getWater_type().equals("medium")) {
                        binding.tvWaterType.setText(getResources().getString(R.string.medium));

                    } else {
                        binding.tvWaterType.setText(getResources().getString(R.string.good));

                    }
                }
            }
        });
        binding.arrowlowwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (watertype.indexOf(addFlterModel.getWater_type()) == 0) {
                    addFlterModel.setWater_type(watertype.get(watertype.size() - 1));
                    binding.tvWaterType.setText(getResources().getString(R.string.good));

                } else {
                    addFlterModel.setWater_type(watertype.get(watertype.indexOf(addFlterModel.getWater_type()) - 1));
                    if (addFlterModel.getWater_type().equals("medium")) {
                        binding.tvWaterType.setText(getResources().getString(R.string.medium));

                    } else {
                        binding.tvWaterType.setText(getResources().getString(R.string.low));

                    }
                }
            }
        });
        binding.arrowhighfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addFlterModel.getCandle_number() == 3) {
                    addFlterModel.setCandle_number(5);
                    binding.tvNum.setText("5");
                    fifeFilter();
                } else if (addFlterModel.getCandle_number() == 5) {
                    addFlterModel.setCandle_number(7);
                    binding.tvNum.setText("7");
                    sevenFilter();
                } else if (addFlterModel.getCandle_number() == 7) {
                    addFlterModel.setCandle_number(3);
                    binding.tvNum.setText("3");
                    thirdFilter();
                }

            }
        });
        binding.arrowlowfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addFlterModel.getCandle_number() == 3) {
                    addFlterModel.setCandle_number(7);
                    binding.tvNum.setText("7");
                    sevenFilter();
                } else if (addFlterModel.getCandle_number() == 5) {
                    addFlterModel.setCandle_number(3);
                    binding.tvNum.setText("3");
                    thirdFilter();
                } else if (addFlterModel.getCandle_number() == 7) {
                    addFlterModel.setCandle_number(5);
                    binding.tvNum.setText("5");
                    sevenFilter();
                }
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int complete=1;
                for(int i=0;i<filterCandleModelList.size();i++){
                    if(filterCandleModelList.get(i).getCandle_number()==0){
                        complete=0;
                    }
                }
                if(complete==1) {
                    addFlterModel.setUser_id(Integer.parseInt(getUserModel().getData().getId()));
                    addFlterModel.setFilter_candle(filterCandleModelList);
                    activityAddFilterMvvm.addFilter(AddFilterActivity.this, addFlterModel);
                }
                else {
                    Toast.makeText(AddFilterActivity.this,getResources().getString(R.string.ch_date),Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.tvDate1.setText(getResources().getString(R.string.day));
                    binding.tvDate2.setText(getResources().getString(R.string.day));
                    binding.tvDate3.setText(getResources().getString(R.string.day));
                    binding.tvDate4.setText(getResources().getString(R.string.day));
                    binding.tvDate5.setText(getResources().getString(R.string.day));
                    binding.tvDate6.setText(getResources().getString(R.string.day));
                    binding.tvDate7.setText(getResources().getString(R.string.day));
                    for(int i=0;i<filterCandleModelList.size();i++){
                        filterCandleModelList.set(i,new FilterCandleModel());


                    }
                } else {
                    binding.tvDate1.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate2.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate3.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate4.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate5.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate6.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    binding.tvDate7.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                    for(int i=0;i<filterCandleModelList.size();i++){
                        FilterCandleModel filterCandleModel=new FilterCandleModel();
                        filterCandleModel.setCandle_number(i+1);
                        filterCandleModel.setLast_clean_time(dateFormat.format(new Date(System.currentTimeMillis())));
                        filterCandleModelList.set(i,filterCandleModel);

                    }
                }
            }
        });
        createDateDialog();
binding.tvSkip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        navigteToHomeActivity();
    }
});

    }

    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog = DatePickerDialog.newInstance(AddFilterActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.grey4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setMaxDate(calendar);
        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);

    }

    public void thirdFilter() {
        binding.tvDate1.setText(getResources().getString(R.string.day));
        binding.tvDate2.setText(getResources().getString(R.string.day));
        binding.tvDate3.setText(getResources().getString(R.string.day));
        binding.tvDate4.setText(getResources().getString(R.string.day));
        binding.tvDate5.setText(getResources().getString(R.string.day));
        binding.tvDate6.setText(getResources().getString(R.string.day));
        binding.tvDate7.setText(getResources().getString(R.string.day));
        filterCandleModelList=new ArrayList<>();
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        binding.llDatefifth.setVisibility(View.GONE);
        binding.llDatefour.setVisibility(View.GONE);
        binding.llDateseven.setVisibility(View.GONE);
        binding.llDatesix.setVisibility(View.GONE);
    }

    public void fifeFilter() {
        binding.tvDate1.setText(getResources().getString(R.string.day));
        binding.tvDate2.setText(getResources().getString(R.string.day));
        binding.tvDate3.setText(getResources().getString(R.string.day));
        binding.tvDate4.setText(getResources().getString(R.string.day));
        binding.tvDate5.setText(getResources().getString(R.string.day));
        binding.tvDate6.setText(getResources().getString(R.string.day));
        binding.tvDate7.setText(getResources().getString(R.string.day));
        filterCandleModelList=new ArrayList<>();
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        binding.llDatefifth.setVisibility(View.VISIBLE);
        binding.llDatefour.setVisibility(View.VISIBLE);
        binding.llDateseven.setVisibility(View.GONE);
        binding.llDatesix.setVisibility(View.GONE);
    }

    public void sevenFilter() {
        binding.tvDate1.setText(getResources().getString(R.string.day));
        binding.tvDate2.setText(getResources().getString(R.string.day));
        binding.tvDate3.setText(getResources().getString(R.string.day));
        binding.tvDate4.setText(getResources().getString(R.string.day));
        binding.tvDate5.setText(getResources().getString(R.string.day));
        binding.tvDate6.setText(getResources().getString(R.string.day));
        binding.tvDate7.setText(getResources().getString(R.string.day));
        filterCandleModelList=new ArrayList<>();
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        filterCandleModelList.add(new FilterCandleModel());
        binding.llDatefifth.setVisibility(View.VISIBLE);
        binding.llDatefour.setVisibility(View.VISIBLE);
        binding.llDateseven.setVisibility(View.VISIBLE);
        binding.llDatesix.setVisibility(View.VISIBLE);

    }

    private void navigteToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        FilterCandleModel filterCandleModel=new FilterCandleModel();
        if (datetype == 1) {
            binding.tvDate1.setText(date);
            filterCandleModel.setCandle_number(1);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(0,filterCandleModel);
        } else if (datetype == 2) {
            filterCandleModel.setCandle_number(2);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(1,filterCandleModel);
            binding.tvDate2.setText(date);
        } else if (datetype == 3) {
            filterCandleModel.setCandle_number(3);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(2,filterCandleModel);
            binding.tvDate3.setText(date);
        } else if (datetype == 4) {
            filterCandleModel.setCandle_number(4);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(3,filterCandleModel);
            binding.tvDate4.setText(date);
        } else if (datetype == 5) {
            filterCandleModel.setCandle_number(5);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(4,filterCandleModel);
            binding.tvDate5.setText(date);
        } else if (datetype == 6) {
            filterCandleModel.setCandle_number(6);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(5,filterCandleModel);
            binding.tvDate6.setText(date);
        } else if (datetype == 7) {
            filterCandleModel.setCandle_number(7);
            filterCandleModel.setLast_clean_time(date);
            filterCandleModelList.set(6,filterCandleModel);
            binding.tvDate7.setText(date);
        }
    }

}