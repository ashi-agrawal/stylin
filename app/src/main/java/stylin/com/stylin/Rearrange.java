package stylin.com.stylin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
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

//            ImageView _imv = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE),
                    0,
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE).length
            );
//            _imv.setImageBitmap(_bitmap);
            final DrawOverlayView dovRect = (DrawOverlayView) findViewById(R.id.dovRect);
            dovRect.addBitmap(bitmap);
//            RelativeLayout rl = (RelativeLayout) findViewById(R.id.base);
//            rl.addView(_imv);
            doneSelectButton = (Button) findViewById(R.id.doneSelectButton);
            doneSelectButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    dovRect.updateBitmap();
                }
            });
        }


    }
}
