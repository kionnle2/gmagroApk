package yan.candaes.gmagro.tools;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yan.candaes.gmagro.ui.MainActivity;

public class DateAndTimePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Context ctx;
    private TextView tv;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    public DateAndTimePicker(Context context, TextView textView) {
        this.ctx = context;
        this.tv = textView;
    }

    public void dateTimeAsk() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, this::onDateSet, year, month, day);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ctx, this::onTimeSet, hour, minute, DateFormat.is24HourFormat(ctx));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;


        try {
            String date = myYear + "-" + (myMonth + 1) + "-" + myday + " " + myHour + ":" + myMinute;
            Date selDateandTime = Tools.sdfEN.parse(date);



            if ((new Date()).after(selDateandTime)) {
                tv.setText(Tools.sdfFR.format(selDateandTime));
            } else {
                Toast.makeText(ctx, "la date n'est pas encore passée", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}