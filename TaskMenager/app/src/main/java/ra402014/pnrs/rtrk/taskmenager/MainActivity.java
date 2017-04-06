package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    protected Button newTaskButton;
    protected Button statButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newTaskButton = (Button)findViewById(R.id.newTask);
        statButton = (Button)findViewById(R.id.statistic);


        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Task.class);
                startActivity(in);

            }
        });

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Stat.class);
                startActivity(in);
            }
        });

    }

}
