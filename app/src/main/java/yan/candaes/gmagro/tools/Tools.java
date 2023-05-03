package yan.candaes.gmagro.tools;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Tools {
    public static SimpleDateFormat sdfEN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdfFR = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<String> fillTimeList(ArrayList<String> arraySpinner, boolean withZero) {
        int minute = 0;
        int heure = 0;
        if(withZero)arraySpinner.add("0:0");
        for (int i = 0; i < 32; i++) {
            minute += 15;
            if (minute == 60) {
                minute = 0;
                heure++;
            }

            arraySpinner.add(heure + ":" + minute);
        }
    return arraySpinner;
    }

}