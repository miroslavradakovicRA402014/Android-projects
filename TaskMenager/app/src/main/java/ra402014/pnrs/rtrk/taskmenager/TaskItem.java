package ra402014.pnrs.rtrk.taskmenager;

/**
 * Created by mika on 8.4.17..
 */

public class TaskItem {

    public enum Color {RED,GREEN,YELLOW};

    protected String name;
    protected int date;
    protected int month;
    protected int year;
    protected int hour;
    protected int minute;
    protected boolean finished;
    protected boolean turned;
    protected Color priority;


    public TaskItem(String taskName,int date,int month,int year,int hour,int minute,boolean finish,boolean turned,Color priority) {
        this.name = taskName;
        this.date  = date;
        this.month = month;
        this.year  = year;
        this.hour  = hour;
        this.minute = minute;
        this.finished = finish;
        this.turned = turned;
        this.priority = priority;
    }


    public String getTaskName() {
        return name;
    }

    public void setTaskName(String taskName) {
        this.name = taskName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finish) {
        this.finished = finish;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public Color getPriority() { return priority;}

    public void setPriority(Color priority) { this.priority = priority;}

}
