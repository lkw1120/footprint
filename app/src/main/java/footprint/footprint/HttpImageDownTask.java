package footprint.footprint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpImageDownTask extends AsyncTask<String, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        // TODO Auto-generated method stub
        Bitmap downloadImage = null;
        try{
            URL myFileUrl = new URL("http://52.79.139.48/imageStorage/" + urls[0]);
            Log.d("FILEDOWN", urls[0]);
            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            downloadImage = BitmapFactory.decodeStream(is);
        }catch(IOException e){
            e.printStackTrace();
        }
        return downloadImage;
    }
}