package backup.wingos.com.wingbackup.custom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import backup.wingos.com.wingbackup.R;

/**
 * Created by xiongbo on 2018/11/14.
 */

public class CustomCaptureActivity extends CaptureActivity {

    private ImageView oldBackImg;

    private CustomDecoratedBarcodeView customDecoratedBarcodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oldBackImg = findViewById(R.id.oldBackImg);
        oldBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected DecoratedBarcodeView initializeContent(){
        setContentView(R.layout.custom_activity_zxing_capture);
        customDecoratedBarcodeView= findViewById(R.id.zxing_custom_barcode_scanner);
        return customDecoratedBarcodeView;
    }

    @Override
    protected void onPause() {
        customDecoratedBarcodeView.stopThread();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        customDecoratedBarcodeView.stopThread();
        super.onDestroy();
    }

}
