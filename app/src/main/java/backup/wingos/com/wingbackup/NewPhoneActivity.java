package backup.wingos.com.wingbackup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by xiongbo on 2018/11/13.
 */

public class NewPhoneActivity extends AppCompatActivity {

    private static String TAG = "NewPhoneActivity";

    //生成的二维码图片
    private Bitmap qrCodeBitmap;
    //二维码传输的数据
    private String content="Hello+sjfisadfisfh---";

    private ImageView qrCodeImg;

    private ImageView backImg;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_new_phone);
        qrCodeImg = findViewById(R.id.qrCodeImg);
        backImg = findViewById(R.id.backImg);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        createQrCode();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * 当正在连接的时候显示该Dialog
     */
    private void showConnectDialog(){
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_connect,null);
        AlertDialog dialog = new AlertDialog.Builder(this,R.style.ConnectDialogTheme)
                .setView(view)
                .create();
        //点击对话框以外的地方 对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        WindowManager.LayoutParams lp =dialog.getWindow().getAttributes();
        lp.width =getResources().getDisplayMetrics().widthPixels-80;
        lp.height=getResources().getDisplayMetrics().heightPixels-80;
        lp.gravity=Gravity.CENTER_VERTICAL;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 用来生成所需要的数据
     */
    public void setContent(String content){
        createQrCode();
    }


    /**
     * 创建二维码
     */
    private void createQrCode(){
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400);
            bitMatrix =deleteWhite(bitMatrix);
            qrCodeBitmap=barcodeEncoder.createBitmap(bitMatrix);
            qrCodeImg.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除二维码的空白区域
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix)
    {
        int[] rec = matrix.getEnclosingRectangle();

        int resWidth = rec[2] ;
        int resHeight = rec[3] ;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++)
        {
            for (int j = 0; j < resHeight; j++)
            {
                if(matrix.get(i+rec[0],j+rec[1]))
                    resMatrix.set(i,j);
            }
        }
        return resMatrix;
    }

}
