package watr.hack.watr;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by peter on 2/26/16.
 */
public class AsyncPoster extends AsyncTask<Void, Void, Boolean> {

    Report report;

    public AsyncPoster(Report report) {
        this.report = report;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String urlParameters = "quality=" + report.quality
                + "&town=" + report.town + "&state=" + report.state;
        byte[] postData = new byte[0];
        postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        String requestURL = "http://httpbin.org/post";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String str = "";
            for (int c = in.read(); c != -1; c = in.read()) {
                str += ((char)c);
            }
            Log.e("response", str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onPostExecute(Boolean connected) {
        Log.w("completed", "on post execute");
    }
}
