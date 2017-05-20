// ITaskBinder.aidl
package ra402014.pnrs.rtrk.taskmenager;

// Declare any non-default types here with import statements

interface ITaskBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getValue();
    void setValue(int op);

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
