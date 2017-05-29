package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.app.LauncherActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

import static ra402014.pnrs.rtrk.taskmenager.R.id.taskName;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE_LIST = 2;
    private static final int REQUEST_CODE_STAT = 3;
    private static final int REQUEST_CODE_UPDATE = 4;
    private static final int RESULT_OK_UPDATE = 5;
    private static final int RESULT_OK_DELETE = 6;
    protected Button   newTaskButton;
    protected Button   statButton;
    protected ListView taskList;
    protected static TaskAdapter adapter;
    protected int itemPosition;
    protected ServiceConnection serviceConnection;
    private ITaskBinder iTaskBinder;
    protected Intent serviceInt;
    protected TaskDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iTaskBinder = new ITaskBinder() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }
            @Override
            public void addTaskNotify(String taskName) throws RemoteException {

            }
            @Override
            public void updateTaskNotify(String taskName) throws RemoteException {

            }
            @Override
            public void deleteTaskNotify(String taskName) throws RemoteException {

            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        };

        adapter = new TaskAdapter(this);

        newTaskButton = (Button)findViewById(R.id.newTask);
        statButton = (Button)findViewById(R.id.statistic);
        taskList = (ListView)findViewById(R.id.taskList);

        taskList.setAdapter(adapter);

        dbHelper = new TaskDBHelper(this);


        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iTaskBinder = ITaskBinder.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        serviceInt = new Intent(MainActivity.this,TaskService.class);

        bindService(serviceInt, serviceConnection, Context.BIND_AUTO_CREATE);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Task.class);
                startActivityForResult(in,REQUEST_CODE_LIST);

            }
        });

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Stat.class);
                startActivityForResult(in,REQUEST_CODE_STAT);
            }
        });


        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {

                itemPosition = position;
                Intent in = new Intent(MainActivity.this, Task.class);
                TaskItem item = (TaskItem) adapter.getItem(position);
                in.putExtra("update", (Serializable) item);
                startActivityForResult(in,REQUEST_CODE_UPDATE);
                return true;
            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LIST && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();

            try {
                iTaskBinder.addTaskNotify(" ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK_UPDATE) {

            try {
                iTaskBinder.updateTaskNotify(" ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK_DELETE) {

            try {
                iTaskBinder.deleteTaskNotify(" ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TaskItem[] items = dbHelper.readTaskItems();
        adapter.updateAdapter(items);
    }

    public static TaskAdapter getTaskAdapter() {
        return adapter;
    }



}
