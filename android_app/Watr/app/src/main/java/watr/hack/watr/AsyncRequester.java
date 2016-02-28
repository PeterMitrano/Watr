package watr.hack.watr;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/26/16.
 */
public class AsyncRequester extends AsyncTask<Void, Void, List<JSONReport>> {

    private String responseString;
    private List<RequesterListener> listeners;

    public interface RequesterListener {

        public void onCompleted(List<JSONReport> report);

    }

    public AsyncRequester() {
        listeners = new ArrayList<RequesterListener>();
    }

    public void addListener(RequesterListener listener) {
       listeners.add(listener);
    }

    @Override
    protected List<JSONReport> doInBackground(Void... params) {


        try {
            String url = "http://ec2-54-175-180-102.compute-1.amazonaws.com:3000/reports";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            while ((responseString = in.readLine()) != null) {
                response.append(responseString);
            }
            in.close();

            JsonParser parser = new JsonParser();
            JsonArray json = parser.parse(response.toString()).getAsJsonArray();

            List<JSONReport> reports = new ArrayList<>();
            for (int i=0;i<json.size();i++){
                reports.add(new JSONReport(json.get(i).getAsJsonObject()));
            }
            return reports;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onPostExecute(List<JSONReport> reports) {
        if (reports == null) {
            Log.e("post execute", "fail!");
        }
        else {
            Log.e("post execute", "done!");
            for (RequesterListener listener : listeners) {
                listener.onCompleted(reports);
            }
        }
    }
}
