package com.example.taskmanagement.adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.PopupMenu;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taskmanagement.model.Homework;
import com.example.taskmanagement.R;
import com.example.taskmanagement.utils.AlertDialogsHelper;
import com.example.taskmanagement.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ulan on 21.09.2018.
 */
public class HomeworksAdapter extends ArrayAdapter<Homework> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Homework> homeworklist;
    private Homework homework;
    private ListView mListView;

    private static class ViewHolder {
        TextView subject;
        TextView description;
        TextView date;
        CardView cardView;
        ImageView popup;
    }

    public HomeworksAdapter(Activity activity, ListView listView,  int resource, ArrayList<Homework> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        homeworklist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String subject = Objects.requireNonNull(getItem(position)).getSubject();
        String description = Objects.requireNonNull(getItem(position)).getDescription();
        String date = Objects.requireNonNull(getItem(position)).getDate();
        int color = Objects.requireNonNull(getItem(position)).getColor();

        homework = new Homework(subject, description, date, color);
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.subject = convertView.findViewById(R.id.subjecthomework);
            holder.description = convertView.findViewById(R.id.descriptionhomework);
            holder.date = convertView.findViewById(R.id.datehomework);
            holder.cardView = convertView.findViewById(R.id.homeworks_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.subject.setText(homework.getSubject());
        holder.description.setText(homework.getDescription());
        holder.date.setText(homework.getDate());
        holder.cardView.setCardBackgroundColor(homework.getColor());
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(mActivity, holder.popup);
                final DbHelper db = new DbHelper(mActivity);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.delete_popup) {
                            db.deleteHomeworkById(getItem(position));
                            db.updateHomework(getItem(position));
                            homeworklist.remove(position);
                            notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.edit_popup) {
                            final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_homework, null);
                            AlertDialogsHelper.getEditHomeworkDialog(mActivity, alertLayout, homeworklist, mListView, position);
                            notifyDataSetChanged();
                            return true;
                        }
                        return onMenuItemClick(item);
                    }
                });
                popup.show();
            }
        });

        hidePopUpMenu(holder);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ArrayList<Homework> getHomeworkList() {
        return homeworklist;
    }

    public Homework getHomework() {
        return homework;
    }

    private void hidePopUpMenu(ViewHolder holder) {
        SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
        if (checkedItems.size() > 0) {
            for (int i = 0; i < checkedItems.size(); i++) {
                int key = checkedItems.keyAt(i);
                if (checkedItems.get(key)) {
                    holder.popup.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            holder.popup.setVisibility(View.VISIBLE);
        }
    }
}

