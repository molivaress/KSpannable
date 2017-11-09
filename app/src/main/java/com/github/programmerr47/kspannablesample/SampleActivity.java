package com.github.programmerr47.kspannablesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.programmerr47.kspannable.Sample;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Sample.INSTANCE.fuck();
    }
}
