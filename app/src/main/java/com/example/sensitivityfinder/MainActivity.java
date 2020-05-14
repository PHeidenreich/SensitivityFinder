package com.example.sensitivityfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCLEAR, btnCALCULATE;
    EditText txtINPUTDPI, txtINPUTCM360, txtINPUTSENS, txtINPUTYAW, txtINPUTCS, txtINPUTNS;
    ListView list;
    CheckBox cbSENS, cbASHE;
    RadioButton rbtnSENSALLDPI, rbtnCM360, rbtnSENSCM360, rbtnRSS;
    RadioGroup radioGroup4;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnCLEAR = (Button) findViewById(R.id.btnCLEAR);
        txtINPUTDPI = (EditText) findViewById(R.id.txtINPUTDPI);
        txtINPUTSENS = (EditText) findViewById(R.id.txtINPUTSENS);
        txtINPUTCM360 = (EditText) findViewById(R.id.txtINPUTCM360);
        txtINPUTYAW = (EditText) findViewById(R.id.txtINPUTYAW);
        txtINPUTCS = (EditText) findViewById(R.id.txtINPUTCS);
        txtINPUTNS = (EditText) findViewById(R.id.txtINPUTNS);

        cbSENS = (CheckBox) findViewById(R.id.cbSENS);
        cbSENS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                txtINPUTNS.setText(null);

            }
        });
        cbASHE = (CheckBox) findViewById(R.id.cbASHE);
        list = (ListView) findViewById(R.id.list);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        rbtnSENSALLDPI = (RadioButton) findViewById(R.id.rbtnSENSALLDPI);
        rbtnCM360 = (RadioButton) findViewById(R.id.rbtnCM360);
        rbtnSENSCM360 = (RadioButton) findViewById(R.id.rbtnSENSCM360);
        rbtnRSS = (RadioButton) findViewById(R.id.rbtnRSS);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        list.setAdapter(adapter);

        btnCALCULATE = (Button) findViewById(R.id.btnCALCULATE);
        btnCALCULATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtnSENSALLDPI.isChecked()) {
                    calculation.findSens(txtINPUTDPI, txtINPUTSENS, arrayList, adapter, getApplicationContext());
                } else if (rbtnCM360.isChecked()) {
                    calculation.findcm360(txtINPUTDPI, txtINPUTSENS, txtINPUTYAW, arrayList, adapter, getApplicationContext());
                } else if (rbtnSENSCM360.isChecked()) {
                    calculation.findsensfromdistance(txtINPUTDPI, txtINPUTYAW, txtINPUTCM360, arrayList, adapter, getApplicationContext());
                } else if (rbtnRSS.isChecked()) {
                    if (cbSENS.isChecked()) {
                        calculation.findscopetosens(txtINPUTDPI, txtINPUTSENS, txtINPUTCS, txtINPUTNS, cbASHE, arrayList, adapter, getApplicationContext());
                    } else
                        calculation.findscope(txtINPUTDPI, txtINPUTSENS, txtINPUTCS, txtINPUTNS, cbASHE, arrayList, adapter, getApplicationContext());
                }
            }
        });

        btnCLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
