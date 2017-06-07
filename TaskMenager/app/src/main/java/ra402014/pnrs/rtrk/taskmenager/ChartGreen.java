package ra402014.pnrs.rtrk.taskmenager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mika on 22.4.17..
 */

public class ChartGreen extends View {

    protected Paint paintChartLow;
    protected Paint paintChartLowBg;
    protected RectF rectLow;
    protected float percentageLow = (float)0;
    protected float percentage = 0;
    protected String percentStr;
    protected boolean drawFlag;
    protected int percentageLowNum = 0;


    protected TaskDBHelper dbHelper;
    protected TaskItem[] items;

    protected CalculateStat statCal;

    public ChartGreen(Context context, AttributeSet attrs) {
        super(context, attrs);


        dbHelper = new TaskDBHelper(context);
        items =  dbHelper.readTaskItems();

        statCal = new CalculateStat();

        if (items != null) {
            for (TaskItem item : items) {
                if (item.getPriority() == TaskItem.Color.GREEN) {
                    percentageLowNum++;
                    if (item.isFinished()) {
                        percentage++;
                    }
                }
            }
        }

        if (percentageLowNum != 0) {
            percentage = statCal.getPercentage(percentageLowNum,(int)percentage);
        }


        paintChartLowBg = new Paint();
        paintChartLowBg.setColor(Color.parseColor("#FF4587E4"));
        paintChartLowBg.setAntiAlias(true);
        paintChartLowBg.setStyle(Paint.Style.FILL);

        paintChartLow = new Paint();
        paintChartLow.setColor(Color.GREEN);
        paintChartLow.setAntiAlias(true);
        paintChartLow.setStyle(Paint.Style.FILL);

        rectLow = new RectF();

        percentStr = Float.toString(percentageLow)+"%";

        drawFlag = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        rectLow.set(left, top, left+width, top + width);
        canvas.drawArc(rectLow, -90, 360, true, paintChartLowBg);
        if(percentageLow!=0) {
            paintChartLow = new Paint();
            paintChartLow.setColor(Color.GREEN);
            paintChartLow.setAntiAlias(true);
            paintChartLow.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectLow, -90, ((float)3.6*percentageLow), true, paintChartLow);
        }

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintChartLow.descent() + paintChartLow.ascent()) / 2)) ;

        paintChartLow.setColor(Color.BLACK);
        paintChartLow.setTextAlign(Paint.Align.CENTER);
        paintChartLow.setTextSize(50);
        canvas.drawText(percentStr,xPos,yPos,paintChartLow);

        if (drawFlag == true) {
            setPercentageLow((float)(percentageLow + 1));
        }

    }

    public void setPercentageLow(float percentageLow) {
        if (percentage > this.percentageLow) {
            this.percentageLow = percentageLow;
            percentStr = Float.toString(this.percentageLow)+"%";
            invalidate();
        } else {
            drawFlag = false;
        }
    }
}
