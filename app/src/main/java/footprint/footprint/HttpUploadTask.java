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
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 서버 통신 메소드 시작
 *
 *  AsyncTask는 generic이기 때문에 extends 시 3개의 generic type을 붙여주게 되는데,
 *  첫번째가 doInBackground() 에서 사용할 Params,
 *  두번째가 onProgressUpdate()에서 사용할 Progress,
 *  세번째가 onPostExecute()에서 사용할 Result의 type이다.
 *
 * 아파치 httpClient는 4.3.2 버전을 사용!!
 * 타 버전을 사용할 경우 컴파일 에러가 발생할 수 있다
 *
 * http://derveljunit.tistory.com/5 참조
 *
 * 이미지 리사이징 관련글
 * http://chiyo85.tistory.com/entry/Android-Bitmap-Object-Resizing-Tip
 *
 *
 */
public class HttpUploadTask extends AsyncTask<String, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... values) {
        // TODO Auto-generated method stub
        Log.d("UPLOAD", "THREAD START");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        InputStream inputStream;
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        HttpPost httpPost = new HttpPost("http://52.79.139.48/uploadArticle.php");

        try {

            Log.d("UPLOAD", "SETMODE PASS");
            builder.addPart("image", new FileBody(new File(values[0])));
            builder.addTextBody("id", values[1], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("date", values[2], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("time", values[3], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("article", values[4], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("filename", values[5], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("latitude", values[6], ContentType.create("Multipart/related", "utf8"));
            builder.addTextBody("longitude", values[7], ContentType.create("Multipart/related", "utf8"));

            Log.d("UPLOAD", "ADDTEXT PASS");


            Log.d("UPLOAD", "HTTPPOST PASS");

            httpPost.setEntity(builder.build());

            Log.d("UPLOAD", "SETENTITY PASS");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            Log.d("UPLOAD", "HTTPRESPONSE PASS");

            HttpEntity httpEntity = httpResponse.getEntity();

            Log.d("UPLOAD", "HTTPENTITY PASS");

            inputStream = httpEntity.getContent();

            Log.d("UPLOAD", "INPUTSTREAM PASS");




            inputStream.close();



        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return "true";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }


}
