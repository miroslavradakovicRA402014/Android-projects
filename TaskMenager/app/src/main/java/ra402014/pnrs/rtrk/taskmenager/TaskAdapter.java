package ra402014.pnrs.rtrk.taskmenager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        mTaskItems = ArrayList<TaskItem>();
    }

    public void addTaksItem(TaskItem item) {
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
            view.setTag(holder);

        }

        TaskItem item = (TaskItem) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        return view;
    }

    private class ViewHolder {


    }


}
