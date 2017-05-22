// ITaskBinder.aidl
package ra402014.pnrs.rtrk.taskmenager;

// Declare any non-default types here with import statements

interface ITaskBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void addTaskNotify(String taskName);
    void updateTaskNotify(String taskName);
    void deleteTaskNotify(String taskName);
}
