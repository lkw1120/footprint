package footprint.footprint;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class HttpRcmdTask extends AsyncTask<String, String, String> {

    /**
     *
     * 추천하기
     */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... values) {
        // TODO Auto-generated method stub
        Log.d("LBRS", "THREAD START");
        LinkedList<LBRS> receiveData = new LinkedList<>();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        InputStream inputStream;
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        HttpPost httpPost = new HttpPost("http://52.79.139.48/recommendChecker.php");

        String checker = null;
        try {

            Log.d("LBRS", "SETMODE PASS");
            builder.addTextBody("key", values[0], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("date", values[1], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("time", values[2], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("article", values[3], ContentType.create("Multipart/related", "utf8"));

            Log.d("LBRS", "ADDTEXT PASS");

            Log.d("LBRS", "HTTPPOST PASS");

            httpPost.setEntity(builder.build());

            Log.d("LBRS", "SETENTITY PASS");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            Log.d("LBRS", "HTTPRESPONSE PASS");

            HttpEntity httpEntity = httpResponse.getEntity();

            Log.d("LBRS", "HTTPENTITY PASS");

            inputStream = httpEntity.getContent();

            Log.d("LBRS", "INPUTSTREAM PASS");


            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "utf8"));


            checker = bufferedReader.readLine();





        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }

        Log.d("LBRS", "FAIL_FUNCTION");
        return checker;

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }



}
