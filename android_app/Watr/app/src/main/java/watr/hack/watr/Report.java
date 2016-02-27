package watr.hack.watr;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DialerFilter;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by peter on 2/26/16.
 */
public class Report {

    private class ReportEntry {

        private final String key;
        public Object value;

        ReportEntry(String key){
            this.key = key;
        }
    }

    private class ChoosableReportEntry extends ReportEntry implements StringSelectorDialog.QualitySelectListener {

        private String items[];
        private TextView label;
        private DialogFragment dialog;

        ChoosableReportEntry(String key, FragmentManager fragmentManager, String items[]) {
            super(key);
            this.items = items;
            dialog = new StringSelectorDialog();
            Bundle args = new Bundle();
            args.putStringArray("items", items);
            dialog.show(fragmentManager, "selector_fragment");
        }

        @Override
        public void onItemClick(int index) {
            value = items[index];
        }
    }

    private class SliderReportEntry extends ReportEntry implements SeekBar.OnSeekBarChangeListener {

        private DialogFragment dialog;

        SliderReportEntry(String key, FragmentManager fragmentManager, String units, int min, int max, int unit) {
            super(key);
            dialog = new SliderDialog();
            Bundle args = new Bundle();
            args.putString("units", units);
            args.putInt("min", min);
            args.putInt("max", max);
            args.putInt("unit", unit);
            dialog.show(fragmentManager, "slider_fragment");
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){
                value = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    }
    private List<ReportEntry> entries;

    public Report(Resources res, FragmentManager fragmentManager){
        entries = new ArrayList<ReportEntry>();
        entries.add(new ChoosableReportEntry("smell", fragmentManager, res.getStringArray(R.array.smells)));
        entries.add(new ChoosableReportEntry("smell", fragmentManager, res.getStringArray(R.array.tastes)));
        entries.add(new ChoosableReportEntry("smell", fragmentManager, res.getStringArray(R.array.colors)));
        entries.add(new SliderReportEntry("ph",fragmentManager, "ppm", 0, 14, 1));
        entries.add(new SliderReportEntry("lead", fragmentManager, "ppm", 0, 1000, 1));
    }

    public JSONObject getBody() {
        JSONObject data = new JSONObject();

        try {
            for (ReportEntry entry : entries){
                data.put(entry.key, entry.value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return data;
    }
}


/**
 lead (parts per million)
 bacteria (organisms per million)
 ph (0-14)
 hardness (milligrams per liter)
 chlorine (parts per million)
 nitrates (parts per million)
 color
 taste
 smell
 zip
 */
