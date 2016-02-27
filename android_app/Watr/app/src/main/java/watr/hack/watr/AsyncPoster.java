package watr.hack.watr;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

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
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("http://www.android.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        }
        catch(MalformedInputException e) {}
        catch(IOException e) {}
        finally {
            urlConnection.disconnect();
        }

        return null;
    }

    private void readStream(InputStream in) {
    }

    public void onPostExecute(Boolean connected) {
        Log.w("completed", "on post execute");
    }
}
