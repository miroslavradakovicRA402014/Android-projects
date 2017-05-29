package ra402014.pnrs.rtrk.taskmenager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class Stat extends Activity {

    protected Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        backButton = (Button) findViewById(R.id.backButton);


        ChartRed chartRed = new ChartRed(getApplicationContext(), null);
        ChartYellow chartYellow = new ChartYellow(getApplicationContext(), null);
        ChartGreen chartGreen = new ChartGreen(getApplicationContext(), null);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                in.putExtra("new data", 3);
                setResult(RESULT_OK, in);
                finish();
            }
        });
    }
}
