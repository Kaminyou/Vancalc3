package com.kaminyou.vancalc3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mainMode:
                Intent intent = new Intent(CalibrationActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.calibMode:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void button_Click(View view){
        int age = 50, gender = 0, previous_freq = 8, new_dose = 0, new_freq = 0;
        double weight = 50, height = 0, crea = 0.8;
        int pre_peak_time, pre_trough_time;
        double pre_peak, pre_trough;
        double k, ccr = 0, cssp, csspeak, csstrough, css_new_p, css_new_peak, css_new_trough;

        RadioButton rdbMale, rdbFemale;
        EditText txtAge = (EditText) findViewById(R.id.txtAge);
        EditText txtWeight = (EditText) findViewById(R.id.txtWeight);
        EditText txtHeight = (EditText) findViewById(R.id.txtHeight);
        EditText txtCrea = (EditText) findViewById(R.id.txtCrea);
        EditText txtPeak = (EditText) findViewById(R.id.txtPeak);
        EditText txtPeakTime = (EditText) findViewById(R.id.txtPeakTime);
        EditText txtTrough = (EditText) findViewById(R.id.txtTrough);
        EditText txtTroughTime = (EditText) findViewById(R.id.txtTroughTime);
        EditText txtPreFreq = (EditText) findViewById(R.id.txtPreFreq);
        EditText txtNewDose = (EditText) findViewById(R.id.txtNewDose);
        EditText txtNewFreq = (EditText) findViewById(R.id.txtNewFreq);

        TextView txtAK = (TextView) findViewById(R.id.txtAK);
        TextView txtAP = (TextView) findViewById(R.id.txtAP);
        TextView txtAPeak = (TextView) findViewById(R.id.txtAPeak);
        TextView txtATrough = (TextView) findViewById(R.id.txtATrough);
        TextView txtANP = (TextView) findViewById(R.id.txtANP);
        TextView txtANPeak = (TextView) findViewById(R.id.txtANPeak);
        TextView txtANTrough = (TextView) findViewById(R.id.txtANTrough);
        TextView txtOther = (TextView) findViewById(R.id.txtOther);

        // check age
        if (isEmpty(txtAge)){
            age = 0;
        }
        else {
            age = Integer.parseInt(txtAge.getText().toString());
        }

        // check weight
        if (isEmpty(txtWeight)){
            weight = 0;
        }
        else {
            weight = Double.parseDouble(txtWeight.getText().toString());
        }

        // check height
        if (isEmpty(txtHeight)){
            height = 0;
        }
        else {
            height = Double.parseDouble(txtHeight.getText().toString());
        }

        // check crea
        if (isEmpty(txtCrea)){
            crea = 0;
        }
        else {
            crea = Double.parseDouble(txtCrea.getText().toString());
            txtOther.setText("None");
        }

        // check peak
        if (isEmpty(txtPeak)){
            txtOther.setText("No Enough Data");
            return;
        }
        else {
            pre_peak = Double.parseDouble(txtPeak.getText().toString());
        }

        // check peak time
        if (isEmpty(txtPeakTime)){
            txtOther.setText("No Enough Data");
            return;
        }
        else {
            pre_peak_time = Integer.parseInt(txtPeakTime.getText().toString());
        }

        // check trough
        if (isEmpty(txtTrough)){
            txtOther.setText("No Enough Data");
            return;
        }
        else {
            pre_trough = Double.parseDouble(txtTrough.getText().toString());
        }

        // check trough time
        if (isEmpty(txtTroughTime)){
            txtOther.setText("No Enough Data");
            return;
        }
        else {
            pre_trough_time = Integer.parseInt(txtTroughTime.getText().toString());
        }

        // check pre freq
        if (isEmpty(txtPreFreq)){
            previous_freq = 0;
        }
        else {
            previous_freq = Integer.parseInt(txtPreFreq.getText().toString());
        }

        // check new dose
        if (isEmpty(txtNewDose)){
            new_dose = 0;
        }
        else {
            new_dose = Integer.parseInt(txtNewDose.getText().toString());
        }

        // check new freq
        if (isEmpty(txtNewFreq)){
            new_freq = 0;
        }
        else {
            new_freq = Integer.parseInt(txtNewFreq.getText().toString());

        }

        // reset note
        txtOther.setText("None");

        // check two point
        if (pre_trough > pre_peak){
            txtOther.setText("ERROR: trough > peak");
            return;
        }
        if (pre_trough_time < pre_peak_time){
            txtOther.setText("ERROR: trough time < peak time");
            return;
        }

        // decide gender
        rdbMale = (RadioButton) findViewById(R.id.rdgMale);
        rdbFemale = (RadioButton) findViewById(R.id.rdgFemale);

        // deal with crea
        if (crea < 0.8){
            crea = 0.8;
        }

        // deal with gender
        if (rdbMale.isChecked()){
            gender = 0;
        }
        if (rdbFemale.isChecked()){
            gender = 1;
        }

        // deal with given height
        double IBW=0, ADJW = 0;
        if (height > 0){

            // IBW
            if (gender == 0){
                IBW = (height-80)*0.7;
            }
            else{
                IBW = (height-70)*0.6;
            }

            //Under
            if (weight <= IBW){
                txtOther.setText("Pt is underweight: use original wt");
            }
            else if (weight > IBW*1.2){
                ADJW = IBW + 0.4*(weight-IBW);
                weight = ADJW;
                txtOther.setText("Pt is overweight: use adjusted wt: "+ String.format("%.3f", ADJW));
            }
            else{
                weight = IBW;
                txtOther.setText("Pt is in normal wt: use ideal wt: "+ String.format("%.3f", IBW));
            }
        }

        // calc ccr
        if (weight >0){
            ccr = ((140 - age) * weight / 72 / crea) - ((140-age) * weight / 72 / crea * 0.15 * gender);
        }

        // cal mode 1
        //k, ccr, cssp, csspeak, csstrough, css_new_p, css_new_peak, css_new_trough;
        k = (Math.log(pre_peak) - Math.log(pre_trough))/(pre_trough_time - pre_peak_time);
        cssp = pre_peak / (Math.exp(-pre_peak_time * k));
        csspeak = (pre_peak / (Math.exp(-pre_peak_time * k))) * Math.exp(-2*k);

        txtAK.setText(String.format("%.3f", k));
        txtAP.setText(String.format("%.3f", cssp));
        txtAPeak.setText(String.format("%.3f", csspeak));

        if (previous_freq > 0) {
            csstrough = (pre_peak / (Math.exp(-pre_peak_time * k))) * Math.exp(-k * previous_freq);
            txtATrough.setText(String.format("%.3f", csstrough));
        }

        // new dose mode
        if ((age >0) && (weight >0) &&(crea >0) &&(new_dose>0) && (new_freq>0)){
            double vd;
            ccr = ccr / 1000 * 60;
            vd = ccr / k;
            css_new_p = new_dose / vd * (1/(1 - (Math.exp(-k * new_freq))));
            css_new_peak = css_new_p * (Math.exp(-k*2));
            css_new_trough = css_new_p * (Math.exp(-k*(new_freq)));

            txtANP.setText(String.format("%.3f", css_new_p));
            txtANPeak.setText(String.format("%.3f", css_new_peak));
            txtANTrough.setText(String.format("%.3f", css_new_trough));

        }
    }
}
