package com.example.android.uamp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.uamp.R;

public class profile_upload extends BaseActivity {


    // Android Components
    TextView name;
    TextView displayName;
    TextView email;
    TextView location;
    TextView gender;
    TextView language;
    TextView country;
    ImageView image;

    // Variables
    String provider_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_upload);
    }

}
