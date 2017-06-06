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

public class ChartRed extends View {

    protected Paint paintChartHigh;
    protected Paint paintChartHighBg;
    protected RectF rectHigh;
    protected float percentageHigh = (float)0;
    protected float percentage = 0;
    protected String percentStr;
    protected boolean drawFlag;
    protected int percentageHighNum = 0;

    protected TaskDBHelper dbHelper;
    protected TaskItem[] items;

    protected CalculateStat statCal;

    public ChartRed(Context context, AttributeSet attrs) {
        super(context, attrs);


        dbHelper = new TaskDBHelper(context);
        items =  dbHelper.readTaskItems();


        statCal = new CalculateStat();

        if (items != null) {
            for (TaskItem item : items) {
                if (item.getPriority() == TaskItem.Color.RED) {
                    percentageHighNum++;
                    if (item.isFinished()) {
                        percentage++;
                    }
                }
            }
        }

        if (percentageHighNum != 0) {
            percentage = statCal.getPercentage((int)percentage,percentageHighNum);
        }

        paintChartHighBg = new Paint();
        paintChartHighBg.setColor(Color.parseColor("#FF4587E4"));
        paintChartHighBg.setAntiAlias(true);
        paintChartHighBg.setStyle(Paint.Style.FILL);

        paintChartHigh = new Paint();
        paintChartHigh.setColor(Color.RED);
        paintChartHigh.setAntiAlias(true);
        paintChartHigh.setStyle(Paint.Style.FILL);

        rectHigh = new RectF();

        percentStr = Float.toString(percentageHigh)+"%";

        drawFlag = true;

    }

/*
    public ChartRed(Context context, AttributeSet attrs,float percentageHigh) {
        super(context, attrs);

        this.percentage = percentageHigh;

        paintChartHighBg = new Paint();
        paintChartHighBg.setColor(Color.parseColor("#FF4587E4"));
        paintChartHighBg.setAntiAlias(true);
        paintChartHighBg.setStyle(Paint.Style.FILL);

        paintChartHigh = new Paint();
        paintChartHigh.setColor(Color.RED);
        paintChartHigh.setAntiAlias(true);
        paintChartHigh.setStyle(Paint.Style.FILL);

        rectHigh = new RectF();

        percentStr = Float.toString(percentageHigh)+"%";

        drawFlag = true;

    }
*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        rectHigh.set(left, top, left+width, top + width);
        canvas.drawArc(rectHigh, -90, 360, true, paintChartHighBg);
        if(percentageHigh!=0) {
            paintChartHigh = new Paint();
            paintChartHigh.setColor(Color.RED);
            paintChartHigh.setAntiAlias(true);
            paintChartHigh.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectHigh, -90, ((float)3.6*percentageHigh), true, paintChartHigh);
        }

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintChartHigh.descent() + paintChartHigh.ascent()) / 2)) ;

        paintChartHigh.setColor(Color.BLACK);
        paintChartHigh.setTextAlign(Paint.Align.CENTER);
        paintChartHigh.setTextSize(50);
        canvas.drawText(percentStr,xPos,yPos,paintChartHigh);

        if (drawFlag == true) {
            setPercentageHigh((float)(percentageHigh + 1));
        }
    }

    public void setPercentageHigh(float percentageHigh) {
        if (percentage > this.percentageHigh) {
            this.percentageHigh = percentageHigh;
            percentStr = Float.toString(this.percentageHigh)+"%";
            invalidate();
        } else {
            drawFlag = false;
        }
    }
}
