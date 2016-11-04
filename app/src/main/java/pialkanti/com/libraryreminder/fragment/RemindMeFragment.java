package pialkanti.com.libraryreminder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pialkanti.com.libraryreminder.R;
import pialkanti.com.libraryreminder.UserHomeActivity;

/**
 * Created by Pial on 10/14/2016.
 */
public class RemindMeFragment extends Fragment {
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_me,
                container, false);

        ((UserHomeActivity)getActivity()).getSupportActionBar().setTitle("");
        ((UserHomeActivity)getActivity()).getSupportActionBar().setTitle("Reminder");

        fab = (FloatingActionButton)view.findViewById(R.id.fab_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHomeActivity userHomeActivity = (UserHomeActivity) getActivity();
                Fragment squadFragment = new CreateReminderFragment();
                FragmentTransaction fragmentTransaction = userHomeActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView,squadFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
