package com.example.taskmanagement.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.taskmanagement.adapters.WeekAdapter;
import com.example.taskmanagement.utils.DbHelper;
import com.example.taskmanagement.R;
import com.example.taskmanagement.utils.FragmentHelper;


public class MondayFragment extends Fragment {

    public static final String KEY_MONDAY_FRAGMENT = "Monday";
    private DbHelper db;
    private ListView listView;
    private WeekAdapter adapter;
    private ImageView popup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        setupAdapter(view);
        setupListViewMultiSelect();
        popup = view.findViewById(R.id.popupbtn);
        return view;
    }

    private void setupAdapter(View view) {
        db = new DbHelper(getActivity());
        listView = view.findViewById(R.id.mondaylist);
        adapter = new WeekAdapter(getActivity(), listView, R.layout.listview_week_adapter, db.getWeek(KEY_MONDAY_FRAGMENT));
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(FragmentHelper.setupListViewMultiSelect(getActivity(), listView, adapter, db));
    }
}