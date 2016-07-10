package stylin.com.stylin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Rearrange extends AppCompatActivity {

    private Bitmap m_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rearrange);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.IMAGE_FILE)) {
            ImageView _imv = new ImageView(this);
            Bitmap _bitmap = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE),
                    0,
                    intent.getByteArrayExtra(MainActivity.IMAGE_FILE).length
            );
            _imv.setImageBitmap(_bitmap);
        }
    }
}
