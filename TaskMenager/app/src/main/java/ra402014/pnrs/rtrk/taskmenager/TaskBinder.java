package ra402014.pnrs.rtrk.taskmenager;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

/**
 * Created by mika on 20.5.17..
 */

public class TaskBinder extends ITaskBinder.Stub {

    protected Notification.Builder builder;
    protected NotificationManager manager;
    protected Context context;

    public TaskBinder(Context context) {
        this.context = context;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
    @Override
    public void addTaskNotify(String taskName) throws RemoteException {
        Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task"+taskName+"added");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
    @Override
    public void updateTaskNotify(String taskName) throws RemoteException {
        Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task"+taskName+"updated");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, builder.build());
    }
    @Override
    public void deleteTaskNotify(String taskName) throws RemoteException {
        Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.ic_notify).setContentTitle("Task manager notification").setContentText("Task"+taskName+"deleted");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(3, builder.build());
    }

}
