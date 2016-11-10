package com.altisource.hubzu.dashboard.ui;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;

public class FailureInfoActivity extends AppCompatActivity {
    public static final String EXTRA_STAGE = "EXTRA_STAGE";
    public static final String EXTRA_ERROR = "EXTRA_ERROR";
    public static final String EXTRA_STACK = "EXTRA_STACK";

    private String stage, error, stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure_info);

        if (savedInstanceState != null) {
            stage = savedInstanceState.getString(EXTRA_STAGE);
            error = savedInstanceState.getString(EXTRA_ERROR);
            stack = savedInstanceState.getString(EXTRA_STACK);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                stage = extras.getString(EXTRA_STAGE);
                error = extras.getString(EXTRA_ERROR);
                stack = extras.getString(EXTRA_STACK);
            }
        }

        ((TextView) findViewById(R.id.stage)).setText(stage);
        ((TextView) findViewById(R.id.error)).setText(error);
        ((TextView) findViewById(R.id.stack)).setText(stack);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(EXTRA_STAGE, stage);
        outState.putString(EXTRA_ERROR, error);
        outState.putString(EXTRA_STACK, stack);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_STAGE, stage);
        outState.putString(EXTRA_ERROR, error);
        outState.putString(EXTRA_STACK, stack);
    }
}
