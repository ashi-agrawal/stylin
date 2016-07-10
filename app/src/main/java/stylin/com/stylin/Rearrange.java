package stylin.com.stylin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Rearrange extends AppCompatActivity {

    private Bitmap m_bitmap;
    private Button doneSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rearrange);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.IMAGE_FILE)) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE),
                    0,
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE).length
            );
            final DrawOverlayView dovRect = (DrawOverlayView) findViewById(R.id.dovRect);
            dovRect.addBitmap(bitmap);
            doneSelectButton = (Button) findViewById(R.id.doneSelectButton);
            doneSelectButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    updateBitmap(dovRect);
                }
            });
        }
    }

    private void updateBitmap(DrawOverlayView dov){
        int w = dov.colorballs.get(3).getX() - dov.colorballs.get(0).getX() + 1;
        int h = dov.colorballs.get(1).getY() - dov.colorballs.get(0).getY() + 1;
        int background = dov.bitmap.getPixel(dov.colorballs.get(0).getX() - 15, dov.colorballs.get(0).getY() - 15);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        for (int x = dov.colorballs.get(0).getX() + 95; x <= dov.colorballs.get(3).getX() + 95; x++) {
            for (int y = dov.colorballs.get(0).getY() + 95; y <= dov.colorballs.get(2).getY() + 95; y++) {
                bmp.setPixel(x - (dov.colorballs.get(0).getX() + 95), y - (dov.colorballs.get(0).getY() + 95), dov.bitmap.getPixel(x, y));
                dov.bitmap.setPixel(x, y, background);
            }
        }
        final ImageView ivDrag = (ImageView) findViewById(R.id.ivDrag);
        ivDrag.setImageBitmap(bmp);
        ivDrag.setX(dov.colorballs.get(0).getX() + 500);
        ivDrag.setY(dov.colorballs.get(0).getY() + 160);
        ivDrag.setBackgroundColor(Color.BLACK);
        ivDrag.setPadding(1, 1, 1, 1);
        dov.updateBalls();
        ivDrag.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                    }
                    case MotionEvent.ACTION_MOVE: {
                        ivDrag.setX(event.getX());
                        ivDrag.setY(event.getY());
                    }
                    case MotionEvent.ACTION_UP: {
                    }
                    return true;
                }
                return false;
            }});
    }
}
