package com.aaronevans.paidtogo.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.Utils.Functions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

/**
 * Created by leandro on 7/11/17.
 */

public class BaseFragment extends Fragment {


    String emailpattren = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean checkAllEditext(EditText editText, EditText et1, EditText et2, EditText et3, EditText et4) {
        if (editText.getText().toString().trim().length() == 0 && et1.getText().toString().trim().length() == 0 && et2.getText().toString().trim().length() == 0 && et3.getText().toString().trim().length() == 0 && et4.getText().toString().trim().length() == 0) {
            editText.setError("Required");
            editText.requestFocus();
            et1.setError("Required");

            et2.setError("Required");

            et3.setError("Required");

            et4.setError("Required");

            return false;

        }
        return true;


    }

    public static boolean isValidMobileNo(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError("Required");
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().trim().length() < 8) {
            editText.setError("Not a Valid Mobile Number");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean CheckEditext(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError("Required");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean CheckTextView(TextView textView) {
        if (TextUtils.isEmpty(textView.getText().toString().trim())) {
            textView.setError("Required");
            textView.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isPasswordSame(EditText editText, EditText editText1) {
        if (!editText.getText().toString().trim().equals(editText1.getText().toString().trim())) {

            editText1.setError("Not Matched");
            editText1.requestFocus();
            return false;
        } else {
            editText.clearFocus();
            editText1.clearFocus();

        }
        return true;

    }

    public static boolean isPasswordGooD(EditText editText) {
        if (editText.getText().toString().trim().length() < 8) {
            editText.setError("Password Length must be greater then or equals eight");
            return false;
        }
        return true;

    }

    public void MakeToast(String msg) {
        try {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        } catch (NullPointerException e) {

        }
    }

    public void toolBarClick(final Context context, Toolbar toolbar, final String checkstatus) {

        ImageView imageView = toolbar.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkstatus.equalsIgnoreCase("")) {

                    int fragments = getChildFragmentManager().getBackStackEntryCount();
                    if (fragments == 1) {
                        getActivity().finish();
                    } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                        getFragmentManager().popBackStack();
                    } else {
                        getActivity().onBackPressed();
                    }
                } else {

                }

            }
        });


    }

    public void startNewActivity(Activity a, Class<? extends Activity> class1, boolean isfinis, Intent intent) {

        try {
            Bundle bundle = null;
            if (intent != null) {
                bundle = intent.getExtras();

            }
            Intent i = new Intent(a, class1);
            if (bundle != null) {
                i.putExtras(bundle);

            }
            a.startActivity(i);
            getActivity().overridePendingTransition(R.anim.left_in, R.anim.right_out);


            if (isfinis) {
                a.finish();


            }


        } catch (ActivityNotFoundException e) {
            MakeToast("Something Went Wrong Please Restart the application!!!!!!!!");
        } catch (Exception e) {
            MakeToast("Something Went Wrong!!!!!!!!");
        }


    }

    public void showToastOnMainThread(final String msg) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }
    }

    public boolean checkInternet() {
        try {
            return Functions.checkInternet(getActivity());
        } catch (NullPointerException e) {
            return false;

        }

    }

    public void showSnackbar(View view, String text, final String from) {
        try {
            final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView snackbarActionTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_action);
            snackbarActionTextView.setTextSize(18);
            snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);
            snackbarActionTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

            TextView textView = (TextView) sbView.findViewById(

                    R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            sbView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (from.equalsIgnoreCase("Reset")) {

                    }
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSnackbarLong(View view, String text, final String from) {
        try {
            final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
            View sbView = snackbar.getView();
            TextView snackbarActionTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_action);
            snackbarActionTextView.setTextSize(18);
            snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);
            snackbarActionTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

            TextView textView = (TextView) sbView.findViewById(

                    R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            sbView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (from.equalsIgnoreCase("Reset")) {

                    }
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkStringISNull(String check) {
        if (check == null) {
            return false;
        } else {
            return true;
        }
    }

    public void showDialog(String msg) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setCancelable(false);
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();


                    }
                }
        );


        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public boolean checkStringNullEmpty(String check) {
        if (check == null || check.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isEMailOk(EditText email) {
        if (!email.getText().toString().trim().matches(emailpattren)) {
            email.setError("Enter valid email");
            email.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getLastDay(String month, String year) {
        String last_date = "";
        String last_day = "";
        String current_month = "";

        try {
            String date = month + "/" + year;
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                Date convertedDate = null;

                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                last_day = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));

                Log.e("Timesheet", "+++MONTH+LAST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                //2020-01-01
                last_date = year + "-" + month + "-" + last_day;
                Log.e("Superfragment", "++343+last_date+++" + last_date);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return last_date;
    }


    public String getLastDayMonth(String month, String year) {
        String last_date = "";
        String last_day = "";
        String current_month = "";

        try {
            String date = month + "/" + year;
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("M/yyyy");
                Date convertedDate = null;

                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                last_day = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));

                Log.e("Timesheet", "+++MONTH+LAST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                //2020-01-01
                last_date = year + "-" + month + "-" + last_day;
                Log.e("Superfragment", "++343+last_date+++" + last_date);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return last_date;
    }


    public String getFirstDay(String month, String year) {
        String first_date = "";
        String first_day = "";
        String current_month = "";

        try {
            String date = month + "/" + year;
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                Date convertedDate = null;
                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_day = String.valueOf(c.getActualMinimum(Calendar.DAY_OF_MONTH));
                current_month = String.valueOf(c.get(Calendar.MONTH));
                Log.e("Timesheet", "+++MONTH+FIRST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_date = year + "-" + month + "-" + first_day;
                Log.e("Superfragment", "++343+first_date+++" + first_date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_date;
    }


    public String getFirstDayFromDate(String date) {
        String first_date = "";
        String first_day = "";
        String current_month = "";
        String year = "";
        String month = "";

        try {
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = null;
                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_day = String.valueOf(c.getActualMinimum(Calendar.DAY_OF_MONTH));
                current_month = String.valueOf(c.get(Calendar.MONTH));
                Log.e("getFirstDayFromDate", "+++MONTH+FIRST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_date = year + "-" + month + "-" + first_day;
                Log.e("getFirstDayFromDate", "+++first_date+++" + first_date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_date;
    }


    public String getLastDayFromDate(String date) {
        String first_date = "";
        String first_day = "";
        String current_month = "";
        String year = "";
        String month = "";

        try {
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = null;
                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_day = String.valueOf(c.getActualMinimum(Calendar.DAY_OF_MONTH));
                current_month = String.valueOf(c.get(Calendar.MONTH));
                Log.e("getFirstDayFromDate", "+++MONTH+FIRST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_date = year + "-" + month + "-" + first_day;
                Log.e("getFirstDayFromDate", "+++first_date+++" + first_date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_date;
    }


    public String getFirstDayofMonth(String month, String year) {
        String first_date = "";
        String first_day = "";
        String current_month = "";

        try {
            String date = month + "/" + year;
            if (date != null && !date.isEmpty()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("M/yyyy");
                Date convertedDate = null;
                convertedDate = dateFormat.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_day = String.valueOf(c.getActualMinimum(Calendar.DAY_OF_MONTH));
                current_month = String.valueOf(c.get(Calendar.MONTH));
                Log.e("Superfragment", "+402++MONTH+FIRST+DAY+++" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
                first_date = year + "-" + month + "-" + first_day;
                Log.e("Superfragment", "++402+first_date+++" + first_date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_date;
    }


    public String convertDateFormat(String original_date, String input_pattern, String output_pattern) {

        DateFormat inputFormat = new SimpleDateFormat(input_pattern);    // yyyy-MM-dd
        DateFormat outputFormat = new SimpleDateFormat(output_pattern);  // dd MMM YYYY"
        String outputDateStr = "";

        try {
            Date date = inputFormat.parse(original_date);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }

    public String convertTimeFormat(String _24HourTime, String input_pattern, String output_pattern) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));
            return _12HourSDF.format(_24HourDt);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean checkIsToday(String date) {
        try {
            String current_date = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
            if (date.equals(current_date)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void showErrorDialog(Activity activity, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                // .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Affinite")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (activity != null) {
//                            sessionManager.logOut();
//                            activity.startActivity(new Intent(activity, LoginActivity.class));
//                            activity.finishAffinity();
                        }
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void clearEditText(EditText et, EditText et1, EditText et2, EditText et3, EditText et4) {
        et.setText("");
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
    }

    public String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        return String.valueOf(month);
    }


    public String getCurrentMonthName() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(calendar.getTime());
        return  month_name;
    }


    public String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }


    public void myLog(String tag, String method_name, String data) {
        Log.e(tag, "++MYLOGE++" + method_name + "+++" + data);
    }



    public void showPointsCapDialog(Activity activity, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                // .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Paidtogo")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (activity != null) {
//                            sessionManager.logOut();
//                            activity.startActivity(new Intent(activity, LoginActivity.class));
//                            activity.finishAffinity();
                        }
                        activity.finish();
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


}