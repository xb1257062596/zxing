package backup.wingos.com.wingbackup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newPhoneBtn;

    private Button oldPhoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newPhoneBtn = findViewById(R.id.newPhoneBtn);
        oldPhoneBtn = findViewById(R.id.oldPhoneBtn);

        newPhoneBtn.setOnClickListener(this);
        oldPhoneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.oldPhoneBtn:
                break;
            case R.id.newPhoneBtn:
                break;
        }
    }
}
