package ra402014.pnrs.rtrk.taskmenager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mika on 26.5.17..
 */

public class TaskDBHelper extends SQLiteOpenHelper {

    final static String baseName = "Tasks";
    final static int version = 10;

    public TaskDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TaskDBHelper(Context context) {
        super(context,baseName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tasks(TaskName TEXT,TaskDate INTEGER,TaskMonth,TaskYear INTEGER,TaskHour INTEGER,TaskMinute INTEGER,TaskDesc TEXT,TaskTurned INTEGER,TaskPriority INTEGER,TaskFinished INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

    }

    public void addTaskInBase(TaskItem task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TaskName",task.getTaskName());
        values.put("TaskDate",task.getDate());
        values.put("TaskMonth",task.getMonth());
        values.put("TaskYear",task.getYear());
        values.put("TaskMinute",task.getMinute());
        values.put("TaskHour",task.getHour());
        values.put("TaskTurned",(task.isTurned() ? 1 : 0));
        values.put("TaskPriority",task.getTaskPriorityInt());
        values.put("TaskFinished",task.isFinishedInt());

        db.insert(baseName,null,values);
        db.close();
    }

    public TaskItem createTask(Cursor curs) {
        String name = curs.getString(curs.getColumnIndex("TaskName"));
        int date = curs.getInt(curs.getColumnIndex("TaskDate"));
        int month = curs.getInt(curs.getColumnIndex("TaskMonth"));
        int year = curs.getInt(curs.getColumnIndex("TaskYear"));
        int hour = curs.getInt(curs.getColumnIndex("TaskHour"));
        int minute = curs.getInt(curs.getColumnIndex("TaskMinute"));
        String desc = curs.getString(curs.getColumnIndex("TaskDesc"));
        int priority = curs.getInt(curs.getColumnIndex("TaskPriority"));
        boolean turned = (curs.getInt(curs.getColumnIndex("TaskTurned"))==1?true:false);
        boolean finish = (curs.getInt(curs.getColumnIndex("TaskFinished"))==1?true:false);
        TaskItem task;

        if (priority == 0) {
            task = new TaskItem(name, desc, date, month, year, hour, minute, finish, turned, TaskItem.Color.GREEN);
        } else if (priority == 1) {
            task = new TaskItem(name, desc, date, month, year, hour, minute, finish, turned, TaskItem.Color.YELLOW);
        } else {
            task = new TaskItem(name, desc, date, month, year, hour, minute, finish, turned, TaskItem.Color.RED);
        }

        return task;
    }

    public TaskItem[] readTaskItems() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(baseName,null,null,null,null,null,null,null);
        TaskItem[] items = new TaskItem[cur.getCount()];
        int i = 0;

        for (cur.moveToFirst();!cur.isAfterLast();cur.moveToNext(),i++) {
            items[i] = createTask(cur);
        }

        return items;
    }

    public void deleteTask(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(baseName,"TaskName"+"=?",new String[]{name});
        close();
    }

}
