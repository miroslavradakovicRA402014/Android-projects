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

import java.util.ArrayList;

import java.io.Serializable;

/**
 * Created by mika on 20.5.17..
 */

public class TaskService extends Service {


    protected TaskBinder binder = new TaskBinder();
    protected static final long PERIOD = 60000L;
    protected ServiceThread mRunnable;
    protected ArrayList<TaskItem> mTaskItems;
    protected TaskItem newItem;

    public TaskService() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRunnable = new ServiceThread();
        mRunnable.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunnable.exit();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        newItem = (TaskItem) intent.getSerializableExtra("service");
        int op = 0;

        try {
            op = binder.getValue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (op == 1) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task added");
            Intent notificationIntent = new Intent(this,MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mTaskItems.add(newItem);
        } else if (op == 2) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task deleted");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task updated");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }


        return binder;
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
            return false;
        }
        @Override
        public void run() {
           while (mRun) {
               for (TaskItem item : mTaskItems) {
                   if (isRemind(item)) {
                       // NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task will be timedout in 15 minutes");
                       //Intent notificationIntent = new Intent(this,MainActivity.class);
                       //PendingIntent contentIntent = PendingIntent.getActivity(ServiceThread.class, 0, notificationIntent,
                       //        PendingIntent.FLAG_UPDATE_CURRENT);
                       //builder.setContentIntent(contentIntent);
                       //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
