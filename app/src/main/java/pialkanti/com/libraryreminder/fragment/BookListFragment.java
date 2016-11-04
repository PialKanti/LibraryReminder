package pialkanti.com.libraryreminder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import pialkanti.com.libraryreminder.BookdetailsAdapter;
import pialkanti.com.libraryreminder.POJO.BookDetails;
import pialkanti.com.libraryreminder.R;
import pialkanti.com.libraryreminder.UserHomeActivity;
import pialkanti.com.libraryreminder.database.DBadapter;

/**
 * Created by Pial on 10/14/2016.
 */
public class BookListFragment extends Fragment {
    ArrayList<BookDetails> bookDetails;
    ListView bookList;
    BookdetailsAdapter bookdetailsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booklist,
                container, false);

        bookList = (ListView) view.findViewById(R.id.BooklistView);

        ((UserHomeActivity)getActivity()).getSupportActionBar().setTitle("");
        ((UserHomeActivity)getActivity()).getSupportActionBar().setTitle("Book List");


        DBadapter dBadapter = new DBadapter(getActivity().getApplicationContext());

        bookDetails = new ArrayList<>();

        bookDetails = dBadapter.getBookInfo();

        bookdetailsAdapter = new BookdetailsAdapter(getActivity(), bookDetails);

        bookList.setAdapter(bookdetailsAdapter);

        return view;
    }
}
