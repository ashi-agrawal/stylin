package stylin.com.stylin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawOverlayView extends View {
    private List<Point> rectPoints;
    private Paint drawPaint;
    private Bitmap bitmap;

    public DrawOverlayView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
        rectPoints = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap, 0, 0, null);
        for (Point p: rectPoints){
            canvas.drawRect(p.x - 100, p.y - 100, p.x + 100, p.y + 100, drawPaint);
        }
    }

    public void addBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        postInvalidate();
    }

    private void setUpPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStrokeWidth(1);
        drawPaint.setStyle(Paint.Style.STROKE);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();
        rectPoints.add(new Point(Math.round(touchX), Math.round(touchY)));
        postInvalidate();
        return true;
    }

}
