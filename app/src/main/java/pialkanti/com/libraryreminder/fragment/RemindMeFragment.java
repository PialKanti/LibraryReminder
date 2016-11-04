package pialkanti.com.libraryreminder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pialkanti.com.libraryreminder.AlarmListAdapter;
import pialkanti.com.libraryreminder.POJO.AlarmDetails;
import pialkanti.com.libraryreminder.R;
import pialkanti.com.libraryreminder.UserHomeActivity;
import pialkanti.com.libraryreminder.database.DBadapter;

/**
 * Created by Pial on 10/14/2016.
 */
public class RemindMeFragment extends Fragment {
    FloatingActionButton fab;
    TextView reminderText;
    ListView alarmList;
    ArrayList<AlarmDetails> alarm;
    AlarmListAdapter alarmListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_me,
                container, false);

        ((UserHomeActivity) getActivity()).getSupportActionBar().setTitle("");
        ((UserHomeActivity) getActivity()).getSupportActionBar().setTitle("Reminder");

        reminderText = (TextView) view.findViewById(R.id.Reminder_text);
        alarmList = (ListView) view.findViewById(R.id.Reminder_listView);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_add_button);

        DBadapter dBadapter = new DBadapter(getActivity().getApplicationContext());

        Log.i("Pial", dBadapter.AlarmTableSize() + "");
        if (dBadapter.AlarmTableSize() == 0) {
            reminderText.setVisibility(View.VISIBLE);
            reminderText.setText("No Active Reminders!");
            alarmList.setVisibility(View.GONE);
        } else {
            alarmList.setVisibility(View.VISIBLE);
            reminderText.setVisibility(View.GONE);
            alarm = dBadapter.getAlarmInfo();
            alarmListAdapter = new AlarmListAdapter(getActivity(), alarm);
            alarmList.setAdapter(alarmListAdapter);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHomeActivity userHomeActivity = (UserHomeActivity) getActivity();
                Fragment squadFragment = new CreateReminderFragment();
                FragmentTransaction fragmentTransaction = userHomeActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, squadFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
