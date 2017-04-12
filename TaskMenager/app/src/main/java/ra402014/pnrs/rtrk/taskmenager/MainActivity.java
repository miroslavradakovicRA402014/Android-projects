package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 2;
    protected Button   newTaskButton;
    protected Button   statButton;
    protected ListView taskList;
    protected static TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new TaskAdapter(this);

        newTaskButton = (Button)findViewById(R.id.newTask);
        statButton = (Button)findViewById(R.id.statistic);
        taskList = (ListView)findViewById(R.id.taskList);

        taskList.setAdapter(adapter);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Task.class);
                startActivityForResult(in,REQUEST_CODE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && requestCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }

    }

    public static TaskAdapter getTaskAdapter() {
        return adapter;
    }

}
