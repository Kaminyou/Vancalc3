package com.kaminyou.vancalc3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    public void button_Click(View view){
        int age = 50, gender = 0, dose = 1000;
        double weight = 50, height = 0, crea = 0.8, freq = 12 ,vd_vanco ,vd = 0.7; //weight_for_vanco
        double ccr, cssp, csspeak, csstrough, k, half;
        RadioButton rdbMale, rdbFemale;
        EditText txtAge = (EditText) findViewById(R.id.txtAge);
        EditText txtWeight = (EditText) findViewById(R.id.txtWeight);
        EditText txtHeight = (EditText) findViewById(R.id.txtHeight);
        EditText txtCrea = (EditText) findViewById(R.id.txtCrea);
        EditText txtVancodose = (EditText) findViewById(R.id.txtVancodose);
        EditText txtVancofreq = (EditText) findViewById(R.id.txtVancofreq);

        TextView txtCcr = (TextView) findViewById(R.id.txtCcr);
        TextView txtP = (TextView) findViewById(R.id.txtP);
        TextView txtPeak = (TextView) findViewById(R.id.txtPeak);
        TextView txtTrough = (TextView) findViewById(R.id.txtTrough);
        TextView txtK = (TextView) findViewById(R.id.txtK);
        TextView txtT = (TextView) findViewById(R.id.txtT);
        TextView txtOther = (TextView) findViewById(R.id.txtOther);

        if (isEmpty(txtAge)){
            txtOther.setText("Not Enough Data");
            return;
        }
        else {
            age = Integer.parseInt(txtAge.getText().toString());
        }

        if (isEmpty(txtWeight)){
            txtOther.setText("Not Enough Data");
            return;
        }
        else {
            weight = Double.parseDouble(txtWeight.getText().toString());
        }
        //weight_for_vanco = weight;

        if (isEmpty(txtHeight)){
            height = 0;
        }
        else {
            height = Double.parseDouble(txtHeight.getText().toString());
        }

        if (isEmpty(txtCrea)){
            txtOther.setText("Not Enough Data");
            return;
        }
        else {
            crea = Double.parseDouble(txtCrea.getText().toString());
            txtOther.setText("None");
        }

        if (isEmpty(txtVancofreq)){
            freq = 0;
        }
        else {
            freq = Double.parseDouble(txtVancofreq.getText().toString());
        }

        if (isEmpty(txtVancodose)){
            dose = 0;
        }
        else {
            dose = Integer.parseInt(txtVancodose.getText().toString());
        }
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
        ccr = ((140 - age) * weight / 72 / crea) - ((140-age) * weight / 72 / crea * 0.15 * gender);

        // vanco
        vd_vanco = weight* vd;
        ccr = ccr / 1000 * 60;
        k = ccr / vd_vanco;
        cssp = dose / vd_vanco * (1/(1 - (Math.exp(-k * freq))));
        csspeak = cssp * (Math.exp(-k*2));
        csstrough = cssp * (Math.exp(-k*(freq)));
        half = 0.693/k;

        //output
        txtCcr.setText(String.format("%.3f", ccr/0.06));
        txtP.setText(String.format("%.3f", cssp));
        txtPeak.setText(String.format("%.3f", csspeak));
        txtTrough.setText(String.format("%.3f", csstrough));
        txtK.setText(String.format("%.3f", k));
        txtT.setText(String.format("%.3f", half));

    }
}
