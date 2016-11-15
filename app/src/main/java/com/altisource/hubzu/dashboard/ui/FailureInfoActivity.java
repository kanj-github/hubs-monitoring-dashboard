package com.altisource.hubzu.dashboard.ui;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
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
