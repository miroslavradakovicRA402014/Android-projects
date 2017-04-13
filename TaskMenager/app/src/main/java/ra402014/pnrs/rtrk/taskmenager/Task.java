package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Task extends Activity {

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

    //protected int retCode;

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
        /*
        Intent in = getIntent();
        retCode = in.getIntExtra("update",5);
        */
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
            @Override
            public void onClick(View v) {

                nameStr = taskName.getText().toString();

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

                if (!nameStr.isEmpty() &&(colorRedPicked || colorYellowPicked || colorGreenPicked) && (year > 2016 && year < 2030) && ( month >= 1 && month <= 12) && ( date >= 1 && date <= maxDay ) && (hour >= 0 && hour <= 23) && (minute >= 0 && minute <= 59) ) {
                    addTaskButton.setEnabled(true);
                }
            }
        });


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              TaskItem.Color color;
              if (colorRedPicked) {
                  color = TaskItem.Color.RED;
              } else if (colorYellowPicked) {
                  color = TaskItem.Color.YELLOW;
              } else {
                  color = TaskItem.Color.GREEN;
              }

              TaskAdapter adapter = MainActivity.getTaskAdapter();
              adapter.addTaskItem(new TaskItem(nameStr, date, month, year, hour, minute, false, checked, color));

              Intent in = getIntent();
              in.putExtra("new data", 3);
              setResult(RESULT_OK, in);

              finish();
            }
        });

        cancelTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Task.this, MainActivity.class);
                startActivity(in);
            }
        });
    }
}
