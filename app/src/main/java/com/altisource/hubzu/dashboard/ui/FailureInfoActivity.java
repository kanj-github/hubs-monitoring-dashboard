package com.altisource.hubzu.dashboard.ui;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.failure_info_title);
        }

        if (savedInstanceState != null) {
            stage = savedInstanceState.getString(EXTRA_STAGE, null);
            error = savedInstanceState.getString(EXTRA_ERROR);
            stack = savedInstanceState.getString(EXTRA_STACK);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                stage = extras.getString(EXTRA_STAGE, null);
                error = extras.getString(EXTRA_ERROR);
                stack = extras.getString(EXTRA_STACK);
            }
        }

        TextView stageTv = ((TextView) findViewById(R.id.stage));
        if (stage != null) {
            stageTv.setText(stage);
        } else {
            // Remove stage section from view
            stageTv.setVisibility(View.GONE);
            findViewById(R.id.step_label).setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.error)).setText(error);
        ((TextView) findViewById(R.id.stack)).setText(stack);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
