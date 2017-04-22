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

    protected float percentageMedium = (float)10;

    public ChartYellow(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintChartMedium = new Paint();
        paintChartMedium.setColor(Color.RED);
        paintChartMedium.setAntiAlias(true);
        paintChartMedium.setStyle(Paint.Style.FILL);

        paintChartBgMedium= new Paint();
        paintChartBgMedium.setColor(Color.BLUE);
        paintChartBgMedium.setAntiAlias(true);
        paintChartBgMedium.setStyle(Paint.Style.FILL);

        rectMedium = new RectF();
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
    }

    public void setPercentageMedium(float percentageMedium) {
        this.percentageMedium = percentageMedium;
        invalidate();
    }
}
