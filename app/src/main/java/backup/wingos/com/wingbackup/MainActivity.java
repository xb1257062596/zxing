package backup.wingos.com.wingbackup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import backup.wingos.com.wingbackup.custom.CustomCaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newPhoneBtn;

    private Button oldPhoneBtn;

    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newPhoneBtn = findViewById(R.id.newPhoneBtn);
        oldPhoneBtn = findViewById(R.id.oldPhoneBtn);
        testBtn = findViewById(R.id.testBtn);

        newPhoneBtn.setOnClickListener(this);
        oldPhoneBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newPhoneBtn:
                startActivity(new Intent(MainActivity.this,NewPhoneActivity.class));
                break;
            case R.id.oldPhoneBtn:
                scanQrCode();
                break;
            case R.id.testBtn:
                startActivity(new Intent(MainActivity.this,OldPhoneActivity.class));
        }
    }

    /**
     * 扫描二维码
     */
    private void scanQrCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String content=result.getContents();
        if(result != null) {
            if(content == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, content, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
