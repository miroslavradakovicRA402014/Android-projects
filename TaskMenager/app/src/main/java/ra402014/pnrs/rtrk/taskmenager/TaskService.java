package ra402014.pnrs.rtrk.taskmenager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by mika on 20.5.17..
 */

public class TaskService extends Service {


    protected TaskBinder binder;
    protected static final long PERIOD = 60000L;
    protected ServiceThread mRunnable;
    protected static TaskDBHelper dbHelper;
    protected Notification.Builder builder;
    protected NotificationManager manager;
    protected TaskItem[] items;

    public TaskService() {
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        mRunnable = new ServiceThread();
        mRunnable.start();
        binder = new TaskBinder(getApplicationContext());

        dbHelper = new TaskDBHelper(getApplicationContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunnable.exit();

    }

    void buildNotification(String taskName,int hour,int min) {
        builder = new Notification.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task "+taskName+" will be timeouted in 15  at "+hour+":"+"minute");
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class ServiceThread extends Thread {

        private boolean mRun;

        private ServiceThread() {
            mRun = false;
        }

        @Override
        public synchronized void start() {
            mRun = true;
            super.start();
        }

        public synchronized void exit() {
            mRun = false;
        }


        public boolean isRemind(TaskItem item) {
            return item.isRemindTime(item.getDate(),item.getMonth(),item.getYear(),item.getHour(),item.getMinute());
        }

        @Override
        public void run() {

            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {

            }

            while (mRun) {
               items = dbHelper.readTaskItems();
               if (items != null) {
                   for (TaskItem item : items) {
                       if (isRemind(item)) {
                           buildNotification(item.getTaskName(), item.getHour(), item.getMinute());
                           manager.notify(4, builder.build());
                       }
                   }
               }

               try {
                   Thread.sleep(PERIOD);
               } catch (InterruptedException e) {
                   break;
               }
           }
        }
    }
}
