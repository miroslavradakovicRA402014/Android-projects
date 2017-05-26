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
        db.execSQL("CREATE TABLE Tasks(TaskName TEXT,TaskDate INTEGER,TaskMonth,TaskYear INTEGER,TaskHour INTEGER,TaskMinute INTEGER,TaskDesc TEXT,TaskTurned BLOB,TaskPriority INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

    }

    public void addTaskInBase(TaskItem task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TaskName",task.getTaskName());
        values.put("TaskDate",task.getTaskName());
        values.put("TaskMonth",task.getMonth());
        values.put("TaskYear",task.getYear());
        values.put("TaskMinute",task.getMinute());
        values.put("TaskHour",task.getHour());
        values.put("TaskTurned",task.isTurned());
        values.put("TaskPriority",task.getTaskPriorityInt());

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
        int priority = curs.getInt(curs.getColumnIndex("TaskMonth"));
        //boolean turned = (curs.getBlob(curs.getColumnIndex("TaskTurned"))==0?true:false);
        TaskItem task;

        if (priority == 0) {
            task = new TaskItem(name, desc, date, month, year, hour, minute, false, true, TaskItem.Color.GREEN);
        } else if (priority == 1) {
            task = new TaskItem(name, desc, date, month, year, hour, minute, false, true, TaskItem.Color.YELLOW);
        } else {
            task = new TaskItem(name, desc, date, month, year, hour, minute, false, true, TaskItem.Color.RED);
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
        db.delete(baseName,"TaskName"+"?=",new String[]{name});
        close();
    }
}
