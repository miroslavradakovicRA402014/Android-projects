package ra402014.pnrs.rtrk.taskmenager;


import android.os.RemoteException;

/**
 * Created by mika on 20.5.17..
 */

public class TaskBinder extends ITaskBinder.Stub {
    protected int operation;
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public int getValue() throws RemoteException {
        return operation;
    }

    @Override
    public void setValue(int op) throws RemoteException {
        operation=op;

    }

}
