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


public class HttpGetArticleTask extends AsyncTask<String, String, ArticleData> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArticleData doInBackground(String... values) {
        // TODO Auto-generated method stub
        Log.d("HGAT", "THREAD START");
        ArticleData result = new ArticleData();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        InputStream inputStream;
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        HttpPost httpPost = new HttpPost("http://52.79.139.48/han/id_sel.php");
        try {

            String[] receiveData = new String[8];

            Log.d("HGAT", "SETMODE PASS");
            builder.addTextBody("id", values[0], ContentType.create("Multipart/related", "utf8"));


            Log.d("HGAT", "ADDTEXT PASS");


            Log.d("HGAT", "HTTPPOST PASS");

            httpPost.setEntity(builder.build());

            Log.d("HGAT", "SETENTITY PASS");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            Log.d("HGAT", "HTTPRESPONSE PASS");

            HttpEntity httpEntity = httpResponse.getEntity();

            Log.d("HGAT", "HTTPENTITY PASS");

            inputStream = httpEntity.getContent();

            Log.d("HGAT", "INPUTSTREAM PASS");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "utf8"));



            String line;
            int i=0;
            int j=0;


            while((line = bufferedReader.readLine()) != null) {

                Log.d("HGAT", line);
                while(true) {


                    receiveData[j] = line.substring(0, ((i = line.indexOf(";"))) );
                    String subString = line;
                    line = subString.substring(i + 1);

                    Log.d("HGAT", receiveData[j]);
                    j++;

                    if(line.indexOf("END",0) == 0) {
                        break;
                    }
                }


            }
                result.id = Integer.parseInt(receiveData[0]);
                result.date = receiveData[1];
                result.time = receiveData[2];
                result.article = receiveData[3];
                result.filename = receiveData[4];
                result.latitude = Double.parseDouble(receiveData[5]);
                result.longitude = Double.parseDouble(receiveData[6]);
                //result.cnt = Integer.parseInt(receiveData[7]);




            inputStream.close();
            bufferedReader.close();


            return result;


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArticleData result) {
        super.onPostExecute(result);


    }

}
