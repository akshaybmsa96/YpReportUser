package yp.com.akki.ypreport.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import yp.com.akki.ypreport.activity.ReportActivity;


/**
 * Created by akshaybmsa96 on 18/01/18.
 */

public class DatePickerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp=new DatePickerDialog(getActivity(),(ReportActivity)getActivity(), year, month, day);
        long now = System.currentTimeMillis() - 1000;
        dp.getDatePicker().setMaxDate(now);
        dp.getDatePicker().setMinDate(now-(1000*60*60*24*7));


// Create a new instance of DatePickerDialog and return it
        return dp;
    }
}
