package watr.hack.watr;

import android.os.AsyncTask;

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
        return null;
    }

    public void onPostExecute(Boolean connected) {

    }
}
