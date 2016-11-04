package pialkanti.com.libraryreminder;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pialkanti.com.libraryreminder.POJO.BookDetails;

/**
 * Created by Pial on 10/20/2016.
 */
public class BookdetailsAdapter extends ArrayAdapter<String> {
    Activity context;
    ArrayList<BookDetails> book;

    public BookdetailsAdapter(Activity context, ArrayList<BookDetails> book) {
        super(context, R.layout.booklistview);
        this.context = context;
        this.book = book;
    }

    static class ViewHolderItem {
        TextView bookTitle, call_no, due, renewal;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.booklistview, null, true);
            convertView.setBackgroundResource(R.drawable.customshape);
            viewHolder = new ViewHolderItem();
            viewHolder.bookTitle = (TextView) convertView.findViewById(R.id.BookTitle);
            viewHolder.call_no = (TextView) convertView.findViewById(R.id.Call_no);
            viewHolder.due = (TextView) convertView.findViewById(R.id.Due_Date);
            viewHolder.renewal = (TextView) convertView.findViewById(R.id.Remaining_renewal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        //String bookInfo[] = book.get(position);
        viewHolder.bookTitle.setText(Html.fromHtml(book.get(position).getBookTitle()));
        viewHolder.call_no.setText(Html.fromHtml("<b>Call No:</b> "+book.get(position).getCall_NO()));
        viewHolder.due.setText(Html.fromHtml("<b>Due:</b> "+book.get(position).getDue_Date()));
        viewHolder.renewal.setText(book.get(position).getRenewal());

        return convertView;
    }

    @Override
    public int getCount() {
        return book.size();
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
