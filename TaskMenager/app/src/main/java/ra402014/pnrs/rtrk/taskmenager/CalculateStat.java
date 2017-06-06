package ra402014.pnrs.rtrk.taskmenager;

/**
 * Created by mika on 6.6.17..
 */

public class CalculateStat {

    public native float getPercentage(int taskNum,int finishedTask);

    static {
        System.loadLibrary("calculatestat");
    }
}
