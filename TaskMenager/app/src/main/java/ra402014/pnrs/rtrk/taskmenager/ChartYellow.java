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

public class ChartYellow extends View {

    protected Paint paintChartMedium;
    protected Paint paintChartMediumBg;
    protected RectF rectMedium;
    protected float percentageMedium = (float)0;
    protected float percentage = 0;
    protected String percentStr;
    protected boolean drawFlag;
    protected int percentageMediumNum = 0;


    protected TaskDBHelper dbHelper;
    protected TaskItem[] items;

    protected CalculateStat statCal;

    public ChartYellow(Context context, AttributeSet attrs) {
        super(context, attrs);

        dbHelper = new TaskDBHelper(context);
        items =  dbHelper.readTaskItems();

        statCal = new CalculateStat();

        if (items != null) {
            for (TaskItem item : items) {
                if (item.getPriority() == TaskItem.Color.YELLOW) {
                    percentageMediumNum++;
                    if (item.isFinished()) {
                        percentage++;
                    }
                }
            }
        }

        if (percentageMediumNum != 0) {
            percentage = statCal.getPercentage(percentageMediumNum,(int)percentage);
        }


        paintChartMediumBg= new Paint();
        paintChartMediumBg.setColor(Color.parseColor("#FF4587E4"));
        paintChartMediumBg.setAntiAlias(true);
        paintChartMediumBg.setStyle(Paint.Style.FILL);


        paintChartMedium = new Paint();
        paintChartMedium.setColor(Color.YELLOW);
        paintChartMedium.setAntiAlias(true);
        paintChartMedium.setStyle(Paint.Style.FILL);

        rectMedium = new RectF();

        percentStr = Float.toString(percentageMedium)+"%";

        drawFlag = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        rectMedium.set(left, top, left+width, top + width);
        canvas.drawArc(rectMedium, -90, 360, true, paintChartMediumBg);
        if(percentageMedium!=0) {
            paintChartMedium = new Paint();
            paintChartMedium.setColor(Color.YELLOW);
            paintChartMedium.setAntiAlias(true);
            paintChartMedium.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectMedium, -90, ((float)3.6*percentageMedium), true, paintChartMedium);
        }

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintChartMedium.descent() + paintChartMedium.ascent()) / 2)) ;

        paintChartMedium.setColor(Color.BLACK);
        paintChartMedium.setTextAlign(Paint.Align.CENTER);
        paintChartMedium.setTextSize(50);
        canvas.drawText(percentStr,xPos,yPos,paintChartMedium);

        if (drawFlag == true) {
            setPercentageMedium((float)(percentageMedium + 1));
        }
    }

    public void setPercentageMedium(float percentageMedium) {

         if (percentage > this.percentageMedium) {
             this.percentageMedium = percentageMedium;
             percentStr = Float.toString(this.percentageMedium)+"%";
             invalidate();
         } else {
             drawFlag = false;
         }
    }
}
