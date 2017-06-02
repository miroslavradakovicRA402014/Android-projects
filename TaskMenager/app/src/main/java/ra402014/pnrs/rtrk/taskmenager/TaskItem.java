package ra402014.pnrs.rtrk.taskmenager;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Created by mika on 8.4.17..
 */

public class TaskItem implements Serializable {

    public enum Color {RED,GREEN,YELLOW};

    protected String name;
    protected String description;
    protected int date;
    protected int month;
    protected int year;
    protected int hour;
    protected int minute;
    protected boolean finished;
    protected boolean turned;
    protected Color priority;

    public TaskItem(String taskName,String description, int date, int month, int year, int hour, int minute, boolean finish, boolean turned, Color priority) {
        this.name = taskName;
        this.description = description;
        this.date  = date;
        this.month = month;
        this.year  = year;
        this.hour  = hour;
        this.minute = minute;
        this.finished = finish;
        this.turned = turned;
        this.priority = priority;
    }

    public boolean isToday(int dateP,int monthP,int yearP) {
        Calendar rightNow = Calendar.getInstance();
        long currTime = rightNow.getTimeInMillis();
        Calendar myDate = Calendar.getInstance();
        myDate.clear();
        myDate.set(yearP,monthP-1,dateP,0,1,0);

        long dayTime = myDate.getTimeInMillis();

        if (currTime >= dayTime) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTommorow() {
        Calendar rightNow = Calendar.getInstance();
        long currTime = rightNow.getTimeInMillis();

        int prevDate = getDate();
        int prevMonth = getMonth();
        int prevYear = getYear();

        if (getDate() != 1) {
            prevDate--;
        } else {
            if (getMonth() != 1) {
                prevMonth--;
                switch (prevMonth) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        prevDate = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        prevDate = 30;
                        break;
                    case 2:
                        if (((getYear() % 4 == 0) && !(getYear() % 100 == 0)) || (getYear() % 400 == 0)) {
                            prevDate = 29;
                        } else {
                            prevDate = 28;
                        }
                        break;
                }
            } else {
                prevDate = 31;
                prevMonth = 12;
                prevYear--;
            }
        }
        if (isToday(prevDate,prevMonth,prevYear)) {
            return true;
        } else {
            return false;
        }
     }

     public int inWeek(int dateP,int monthP,int yearP) {
         Calendar rightNow = Calendar.getInstance();
         long currTime = rightNow.getTimeInMillis();
         Calendar myDate = Calendar.getInstance();
         myDate.clear();
         myDate.set(yearP,monthP-1,dateP,0,1,0);

         long dayTime = myDate.getTimeInMillis();

         long diffTime = dayTime - currTime;

          if (diffTime > 0) {
              long diffDays = diffTime / (1000 * 60 * 60 * 24);
              if (diffDays <= 7 && !isTommorow()) {
                  switch (myDate.get(Calendar.DAY_OF_WEEK)) {
                      case Calendar.MONDAY:
                          return 1;
                      case Calendar.TUESDAY:
                          return 2;
                      case Calendar.WEDNESDAY:
                          return 3;
                      case Calendar.THURSDAY:
                          return 4;
                      case Calendar.FRIDAY:
                          return 5;
                      case Calendar.SATURDAY:
                          return 6;
                      case Calendar.SUNDAY:
                          return 7;
                  }
              } else {
                  return -1;
              }

          }

          return -1;

     }
    public boolean isRemindTime(int dateP,int monthP,int yearP,int houP,int minP ) {

        if (!isTurned()) {
            return false;
        }

        Calendar rightNow = Calendar.getInstance();
        long currTime = rightNow.getTimeInMillis();
        Calendar myDate = Calendar.getInstance();
        myDate.clear();
        myDate.set(yearP,monthP-1,dateP,houP,minP,0);

        long dayTime = myDate.getTimeInMillis();

        long diffTime = dayTime - currTime;

        if (diffTime > 0) {
            long diffMinutes = diffTime / (1000 * 60);
            if (diffMinutes == 1) {
                return true;
            } else {
                return false;
            }
        }

        return false;

    }

    @Override
    public String toString() {

        int dayOfWeek = inWeek(getDate(),getMonth(),getYear());

        if (isToday(getDate(),getMonth(),getYear())) {
            return  "Today";
        }

        if (isTommorow()) {
            return  "Tommorow";
        }

        if ( dayOfWeek != -1) {
            switch (dayOfWeek) {
                case 1:
                    return "Monday";
                case 2:
                    return "Tuesday";
                case 3:
                    return "Wedenesday";
                case 4:
                    return "Thursday";
                case 5:
                    return "Friday";
                case 6:
                    return "Saturday";
                case 7:
                    return "Sunday";
            }

        }

        return date + "/" + month + "/" + year;
    }
    public int getTaskPriorityInt() {

        int retVal = 0;

        switch (this.getPriority()) {
            case RED:
                retVal = 2;
                break;
            case YELLOW:
                retVal =  1;
                break;
            case GREEN:
                retVal =  0;
                break;
        }

        return retVal;
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

    public int isFinishedInt() {
        if (finished){
            return 1;
        } else {
            return 0;
        }
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

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

}
