package com.example.user.swu.likelion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;


public class ModifyFragment extends DialogFragment{

    private static final String TAG = ModifyFragment.class.getSimpleName();

    public ModifyFragment(){}

    String hour;
    String minute;
    String day;


    public interface ModifytListener{
        void onFInishModify(String hour, String minute, String day);
    }

    private ModifytListener listener;

    Button btnOK;
    Button btnNO;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (ModifytListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify,container);

        TimePicker timePicker = view.findViewById(R.id.timePicker);
        NumberPicker datePicker = view.findViewById(R.id.datePicker);

        final String dates[] = { "평일","토요일","공휴일"};

        datePicker.setMinValue(0);
        datePicker.setMaxValue(dates.length - 1);
        datePicker.setDisplayedValues(dates);
        datePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        //hour = String.valueOf(timePicker.getHour());
        //minute = String.valueOf(timePicker.getMinute());
        //day = dates[datePicker.getValue()];

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                hour=String.valueOf(h);
                minute = String.valueOf(m);
                //Toast.makeText(getContext(), "h=>"+hour+", m=>"+minute, Toast.LENGTH_SHORT).show();

            }
        });
        
        datePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(getContext(), "Value=>"+dates[newVal], Toast.LENGTH_SHORT).show();
                day = dates[newVal];
            }
        });



        btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"hour=======>"+hour+", minute========>"+minute+", listener=====>"+listener.toString());
                listener.onFInishModify(hour, minute, day);
                ModifyFragment.this.dismiss();
            }
        });

        btnNO = view.findViewById(R.id.btnNO);
        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyFragment.this.dismiss();
            }
        });

        return view;
    }
}
