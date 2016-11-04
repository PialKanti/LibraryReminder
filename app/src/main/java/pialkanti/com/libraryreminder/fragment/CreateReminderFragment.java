package pialkanti.com.libraryreminder.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pialkanti.com.libraryreminder.CreateReminderAdapter;
import pialkanti.com.libraryreminder.POJO.BookDetails;
import pialkanti.com.libraryreminder.R;
import pialkanti.com.libraryreminder.UserHomeActivity;
import pialkanti.com.libraryreminder.database.DBadapter;

/**
 * Created by Pial on 10/22/2016.
 */
public class CreateReminderFragment extends Fragment {
    ListView list;
    Integer[] icons = {R.mipmap.calendar, R.mipmap.alarm, R.mipmap.repeat};
    String[] textItem;
    CreateReminderAdapter reminderAdapter;
    ArrayList<BookDetails> bookDetails;
    TextView text;
    FloatingActionButton fab_OK;
    String earlist_due_date = "";
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    String repeat = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reminder,
                container, false);

        ((UserHomeActivity) getActivity()).getSupportActionBar().setTitle("");
        ((UserHomeActivity) getActivity()).getSupportActionBar().setTitle("Create Reminder");


        text = (TextView) view.findViewById(R.id.due_date_text);
        fab_OK = (FloatingActionButton) view.findViewById(R.id.fab_ok_button);

        //getting today's date and time

        textItem = new String[3];
        textItem[0] = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+6:00"));
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR);
        mMinute = cal.get(Calendar.MINUTE);
        mSecond = cal.get(Calendar.SECOND);
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("hh:mm a");
        textItem[1] = date.format(currentLocalTime);
        textItem[2] = "No Repeating";

        //getting earlist due date from database

        final DBadapter dBadapter = new DBadapter(getActivity().getApplicationContext());

        bookDetails = new ArrayList<>();

        bookDetails = dBadapter.getBookInfo();

        if (bookDetails.size() > 1) {
            if (bookDetails.size() == 2) {
                earlist_due_date = CompareDates(bookDetails.get(0).getDue_Date(), bookDetails.get(1).getDue_Date());
            } else if (bookDetails.size() == 3) {
                earlist_due_date = CompareDates(bookDetails.get(0).getDue_Date(), bookDetails.get(1).getDue_Date());
                earlist_due_date = CompareDates(earlist_due_date, bookDetails.get(2).getDue_Date());
            }
        } else {
            earlist_due_date = bookDetails.get(0).getDue_Date();
        }

        text.setText("Note: Your earliest expired date is " + earlist_due_date + ".");

        list = (ListView) view.findViewById(R.id.Reminder_listView);
        Log.i("Pial", icons.length + " " + textItem.length);
        reminderAdapter = new CreateReminderAdapter(getActivity(), icons, textItem);
        list.setAdapter(reminderAdapter);


        final Calendar c = Calendar.getInstance();

        // Get Current Date
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Time
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final TextView reminder_txt = (TextView) view.findViewById(R.id.text_reminder);
                //Opens Datepicker
                if (i == 0) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    mYear = year;
                                    mMonth = monthOfYear;
                                    mDay = dayOfMonth;
                                    try {
                                        Calendar calendar = Calendar.getInstance();
                                        String inputDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        String todayDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        Date strDate = sdf.parse(inputDate);
                                        Date currentDate = sdf.parse(todayDate);
                                        Date expireDate = sdf.parse(earlist_due_date);
                                        if (strDate.before(currentDate)) {
                                            //checks if input date is before today's date and shows alert dialog
                                            AlertDialog.Builder builderRepeatAlarm = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog);
                                            builderRepeatAlarm.setTitle("Warning");
                                            builderRepeatAlarm.setMessage("Please select the upcoming date for reminder.");
                                            builderRepeatAlarm.setNegativeButton(
                                                    "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            AlertDialog dialog = builderRepeatAlarm.create();
                                            dialog.show();

                                        } else if (strDate.getTime() >= System.currentTimeMillis() && strDate.after(expireDate)) {
                                            AlertDialog.Builder builderRepeatAlarm = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog);
                                            builderRepeatAlarm.setTitle("Warning");
                                            builderRepeatAlarm.setMessage("Please select a date before earliest expired date, " + earlist_due_date);
                                            builderRepeatAlarm.setNegativeButton(
                                                    "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            AlertDialog dialog = builderRepeatAlarm.create();
                                            dialog.show();
                                        } else {
                                            reminder_txt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                } else if (i == 1) {
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    mHour = hourOfDay;
                                    mMinute = minute;
                                    try {
                                        if (hourOfDay > 12) {
                                            reminder_txt.setText((hourOfDay - 12) + ":" + minute + " PM");
                                        } else if (hourOfDay == 12) {
                                            reminder_txt.setText("12" + ":" + minute + " PM");
                                        } else if (hourOfDay < 12) {
                                            if (hourOfDay != 0)
                                                reminder_txt.setText(hourOfDay + ":" + minute + " AM");
                                            else
                                                reminder_txt.setText("12" + ":" + minute + " AM");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                } else if (i == 2) {
                    //This shows repeat alarm time options
                    AlertDialog.Builder builderRepeatAlarm = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog);
                    builderRepeatAlarm.setTitle("Select time for repeat :");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1);
                    arrayAdapter.add("No Repeating");
                    arrayAdapter.add("Every 15 minutes");
                    arrayAdapter.add("Every half hour");
                    arrayAdapter.add("Every 1 hour");
                    arrayAdapter.add("Every 2 hours");
                    arrayAdapter.add("Every 3 hours");

                    final String[] choice = {"No Repeating", "Every 15 minutes", "Every half hour", "Every 1 hour", "Every 2 hours", "Every 3 hours"};
                    final String[] repeatTime = {"0", "0.25", "0.5", "1", "2", "3"};

                    builderRepeatAlarm.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderRepeatAlarm.setAdapter(
                            arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reminder_txt.setText(choice[which]);
                                    repeat = repeatTime[which];
                                }
                            });
                    builderRepeatAlarm.show();

                }
            }
        });

        //clicking OK button will set Notification for a Specific Date
        fab_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.clear();
                calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);

                //Insert Alarm details into Database
                Log.i("Check", repeat);
                dBadapter.insertAlarmDetails(mYear, mMonth, mDay, mHour, mMinute, mSecond, repeat);

                Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntent.addCategory("android.intent.category.DEFAULT");

                PendingIntent boardCast = PendingIntent.getBroadcast(getActivity(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Setting notification for exact date
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), boardCast);

                Toast.makeText(getActivity(), "Notification is set", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public String CompareDates(String date1, String date2) {
        String result = "";
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date Date1 = formatter.parse(date1);
            Date Date2 = formatter.parse(date2);
            if (Date1 != null && Date2 != null) {
                if (Date1.equals(Date2)) {
                    result = date1;
                } else {
                    if (Date1.after(Date2)) {
                        result = date1;
                    } else
                        result = date2;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
