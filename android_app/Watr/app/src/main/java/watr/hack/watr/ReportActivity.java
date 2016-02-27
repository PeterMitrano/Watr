package watr.hack.watr;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReportActivity extends Activity implements View.OnClickListener, AsyncPoster.PosterListener,
        QualitySelectorDialog.QualitySelectListener {

    EditText townEdit, stateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button createReportButton = (Button) findViewById(R.id.submitReportButton);
        createReportButton.setOnClickListener(this);

        Button selectQualityButton = (Button) findViewById(R.id.selectQualityButton);
        selectQualityButton.setOnClickListener(this);

        townEdit = (EditText) findViewById(R.id.townEdit);
        stateEdit = (EditText) findViewById(R.id.stateEdit);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitReportButton){
            Report report = new Report();
            report.state = stateEdit.getText().toString();
            report.town = townEdit.getText().toString();
            report.quality = 3;
            AsyncPoster poster = new AsyncPoster(report);
            poster.addListener(this);
            poster.execute();
        }
        else if (v.getId() == R.id.selectQualityButton) {
            DialogFragment newFragment = new QualitySelectorDialog();
            newFragment.show(getFragmentManager(), "quality_selector_fragment");
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

    @Override
    public void onItemClick(int index) {
        Resources res = getResources();
        String quality = res.getStringArray(R.array.qualities_array)[index];
    }
}
