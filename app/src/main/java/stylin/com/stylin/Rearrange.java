package stylin.com.stylin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Rearrange extends AppCompatActivity {

    private Bitmap m_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rearrange);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.IMAGE_FILE)) {

//            ImageView _imv = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE),
                    0,
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE).length
            );
//            _imv.setImageBitmap(_bitmap);
            DrawOverlayView dovRect = (DrawOverlayView) findViewById(R.id.dovRect);
            dovRect.addBitmap(bitmap);
//            RelativeLayout rl = (RelativeLayout) findViewById(R.id.base);
//            rl.addView(_imv);
        }
    }
}
