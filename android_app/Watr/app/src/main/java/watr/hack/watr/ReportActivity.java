package watr.hack.watr;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReportActivity extends Activity implements View.OnClickListener, AsyncPoster.PosterListener {

    EditText zipEdit;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button createReportButton = (Button) findViewById(R.id.submitReportButton);
        createReportButton.setOnClickListener(this);

        TextView selectQualityButton = (TextView) findViewById(R.id.selectColorButton);
        selectQualityButton.setOnClickListener(this);

        zipEdit = (EditText) findViewById(R.id.zipEdit);

        report = new Report(getResources(), getFragmentManager());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitReportButton){
            AsyncPoster poster = new AsyncPoster(report);
            poster.addListener(this);
            poster.execute();
        }
    }

    @Override
    public void onCompleted(Boolean connected) {
        if (connected) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Thank you! We've submitted your report",
                    Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(findViewById(android.R.id.content),
                    "Whoops! Please connect to the internet.",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
