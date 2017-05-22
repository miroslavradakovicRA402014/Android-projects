package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class Task extends Activity {

    private static final int RESULT_OK_UPDATE = 5;
    private static final int RESULT_OK_DELETE = 6;

    protected Button addTaskButton;
    protected Button cancelTaskButton;
    protected Button redButton;
    protected Button yellowButton;
    protected Button greenButton;
    protected Button confirmButton;
    protected EditText taskName;
    protected EditText dateEdit;
    protected EditText monthEdit;
    protected EditText yearEdit;
    protected EditText hourEdit;
    protected EditText minuteEdit;
    protected EditText taskDescriptionEdit;
    protected CheckBox reminder;
    protected boolean colorRedPicked;
    protected boolean colorYellowPicked;
    protected boolean colorGreenPicked;

    protected String nameStr;
    protected String descStr;

    protected String dateStr;
    protected String monthStr;
    protected String yearStr;
    protected String hourStr;
    protected String minStr;

    protected int date;
    protected int month;
    protected int year;
    protected int hour;
    protected int minute;

    protected boolean checked;

    protected int maxDay;

    protected boolean preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addTaskButton = (Button) findViewById(R.id.addTask);
        cancelTaskButton = (Button) findViewById(R.id.cancelTask);

        taskName = (EditText) findViewById(R.id.taskName);
        dateEdit = (EditText) findViewById(R.id.editTextDate);
        monthEdit = (EditText) findViewById(R.id.editTextMonth);
        yearEdit = (EditText) findViewById(R.id.editTextYear);
        minuteEdit = (EditText) findViewById(R.id.editTextMinute);
        hourEdit = (EditText) findViewById(R.id.editTextHour);
        taskDescriptionEdit = (EditText) findViewById(R.id.taskDescriptor);

        reminder = (CheckBox) findViewById(R.id.checkBoxReminder);

        redButton = (Button) findViewById(R.id.redButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);
        greenButton = (Button) findViewById(R.id.greenButton);

        confirmButton = (Button) findViewById(R.id.confirmTask);

        addTaskButton.setEnabled(false);

        Intent in = getIntent();
        if (in.hasExtra("update")) {

            preview = true;

            TaskItem item = (TaskItem) in.getSerializableExtra("update");

            addTaskButton.setText("Update");
            cancelTaskButton.setText("Delete");
            taskName.setText(item.getTaskName());
            dateEdit.setText(Integer.toString(item.getDate()));
            monthEdit.setText(Integer.toString(item.getMonth()));
            yearEdit.setText(Integer.toString(item.getYear()));
            hourEdit.setText(Integer.toString(item.getHour()));
            minuteEdit.setText(Integer.toString(item.getMinute()));
            taskDescriptionEdit.setText(item.getDescription());

            reminder.setChecked(item.isTurned());

            TaskItem.Color color = item.getPriority();

            if (color == TaskItem.Color.RED) {
                greenButton.setEnabled(false);
                greenButton.setAlpha(0.2f);
                yellowButton.setEnabled(false);
                yellowButton.setAlpha(0.2f);
                colorRedPicked = true;
            } else if (color == TaskItem.Color.YELLOW){
                redButton.setEnabled(false);
                redButton.setAlpha(0.2f);
                greenButton.setEnabled(false);
                greenButton.setAlpha(0.2f);
                colorYellowPicked = true;
            } else {
                redButton.setEnabled(false);
                redButton.setAlpha(0.2f);
                yellowButton.setEnabled(false);
                yellowButton.setAlpha(0.2f);
                colorGreenPicked = true;
            }

        } else {
            preview = false;
        }

        redButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             if (!colorRedPicked) {
                 greenButton.setEnabled(false);
                 greenButton.setAlpha(0.2f);
                 yellowButton.setEnabled(false);
                 yellowButton.setAlpha(0.2f);
                 colorRedPicked = true;
             } else {
                 greenButton.setEnabled(true);
                 greenButton.setAlpha(1f);
                 yellowButton.setEnabled(true);
                 yellowButton.setAlpha(1f);
                 colorRedPicked = false;
             }
           }

        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!colorYellowPicked) {
                    redButton.setEnabled(false);
                    redButton.setAlpha(0.2f);
                    greenButton.setEnabled(false);
                    greenButton.setAlpha(0.2f);
                    colorYellowPicked = true;
                } else {
                    greenButton.setEnabled(true);
                    greenButton.setAlpha(1f);
                    redButton.setEnabled(true);
                    redButton.setAlpha(1f);
                    colorYellowPicked = false;
                }
            }

        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!colorGreenPicked) {
                    redButton.setEnabled(false);
                    redButton.setAlpha(0.2f);
                    yellowButton.setEnabled(false);
                    yellowButton.setAlpha(0.2f);
                    colorGreenPicked = true;
                } else {
                    redButton.setEnabled(true);
                    redButton.setAlpha(1f);
                    yellowButton.setEnabled(true);
                    yellowButton.setAlpha(1f);
                    colorGreenPicked = false;
                }
            }

        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            protected boolean isNumber(String str) {
                int i;
                for (i = 0; i < str.length(); i++) {
                    if (!( ((int) str.charAt(i)) >= 48 && ((int) str.charAt(i)) <= 57  ))
                    {
                        return false;
                    }
                }
                return true;
            }

            protected int checkEnter(String name,int date,int maxday,int month,int year,int hour,int minute,boolean red,boolean yellow,boolean green) {

                if (name.isEmpty()) {
                    return 1;
                } else if (name.length() > 15) {
                    return 2;
                }

                if (date <= 0 || date > maxday) {
                    return 3;
                }

                if (month <= 0 || month > 12) {
                    return 4;
                }

                if (year < 2017 || year > 2030) {
                    return 5;
                }

                if (minute < 0 || minute >= 60) {
                    return 6;
                }

                if (hour < 0 && hour > 24) {
                    return 7;
                }

                if (!red && !yellow && !green) {
                    return 8;
                }

                return 0;

            }
            @Override
            public void onClick(View v) {

                nameStr = taskName.getText().toString();
                descStr = taskDescriptionEdit.getText().toString();;

                dateStr = dateEdit.getText().toString();
                monthStr = monthEdit.getText().toString();
                yearStr = yearEdit.getText().toString();
                hourStr = hourEdit.getText().toString();
                minStr =  minuteEdit.getText().toString();

                checked = reminder.isChecked();

                if (this.isNumber(dateStr) && this.isNumber(monthStr) && this.isNumber(yearStr) && this.isNumber(hourStr) && this.isNumber(minStr)) {
                  date = Integer.parseInt(dateStr);
                  month = Integer.parseInt(monthStr);
                  year = Integer.parseInt(yearStr);

                  hour = Integer.parseInt(hourStr);
                  minute = Integer.parseInt(minStr);
                }
                else {
                    return;
                }

                switch (month) {
                    case 1 :case 3: case 5: case 7: case 8: case 10: case 12:
                      maxDay = 31;
                      break;
                    case 4: case 6: case 9: case 11:
                      maxDay = 30;
                      break;
                    case 2:
                        if (((year % 4 == 0) &&  !(year % 100 == 0))  || (year % 400 == 0)) {
                            maxDay = 29;
                        } else {
                            maxDay = 28;
                        }
                      break;
                }

                int errorCode = checkEnter(nameStr,date,maxDay,month,year,hour,minute,colorRedPicked,colorYellowPicked,colorGreenPicked);
                if (errorCode == 0) {
                    taskName.setEnabled(false);
                    dateEdit.setEnabled(false);
                    monthEdit.setEnabled(false);
                    yearEdit.setEnabled(false);
                    hourEdit.setEnabled(false);
                    minuteEdit.setEnabled(false);
                    taskDescriptionEdit.setEnabled(false);
                    redButton.setEnabled(false);
                    greenButton.setEnabled(false);
                    yellowButton.setEnabled(false);
                    addTaskButton.setEnabled(true);
                    reminder.setEnabled(false);
                } else {
                    switch (errorCode) {
                        case 1:
                            Toast.makeText(getBaseContext(),"Task name is empty!",Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(getBaseContext(),"Task name is too long!",Toast.LENGTH_LONG).show();
                            break;
                        case 3:
                            Toast.makeText(getBaseContext(),"Invalid date!",Toast.LENGTH_LONG).show();
                            break;
                        case 4:
                            Toast.makeText(getBaseContext(),"Invalid dmonth!",Toast.LENGTH_LONG).show();
                            break;
                        case 5:
                            Toast.makeText(getBaseContext(),"Invalid year!",Toast.LENGTH_LONG).show();
                            break;
                        case 6:
                            Toast.makeText(getBaseContext(),"Invalid hour!",Toast.LENGTH_LONG).show();
                            break;
                        case 7:
                            Toast.makeText(getBaseContext(),"Invalid minute!",Toast.LENGTH_LONG).show();
                            break;
                        case 8:
                            Toast.makeText(getBaseContext(),"Pick priority!",Toast.LENGTH_LONG).show();
                            break;
                    }

                }

            }

        });


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!preview) {
                TaskItem.Color color;
                if (colorRedPicked) {
                    color = TaskItem.Color.RED;
                } else if (colorYellowPicked) {
                    color = TaskItem.Color.YELLOW;
                } else {
                    color = TaskItem.Color.GREEN;
                }

                TaskAdapter adapter = MainActivity.getTaskAdapter();
                TaskItem item = new TaskItem(nameStr,descStr, date, month, year, hour, minute, false, checked, color);
                adapter.addTaskItem(item);

                Intent in = getIntent();
                in.putExtra("new data", 3);
                setResult(RESULT_OK, in);
                finish();
            } else {
                setResult(RESULT_OK_UPDATE);
                finish();
            }
            }
        });

        cancelTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (!preview) {
                 Intent in = new Intent(Task.this, MainActivity.class);
                 startActivity(in);
             } else {
                 setResult(RESULT_OK_DELETE);
                 finish();
             }
            }
        });
    }
}
