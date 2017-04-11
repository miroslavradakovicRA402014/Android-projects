package ra402014.pnrs.rtrk.taskmenager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mika on 8.4.17..
 */

public class TaskAdapter extends BaseAdapter {


    protected Context mContext;
    protected ArrayList<TaskItem> mTaskItems;


    public TaskAdapter(Context context) {
        mContext = context;
        mTaskItems = new ArrayList<TaskItem>();
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
            ViewHolder holder = new ViewHolder();
            holder.nameText = (TextView) view.findViewById(R.id.taskNameList);
            holder.dateText = (TextView) view.findViewById(R.id.taskDate);
            holder.colorButton = (Button) view.findViewById(R.id.taskPriority);
            holder.completedBox = (CheckBox) view.findViewById(R.id.taskComplete);
            holder.stateButton = (RadioButton) view.findViewById(R.id.taskOn);
            view.setTag(holder);

        }

        TaskItem item = (TaskItem) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.colorButton.setClickable(false);
        if (item.getPriority() == TaskItem.Color.RED) {
            holder.colorButton.setBackgroundColor(Color.RED);
        } else if (item.getPriority() == TaskItem.Color.YELLOW) {
            holder.colorButton.setBackgroundColor(Color.YELLOW);
        } else {
            holder.colorButton.setBackgroundColor(Color.GREEN);
        }

        holder.nameText.setText(item.getTaskName());
        String dateTxt = item.toString();
        holder.dateText.setText(dateTxt);

        holder.completedBox.setClickable(false);
        holder.completedBox.setChecked(item.isFinished());
        holder.stateButton.setClickable(false);
        holder.stateButton.setChecked(item.isTurned());

        return view;
    }

    private class ViewHolder {
        public TextView nameText = null;
        public TextView dateText = null;
        public Button colorButton = null;
        public CheckBox completedBox = null;
        public RadioButton stateButton = null;

    }


}
