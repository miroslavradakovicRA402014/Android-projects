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
    protected float percentageHigh = (float)68;
    protected String percentStr;

    public ChartRed(Context context, AttributeSet attrs) {
        super(context, attrs);

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        rectHigh.set(left, top, left+width, top + width);
        canvas.drawArc(rectHigh, -90, 360, true, paintChartHighBg);
        if(percentageHigh!=0) {
            canvas.drawArc(rectHigh, -90, ((float)3.6*percentageHigh), true, paintChartHigh);
        }

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintChartHigh.descent() + paintChartHigh.ascent()) / 2)) ;

        paintChartHigh.setColor(Color.BLACK);
        paintChartHigh.setTextAlign(Paint.Align.CENTER);
        paintChartHigh.setTextSize(50);
        canvas.drawText(percentStr,xPos,yPos,paintChartHigh);
    }

    public void setPercentageHigh(float percentageHigh) {
        this.percentageHigh = percentageHigh;
        invalidate();
    }
}
