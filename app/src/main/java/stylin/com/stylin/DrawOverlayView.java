package stylin.com.stylin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DrawOverlayView extends View {
    private List<Point> rectPoints;
    private Paint drawPaint;
    private Paint paint;
    public Bitmap bitmap;
    private Bitmap bmp;
    Point[] points = new Point[4];

    /**
     * point1 and point 3 are of same group and same as point 2 and point4
     */
    int groupId = -1;
    public ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>();
    // array that holds the balls
    private int balID = 0;
    // variable to know what ball is being dragged
    private Canvas canvas;
    private Button doneSelectButton;

    public DrawOverlayView (Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        rectPoints = new ArrayList<Point>();
    }

//    public void updateBitmap() {
//        int w = colorballs.get(3).getX() - colorballs.get(0).getX() + 1;
//        int h = colorballs.get(1).getY() - colorballs.get(0).getY() + 1;
//        int background = bitmap.getPixel(colorballs.get(0).getX() - 15, colorballs.get(0).getY() - 15);
//
//        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
//        bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
//        Canvas canvas = new Canvas(bmp);
//        for (int x = colorballs.get(0).getX() + 95; x <= colorballs.get(3).getX() + 95; x++) {
//            for (int y = colorballs.get(0).getY() + 95; y <= colorballs.get(2).getY() + 95; y++) {
//                bmp.setPixel(x - (colorballs.get(0).getX() + 95), y - (colorballs.get(0).getY() + 95), bitmap.getPixel(x, y));
//                bitmap.setPixel(x, y, background);
//            }
//        }
//        postInvalidate();
//    }

    public DrawOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (bmp != null) {
            canvas.drawBitmap(bmp, 0, 0, null);
        }
        if(points[3]==null) //point4 null when user did not touch and move on screen.
            return;
        int left, top, right, bottom;
        left = points[0].x;
        top = points[0].y;
        right = points[0].x;
        bottom = points[0].y;
        for (int i = 1; i < points.length; i++) {
            left = left > points[i].x ? points[i].x:left;
            top = top > points[i].y ? points[i].y:top;
            right = right < points[i].x ? points[i].x:right;
            bottom = bottom < points[i].y ? points[i].y:bottom;
        }
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);

        //draw stroke
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawRect(
                left + colorballs.get(0).getWidthOfBall() / 2,
                top + colorballs.get(0).getWidthOfBall() / 2,
                right + colorballs.get(2).getWidthOfBall() / 2,
                bottom + colorballs.get(2).getWidthOfBall() / 2, paint);
        //fill the rectangle
        paint.setStrokeWidth(0);
        canvas.drawRect(
                left + colorballs.get(0).getWidthOfBall() / 2,
                top + colorballs.get(0).getWidthOfBall() / 2,
                right + colorballs.get(2).getWidthOfBall() / 2,
                bottom + colorballs.get(2).getWidthOfBall() / 2, paint);

        //draw the corners
        BitmapDrawable bitmap = new BitmapDrawable();
        // draw the balls on the canvas
        paint.setColor(Color.BLACK);
        paint.setTextSize(18);
        paint.setStrokeWidth(0);
        for (int i =0; i < colorballs.size(); i ++) {
            ColorBall ball = colorballs.get(i);
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(),
                    paint);

            canvas.drawText("" + (i+1), ball.getX(), ball.getY(), paint);
        }
//        for (Point p: rectPoints){
//            canvas.drawRect(p.x - 100, p.y - 100, p.x + 100, p.y + 100, drawPaint);
//        }
    }

    public void addBitmap(Bitmap bitmap){
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.bitmap = mutableBitmap;
        postInvalidate();
    }

    public void updateBalls(){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
//        float touchX = event.getX();
//        float touchY = event.getY();
//        rectPoints.add(new Point(Math.round(touchX), Math.round(touchY)));
//        postInvalidate();
//        return true;
        int eventaction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventaction) {

            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
                // a ball
                if (points[0] == null) {
                    //initialize rectangle.
                    points[0] = new Point();
                    points[0].x = X;
                    points[0].y = Y;

                    points[1] = new Point();
                    points[1].x = X;
                    points[1].y = Y + 30;

                    points[2] = new Point();
                    points[2].x = X + 30;
                    points[2].y = Y + 30;

                    points[3] = new Point();
                    points[3].x = X +30;
                    points[3].y = Y;

                    balID = 2;
                    groupId = 1;
                    // declare each ball with the ColorBall class
                    for (Point pt : points) {
                        colorballs.add(new ColorBall(getContext(), R.mipmap.circle, pt));
                    }
                } else {
                    //resize rectangle
                    balID = -1;
                    groupId = -1;
                    for (int i = colorballs.size()-1; i>=0; i--) {
                        ColorBall ball = colorballs.get(i);
                        // check if inside the bounds of the ball (circle)
                        // get the center for the ball
                        int centerX = ball.getX() + ball.getWidthOfBall();
                        int centerY = ball.getY() + ball.getHeightOfBall();
                        paint.setColor(Color.CYAN);
                        // calculate the radius from the touch to the center of the
                        // ball
                        double radCircle = Math
                                .sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
                                        * (centerY - Y)));

                        if (radCircle < ball.getWidthOfBall()) {

                            balID = ball.getID();
                            if (balID == 1 || balID == 3) {
                                groupId = 2;
                            } else {
                                groupId = 1;
                            }
                            invalidate();
                            break;
                        }
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE: // touch drag with the ball


                if (balID > -1) {
                    // move the balls the same as the finger
                    colorballs.get(balID).setX(X);
                    colorballs.get(balID).setY(Y);

                    paint.setColor(Color.CYAN);
                    if (groupId == 1) {
                        colorballs.get(1).setX(colorballs.get(0).getX());
                        colorballs.get(1).setY(colorballs.get(2).getY());
                        colorballs.get(3).setX(colorballs.get(2).getX());
                        colorballs.get(3).setY(colorballs.get(0).getY());
                    } else {
                        colorballs.get(0).setX(colorballs.get(1).getX());
                        colorballs.get(0).setY(colorballs.get(3).getY());
                        colorballs.get(2).setX(colorballs.get(3).getX());
                        colorballs.get(2).setY(colorballs.get(1).getY());
                    }

                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping

                break;
        }
        // redraw the canvas
        invalidate();
        return true;
    }



}
