package com.example.sensitivityfinder;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class calculation {
    static final double ZoomSens = 37.89;
    static final double AsheMulti = 1.356555;
    static final double cmVal = 0.39370;
    static DecimalFormat df = new DecimalFormat("0.000");


    public static void findscope(EditText dpi, EditText sens, EditText curScope, EditText newScope, CheckBox chkbtn, ArrayList alist, ArrayAdapter adapter, Context context) {
        if (!TextUtils.isEmpty(dpi.getText().toString().trim()) && !TextUtils.isEmpty(sens.getText().toString().trim()) && !TextUtils.isEmpty(curScope.getText().toString().trim()) && !TextUtils.isEmpty(newScope.getText().toString().trim())) {
            alist.clear();
            //adapter.notifyDataSetChanged();
            double edpi = Double.parseDouble(dpi.getText().toString()) * Double.parseDouble(sens.getText().toString()) * Double.parseDouble(curScope.getText().toString());
            double result = edpi / Double.parseDouble(dpi.getText().toString()) / Double.parseDouble(newScope.getText().toString());
            if (!chkbtn.isChecked()) {
                alist.add("New Scope Sens. = " + df.format(result));
                alist.add("DPI = " + dpi.getText().toString());
                alist.add("Sensitivity = " + newScope.getText().toString());
                adapter.notifyDataSetChanged();

            } else if (chkbtn.isChecked()) {
                result = (result * AsheMulti);
                alist.add("New Scope Sens. Ashe = " + df.format(result));
                alist.add("DPI = " + dpi.getText().toString());
                alist.add("Sensitivity = " + newScope.getText().toString());
                adapter.notifyDataSetChanged();
            }
        } else
            showToast(context, "Find Sens. must not be checked or New Scope or Sensitivity is not blank");

    }

    public static void findscopetosens(EditText dpi, EditText sens, EditText curScope, EditText newScope, CheckBox chkbtn, ArrayList alist, ArrayAdapter adapter, Context context) {

        if (!TextUtils.isEmpty(dpi.getText().toString().trim()) && !TextUtils.isEmpty(sens.getText().toString().trim()) &&
                !TextUtils.isEmpty(curScope.getText().toString().trim()) && TextUtils.isEmpty(newScope.getText().toString().trim())) {
            alist.clear();
            adapter.notifyDataSetChanged();
            double edpi = Double.parseDouble(dpi.getText().toString()) * Double.parseDouble(sens.getText().toString()) * Double.parseDouble(curScope.getText().toString());
            double result = edpi / Double.parseDouble(dpi.getText().toString()) / ZoomSens;
            if (!chkbtn.isChecked()) {
                alist.add("Scope Sensitivity = 37.89");
                alist.add("DPI = " + dpi.getText().toString());
                alist.add("New Sensitivity = " + df.format(result));
                adapter.notifyDataSetChanged();

            } else if (chkbtn.isChecked()) {
                alist.add("Scope Sensitivity = 51.399");
                alist.add("DPI = " + dpi.getText().toString());
                alist.add("New Sensitivity Ashe = " + df.format(result));
                adapter.notifyDataSetChanged();
            }
        } else
            showToast(context, "New Scope or Sensitivity field must be blank.");

    }

    // find cm per 360
    public static void findcm360(EditText dpi, EditText sens, EditText yaw, ArrayList alist, ArrayAdapter adapter, Context context) {
        double edpi = 0;
        double result = 0;
        if (!TextUtils.isEmpty(dpi.getText().toString().trim()) && !TextUtils.isEmpty(sens.getText().toString().trim()) && !TextUtils.isEmpty(yaw.getText().toString().trim())) {
            alist.clear();
            adapter.notifyDataSetChanged();
            edpi = Double.parseDouble(dpi.getText().toString()) * Double.parseDouble(sens.getText().toString()) * Double.parseDouble(yaw.getText().toString());
            result = (360 / edpi) / 0.39370;
            alist.add(df.format(result) + " cm/360");
            adapter.notifyDataSetChanged();
        } else
            showToast(context, "DPI, Sensitivity, and Yaw must be filled in.");
    }

    // find sensitivity from distance
    public static void findsensfromdistance(EditText dpi, EditText yaw, EditText distance, ArrayList alist, ArrayAdapter adapter, Context context) {
        double result = 0;
        if (!TextUtils.isEmpty(dpi.getText().toString().trim()) && !TextUtils.isEmpty(distance.getText().toString().trim()) && !TextUtils.isEmpty(yaw.getText().toString().trim())) {
            alist.clear();
            adapter.notifyDataSetChanged();
            result = (360 / Double.parseDouble(dpi.getText().toString()) / Double.parseDouble(yaw.getText().toString()) / (Double.parseDouble(distance.getText().toString()) * cmVal));
            alist.add(df.format(result));
            adapter.notifyDataSetChanged();
        } else
            showToast(context, "DPI, cm/360, and Yaw must be filled in.");
    }

    // find sensitivity
    public static void findSens(EditText dpi, EditText sens, ArrayList alist, ArrayAdapter adapter, Context context) {
        double result = 0;
        double edpi = 0;

        if (!TextUtils.isEmpty(dpi.getText().toString().trim()) && !TextUtils.isEmpty(sens.getText().toString().trim())) {
            alist.clear();
            adapter.notifyDataSetChanged();
            edpi = Double.parseDouble(dpi.getText().toString()) * Double.parseDouble(sens.getText().toString());
            for (int i = 100; i <= 12000; ) {
                while (i < 1000) {
                    result = edpi / i;
                    alist.add(" " + i + " = " + df.format(result));
                    adapter.notifyDataSetChanged();
                    i = i + 100;
                }
                result = edpi / i;
                alist.add(i + " = " + df.format(result));
                adapter.notifyDataSetChanged();
                i = i + 100;
                if (result <= 1.0) {
                    break;
                }
            }
        } else
            showToast(context, "DPI and Sensitivity must be filled in.");
    }

    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}

