package footprint.footprint;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class PreLoadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preload_page);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();       // 2.5 초후 이미지를 닫아버림
            }
        }, 2500);
    }

}