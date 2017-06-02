package ra402014.pnrs.rtrk.taskmenager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mika on 8.4.17..
 */

public class TaskAdapter extends BaseAdapter {


    protected Context mContext;
    protected ArrayList<TaskItem> mTaskItems;
    protected TaskDBHelper dbHelper;

    public TaskAdapter(Context context) {
        mContext = context;
        mTaskItems = new ArrayList<TaskItem>();
        dbHelper = new TaskDBHelper(context);
    }

    public void addTaskItem(TaskItem item) {
        mTaskItems.add(item);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mTaskItems.size();
    }

    @Override
    public Object getItem(int position) {
        Object retVal = null;
        try {
            retVal = mTaskItems.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public void updateAdapter(TaskItem[] items) {
        mTaskItems.clear();
        if (items != null) {
            for (TaskItem item:items) {
                mTaskItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_element,null);
            final ViewHolder holder = new ViewHolder();
            holder.nameText = (TextView) view.findViewById(R.id.taskNameList);
            holder.dateText = (TextView) view.findViewById(R.id.taskDate);
            holder.colorButton = (Button) view.findViewById(R.id.taskPriority);
            holder.completedBox = (CheckBox) view.findViewById(R.id.taskComplete);
            holder.stateButton = (RadioButton) view.findViewById(R.id.taskOn);
            view.setTag(holder);

        }

        final TaskItem item = (TaskItem) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.colorButton.setClickable(false);
        if (item.getPriority() == TaskItem.Color.RED) {
            holder.colorButton.setBackgroundColor(Color.RED);
        } else if (item.getPriority() == TaskItem.Color.YELLOW) {
            holder.colorButton.setBackgroundColor(Color.YELLOW);
        } else {
            holder.colorButton.setBackgroundColor(Color.GREEN);
        }

        holder.completedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TaskItem itemUpdated = new TaskItem(item.getTaskName(),item.getDescription(),item.getDate(),item.getMonth(),item.getYear(),item.getHour(),item.getMinute(),true,item.isTurned(),item.getPriority());
                    dbHelper.updateTask(itemUpdated.getTaskName(),itemUpdated);
                    TaskItem[] items = dbHelper.readTaskItems();
                    TaskAdapter adapter = MainActivity.getTaskAdapter();
                    adapter.updateAdapter(items);
                    holder.nameText.setPaintFlags(holder.nameText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    TaskItem itemUpdated = new TaskItem(item.getTaskName(),item.getDescription(),item.getDate(),item.getMonth(),item.getYear(),item.getHour(),item.getMinute(),false,item.isTurned(),item.getPriority());
                    dbHelper.updateTask(itemUpdated.getTaskName(),itemUpdated);
                    TaskItem[] items = dbHelper.readTaskItems();
                    TaskAdapter adapter = MainActivity.getTaskAdapter();
                    adapter.updateAdapter(items);
                    holder.nameText.setPaintFlags(holder.nameText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        holder.nameText.setText(item.getTaskName());

        String dateTxt = item.toString();
        holder.dateText.setText(dateTxt);

        holder.stateButton.setClickable(false);
        holder.stateButton.setChecked(item.isTurned());

        if (item.isFinished()) {
            holder.completedBox.setChecked(true);
        }

        return view;
    }

    public ArrayList<TaskItem> getmTaskItems() {
        return mTaskItems;
    }

    private class ViewHolder {
        public TextView nameText = null;
        public TextView dateText = null;
        public Button colorButton = null;
        public CheckBox completedBox = null;
        public RadioButton stateButton = null;

    }


}
