package ra402014.pnrs.rtrk.taskmenager;

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
    protected ArrayList<TaskItem> mTaskItems;
    protected TaskItem item;
    protected NotificationCompat.Builder builder;
    public TaskService() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRunnable = new ServiceThread();
        mRunnable.start();
        binder = new TaskBinder(getApplicationContext());
        mTaskItems = MainActivity.getTaskAdapter().getmTaskItems();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Service created");
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunnable.exit();
    }

    void buildNotification(String taskName) {
        builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task will be"+taskName+"timeouted in 15 minutes");
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

        private boolean mRun = true;

        @Override
        public synchronized void start() {
            mRun = true;
            super.start();
        }

        public synchronized void exit() {
            mRun = false;
        }


        public boolean isRemind(TaskItem item) {
            return item.isRemindTime(item.getDate(),item.getMonth(),item.getYear());
        }
        @Override
        public void run() {
           while (mRun) {
               for (TaskItem item : mTaskItems) {
                   if (isRemind(item)) {
                       buildNotification(item.getTaskName());
                       NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                       manager.notify(4, builder.build());
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
