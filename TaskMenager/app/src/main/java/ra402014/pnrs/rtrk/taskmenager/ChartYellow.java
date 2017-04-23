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
    protected Paint paintChartBgMedium;
    protected RectF rectMedium;
    protected float percentageMedium = (float)30;
    protected String percentStr;

    public ChartYellow(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintChartMedium = new Paint();
        paintChartMedium.setColor(Color.YELLOW);
        paintChartMedium.setAntiAlias(true);
        paintChartMedium.setStyle(Paint.Style.FILL);

        paintChartBgMedium= new Paint();
        paintChartBgMedium.setColor(Color.BLUE);
        paintChartBgMedium.setAntiAlias(true);
        paintChartBgMedium.setStyle(Paint.Style.FILL);

        rectMedium = new RectF();

        percentStr = Float.toString(percentageMedium)+"%";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        rectMedium.set(left, top, left+width, top + width);
        canvas.drawArc(rectMedium, -90, 360, true, paintChartBgMedium);
        if(percentageMedium!=0) {
            canvas.drawArc(rectMedium, -90, ((float)3.6*percentageMedium), true, paintChartMedium);
        }

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintChartMedium.descent() + paintChartMedium.ascent()) / 2)) ;

        paintChartMedium.setColor(Color.BLACK);
        paintChartMedium.setTextAlign(Paint.Align.CENTER);
        paintChartMedium.setTextSize(50);
        canvas.drawText(percentStr,xPos,yPos,paintChartMedium);
    }

    public void setPercentageMedium(float percentageMedium) {
        this.percentageMedium = percentageMedium;
        invalidate();
    }
}
