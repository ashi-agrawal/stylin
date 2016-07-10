public class DrawOverlayView extends View {
    private List<Point> rectPoints;
    private Paint drawPaint;

    public DrawOverlayView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
        rectPoints = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas){
        for (Point p: rectPoints){
            drawRect(p.x - 5, p.y - 5, p.x + 5, p.y + 5, drawPaint);
        }
    }

    private void setUpPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStrokeWidth(1);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float.touchY = event.getY();
        rectPoints.add(new Point(Math.round(touchX), Math.round(touchY)));
        postInvalidate();
        return true;
    }

}
