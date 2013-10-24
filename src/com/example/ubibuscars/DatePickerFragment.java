package com.example.ubibuscars;

import java.util.Calendar;

import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment {


	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (AnuncioCaronaActivity) getActivity(), year, month, day);
		
	}



}
