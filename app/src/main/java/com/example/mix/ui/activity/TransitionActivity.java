package com.example.mix.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mix.R;

public class TransitionActivity extends AppCompatActivity {

    private ViewDataBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

    }
    public void onClick(View view) {
       startActivity(new Intent(this,MainActivity.class));
       finish();
    }
}
