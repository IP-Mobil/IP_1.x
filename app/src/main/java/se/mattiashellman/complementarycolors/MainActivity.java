package se.mattiashellman.complementarycolors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, SeekBar.OnSeekBarChangeListener, ColorLogic.MidwayBrightnessListener {

    private ColorLogic colorLogic;
    static final String STATE_COLOR = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.editR)).setOnEditorActionListener(this);
        ((TextView) findViewById(R.id.editG)).setOnEditorActionListener(this);
        ((TextView) findViewById(R.id.editB)).setOnEditorActionListener(this);
        ((SeekBar) findViewById(R.id.seekR)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.seekG)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.seekB)).setOnSeekBarChangeListener(this);

        if (savedInstanceState != null) {
            colorLogic = new ColorLogic(savedInstanceState.getInt(STATE_COLOR));
        } else {
            colorLogic = new ColorLogic(getResources().getInteger(R.integer.default_r),
                    getResources().getInteger(R.integer.default_g),
                    getResources().getInteger(R.integer.default_b));
        }

        colorLogic.setMidwayBrightnessListener(this);

        updateColorDisplay();
        updateCompColorDisplay();
    }

    /**
     * Instigates updates to the color display on progressbar changes
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekR :
                colorLogic.setRed(progress);
                break;
            case R.id.seekG :
                colorLogic.setGreen(progress);
                break;
            case R.id.seekB :
                colorLogic.setBlue(progress);
                break;
        }
        updateColorDisplay();
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // do nothing
    }

    /**
     * Instigates updates to the complementary color display on progressbar releases
     */
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateCompColorDisplay();
    }

    /**
     * Handles RGB text input changes
     */
    public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {
        int progress = Integer.parseInt(v.getText().toString());
        switch (v.getId()) {
            case R.id.editR :
                ((SeekBar) findViewById(R.id.seekR)).setProgress(progress);
                break;
            case R.id.editG :
                ((SeekBar) findViewById(R.id.seekG)).setProgress(progress);
                break;
            case R.id.editB :
                ((SeekBar) findViewById(R.id.seekB)).setProgress(progress);
                break;
        }

        updateCompColorDisplay();

        return false;
    }

    /**
     * Updates color display & text
     */
    void updateColorDisplay() {
        ((ImageView) findViewById(R.id.color)).setColorFilter(colorLogic.getColor());

        ((EditText) findViewById(R.id.editR)).setText(String.valueOf(colorLogic.getRed()));
        ((EditText) findViewById(R.id.editG)).setText(String.valueOf(colorLogic.getGreen()));
        ((EditText) findViewById(R.id.editB)).setText(String.valueOf(colorLogic.getBlue()));
    }

    /**
     * Updates complementary color display & text
     */
    void updateCompColorDisplay() {
        ((ImageView) findViewById(R.id.compColor)).setColorFilter(colorLogic.getCompColor());

        ((TextView) findViewById(R.id.textCompR)).setText(String.valueOf(colorLogic.getCompRed()));
        ((TextView) findViewById(R.id.textCompG)).setText(String.valueOf(colorLogic.getCompGreen()));
        ((TextView) findViewById(R.id.textCompB)).setText(String.valueOf(colorLogic.getCompBlue()));
    }

    /**
     * Changes complementary color text to white when background is dark
     */
    public void onDarkThreshold() {
        ((TextView) findViewById(R.id.textCompR)).setTextColor(getResources().getColor(android.R.color.white, null));
        ((TextView) findViewById(R.id.textCompG)).setTextColor(getResources().getColor(android.R.color.white, null));
        ((TextView) findViewById(R.id.textCompB)).setTextColor(getResources().getColor(android.R.color.white, null));

        ((TextView) findViewById(R.id.textCompLabR)).setTextColor(getResources().getColor(android.R.color.white, null));
        ((TextView) findViewById(R.id.textCompLabG)).setTextColor(getResources().getColor(android.R.color.white, null));
        ((TextView) findViewById(R.id.textCompLabB)).setTextColor(getResources().getColor(android.R.color.white, null));
    }

    /**
     * Changes complementary color text to black when background is bright
     */
    public void onLightThreshold() {
        ((TextView) findViewById(R.id.textCompR)).setTextColor(getResources().getColor(android.R.color.black, null));
        ((TextView) findViewById(R.id.textCompG)).setTextColor(getResources().getColor(android.R.color.black, null));
        ((TextView) findViewById(R.id.textCompB)).setTextColor(getResources().getColor(android.R.color.black, null));

        ((TextView) findViewById(R.id.textCompLabR)).setTextColor(getResources().getColor(android.R.color.black, null));
        ((TextView) findViewById(R.id.textCompLabG)).setTextColor(getResources().getColor(android.R.color.black, null));
        ((TextView) findViewById(R.id.textCompLabB)).setTextColor(getResources().getColor(android.R.color.black, null));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_COLOR, colorLogic.getColor());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ((ImageView) findViewById(R.id.compColor)).setColorFilter(colorLogic.getCompColor());
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    */
}