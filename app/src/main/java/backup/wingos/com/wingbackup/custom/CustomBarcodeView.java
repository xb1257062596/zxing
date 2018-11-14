package backup.wingos.com.wingbackup.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.camera.CameraInstance;

/**
 * Created by xiongbo on 2018/11/14.
 */

public class CustomBarcodeView extends BarcodeView {
    public CustomBarcodeView(Context context) {
        super(context);
    }

    public CustomBarcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBarcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initializeAttributes(AttributeSet attrs){
        super.initializeAttributes(attrs);
    }

    /**
     * 造成了延时效果
     * 本来为 2000000000 修改成了 10000000
     */
    @Override
    public void pauseAndWait() {
        CameraInstance instance = getCameraInstance();
        pause();
        long startTime = System.nanoTime();
        while(instance != null && !instance.isCameraClosed()) {
            if(System.nanoTime() - startTime > 10000000) {
                // Don't wait for longer than 2 seconds
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
