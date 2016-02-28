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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/26/16.
 */
public class AsyncPoster extends AsyncTask<Void, Void, Boolean> {

    private Report report;
    private String responseString;
    private List<PosterListener> listeners;

    public interface PosterListener {

        public void onCompleted(Boolean connected);

    }

    public AsyncPoster(Report report) {
        this.report = report;
        listeners = new ArrayList<PosterListener>();
    }

    public void addListener(PosterListener listener) {
       listeners.add(listener);
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        byte[] postData;
        postData = report.getBody().toString().getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        String requestURL = "http://ec2-54-175-180-102.compute-1.amazonaws.com:3000/reports";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (int c = in.read(); c != -1; c = in.read()) {
                responseString += ((char)c);
            }

            Log.e("response", responseString);

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void onPostExecute(Boolean connected) {
        for (PosterListener listener : listeners) {
            listener.onCompleted(connected);
        }
    }
}
