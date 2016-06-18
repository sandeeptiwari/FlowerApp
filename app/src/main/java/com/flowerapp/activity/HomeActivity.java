package com.flowerapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.flowerapp.R;
import com.flowerapp.bean.Result;
import com.flowerapp.fragment.ImageGroupFragment;

/**
 * Created by Sandeep Tiwari on 6/16/2016.
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        if (findViewById(R.id.ui_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ImageGroupFragment imageGroupFragment = new ImageGroupFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            Bundle bundle = getIntent().getExtras();
            imageGroupFragment.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.ui_container, imageGroupFragment).commit();
        }
    }
}
