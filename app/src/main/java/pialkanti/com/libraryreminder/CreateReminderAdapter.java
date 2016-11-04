package pialkanti.com.libraryreminder;

import android.app.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pial on 10/22/2016.
 */
public class CreateReminderAdapter extends ArrayAdapter<String> {
    Activity context;
    Integer[] icons;
    String[] textItem;

    public CreateReminderAdapter(Activity context1, Integer[] icons,String[] textItem) {
        super(context1, R.layout.create_reminder_row);
        context = context1;
        this.icons = icons;
        this.textItem = textItem;
    }

    static class ViewHolderItem {
        ImageView icon;
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.create_reminder_row, null, true);
            viewHolder = new ViewHolderItem();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon_reminder);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text_reminder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.icon.setBackgroundResource(icons[position]);
        viewHolder.text.setText(textItem[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        Log.i("Pial",icons.length+" "+textItem.length);
        return icons.length;
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
