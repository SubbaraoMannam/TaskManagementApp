package com.example.taskmanagement.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.taskmanagement.adapters.WeekAdapter;
import com.example.taskmanagement.utils.DbHelper;
import com.example.taskmanagement.R;
import com.example.taskmanagement.utils.FragmentHelper;


/**
 * Created by Ulan on 06.09.2018.
 */
public class SundayFragment extends Fragment {

    public static final String KEY_SUNDAY_FRAGMENT = "Sunday";
    private DbHelper db;
    private ListView listView;
    private WeekAdapter adapter;
    private int listposition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunday, container, false);
        setupAdapter(view);
        setupListViewMultiSelect();
        return view;
    }

    private void setupAdapter(View view) {
        db = new DbHelper(getActivity());
        listView = view.findViewById(R.id.sundaylist);
        adapter = new WeekAdapter(getActivity(), listView, R.layout.listview_week_adapter, db.getWeek(KEY_SUNDAY_FRAGMENT));
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(FragmentHelper.setupListViewMultiSelect(getActivity(), listView, adapter, db));
    }
}