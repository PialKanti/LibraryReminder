package pialkanti.com.libraryreminder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pialkanti.com.libraryreminder.POJO.AlarmDetails;

/**
 * Created by Pial on 11/4/2016.
 */
public class AlarmListAdapter extends ArrayAdapter<String> {
    Activity context;
    ArrayList<AlarmDetails> alarmDetails;
    int year, month, day, hour, minute, second;
    String repeat;

    public AlarmListAdapter(Activity context1, ArrayList<AlarmDetails> alarmDetails) {
        super(context1, R.layout.alarm_list_row);
        context = context1;
        this.alarmDetails = alarmDetails;
    }

    static class ViewHolderItem {
        TextView reminderDate;
        TextView reminderTime;
        TextView reminderRepeat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView != null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.alarm_list_row, null, true);
            viewHolder = new ViewHolderItem();
            viewHolder.reminderDate = (TextView) convertView.findViewById(R.id.reminder_date);
            viewHolder.reminderTime = (TextView) convertView.findViewById(R.id.reminder_time);
            viewHolder.reminderRepeat = (TextView) convertView.findViewById(R.id.reminder_repeat);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        year = alarmDetails.get(position).getYear();
        month = alarmDetails.get(position).getMonth();
        day = alarmDetails.get(position).getDay();
        hour = alarmDetails.get(position).getHour();
        minute = alarmDetails.get(position).getMinute();
        second = alarmDetails.get(position).getSecond();
        repeat = alarmDetails.get(position).getRepeat();

        viewHolder.reminderDate.setText(day + "/" + (month + 1) + "/" + year);
        if (hour > 12) {
            viewHolder.reminderTime.setText((hour - 12) + ":" + minute + " PM");
        } else if (hour == 12) {
            viewHolder.reminderTime.setText("12" + ":" + minute + " PM");
        } else if (hour < 12) {
            if (hour != 0)
                viewHolder.reminderTime.setText(hour + ":" + minute + " AM");
            else
                viewHolder.reminderTime.setText("12" + ":" + minute + " AM");
        }

        if (repeat == "0") {
            viewHolder.reminderRepeat.setText("No Repeating");
        } else if (repeat == "0.25") {
            viewHolder.reminderRepeat.setText("Every 15 minutes");
        } else if (repeat == "0.5") {
            viewHolder.reminderRepeat.setText("Every half hour");
        } else {
            viewHolder.reminderRepeat.setText("Every " + repeat + " hour");
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return alarmDetails.size();
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
