package com.rflournoy.burnedcaloriescalculator;



import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View.OnKeyListener;
import android.content.SharedPreferences;
import android.app.Activity;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;



public class MainActivity extends Activity implements OnItemSelectedListener, OnEditorActionListener, OnKeyListener {


    // define variables for the widgets
    private Spinner feetSpinner;
    private Spinner inchSpinner;
    private SeekBar milesSeekBar;
    private EditText weightEditText;
    private TextView milesRunTextView;
    private TextView bmiResultsTextView;
    private TextView caloriesBurnedResultTextView;

    private int feet;
    private int inches;
    private float weight;
    private int seekBarProgress;
    private int milesRun;
    private float caloriesBurned;
    private float bmiAdjustment;
    private String weightString;




    // define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        milesRunTextView = (TextView) findViewById(R.id.milesRunTextView);
        caloriesBurnedResultTextView = (TextView) findViewById(R.id.caloriesBurnedResultTextView);
        bmiResultsTextView = (TextView) findViewById(R.id.bmiResultsTextView);
        feetSpinner = (Spinner) findViewById(R.id.feetSpinner);
        inchSpinner = (Spinner) findViewById(R.id.inchSpinner);
        milesSeekBar = (SeekBar) findViewById(R.id.milesSeekBar);

        //Set listeners
        weightEditText.setOnEditorActionListener(this);
        weightEditText.setOnKeyListener(this);
        feetSpinner.setOnItemSelectedListener(this);
        inchSpinner.setOnItemSelectedListener(this);

        //Use an inner anonymous class for seekbar event listener
        milesSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarProgress = milesSeekBar.getProgress();
                String seekBarParsed = String.valueOf(seekBarProgress);
                milesRunTextView.setText(seekBarParsed + " miles");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calculateAndDisplay();
            }
        });

        // set array adapters for feetspinner
        ArrayAdapter<CharSequence> feetAdapter = ArrayAdapter.createFromResource(
                this, R.array.feetSpinner, android.R.layout.simple_spinner_item);
        feetAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        feetSpinner.setAdapter(feetAdapter);

        // set array adapters for inchspinner
        ArrayAdapter<CharSequence> inchAdapter = ArrayAdapter.createFromResource(
                this, R.array.inchSpinner, android.R.layout.simple_spinner_item);
        feetAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        inchSpinner.setAdapter(inchAdapter);

    }

    public void calculateAndDisplay(){

        weightString = weightEditText.getText().toString();
        if (weightString.equals("")) {
            weight = 0;
        }
        else {
            weight = Float.parseFloat(weightString);
        }

        //Establish miles run
        milesRun = milesSeekBar.getProgress();

        //Calculate calories and BMI
        caloriesBurned =  (float) 0.75 * weight * milesRun;
        bmiAdjustment = (int)((weight * 703) / ((12 * feet + inches) * (12 * feet + inches)));

        //Display calories and BMI
        caloriesBurnedResultTextView.setText(Float.toString(caloriesBurned));
        bmiResultsTextView.setText(Float.toString((bmiAdjustment)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        feet = feetSpinner.getSelectedItemPosition();
        inches = inchSpinner.getSelectedItemPosition();
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }
}
