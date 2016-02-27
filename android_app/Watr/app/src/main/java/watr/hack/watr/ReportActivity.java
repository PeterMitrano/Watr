package watr.hack.watr;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ReportActivity extends Activity implements View.OnClickListener, AsyncPoster.PosterListener {

    Report report;
    Button submitButton;
    FrameLayout submitFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        submitFrame = (FrameLayout) findViewById(R.id.submit_frame);
        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        report = new Report(this, getResources(), getFragmentManager());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_button){
            submitFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
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
            submitFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        else {
            Snackbar.make(findViewById(android.R.id.content),
                    "Whoops! Please connect to the internet.",
                    Snackbar.LENGTH_LONG).show();
            submitFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        }
    }
}
