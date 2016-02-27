package watr.hack.watr;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/26/16.
 */
public class Report {

    private abstract class ReportEntry implements View.OnClickListener {

        private final String key;
        public Object value;

        ReportEntry(String key) {
            this.key = key;
        }
    }

    private class ChoosableReportEntry extends ReportEntry implements StringSelectorDialog.StringSelectListener {

        private String items[];
        private StringSelectorDialog dialog;
        private FragmentManager fragmentManager;

        ChoosableReportEntry(String key, FragmentManager fragmentManager, String items[]) {
            super(key);
            this.items = items;
            this.fragmentManager = fragmentManager;

            Bundle args = new Bundle();
            args.putStringArray("items", items);

            dialog = new StringSelectorDialog();
            dialog.addListener(this);
            dialog.setArguments(args);
        }

        @Override
        public void onItemClick(int index) {
            value = items[index];
        }

        @Override
        public void onClick(View v) {
            dialog.show(fragmentManager, "selector_fragment");
        }
    }

    private class TextReportEntry extends ReportEntry implements ZipcodeDialog.ZipcodeListener {

        private ZipcodeDialog dialog;
        private FragmentManager fragmentManager;

        TextReportEntry(String key, FragmentManager fragmentManager) {
            super(key);
            this.fragmentManager = fragmentManager;
            Bundle args = new Bundle();
            args.putString("label", key);
            dialog = new ZipcodeDialog();
            dialog.addListener(this);
        }

        @Override
        public void onClick(View v) {
            dialog.show(fragmentManager, "text_fragment");
        }

        @Override
        public void onTextSubmit(String value) {
           this.value = value;
        }
    }

    private class SliderReportEntry extends ReportEntry implements SliderDialog.SliderListener {

        private SliderDialog dialog;
        private FragmentManager fragmentManager;

        SliderReportEntry(String key, FragmentManager fragmentManager, String units, int min, int max, int unit) {
            super(key);
            this.fragmentManager = fragmentManager;

            Bundle args = new Bundle();
            args.putString("units", units);
            args.putInt("min", min);
            args.putInt("max", max);
            args.putInt("unit", unit);

            dialog = new SliderDialog();
            dialog.setArguments(args);
            dialog.addListener(this);
        }

        @Override
        public void onClick(View v) {
            dialog.show(fragmentManager, "slider_fragment");
        }

        @Override
        public void onSliderUpdate(int value) {
            this.value = value;
        }
    }

    private List<ReportEntry> entries;
    private ReportEntry zipcodeEntry;

    public Report(Activity activity, Resources res, FragmentManager fragmentManager) {
        ChoosableReportEntry smell = new ChoosableReportEntry("Smell", fragmentManager, res.getStringArray(R.array.smells));
        ChoosableReportEntry taste = new ChoosableReportEntry("Tastes", fragmentManager, res.getStringArray(R.array.tastes));
        ChoosableReportEntry color = new ChoosableReportEntry("Colors", fragmentManager, res.getStringArray(R.array.colors));
        SliderReportEntry ph = new SliderReportEntry("pH", fragmentManager, "ppm", 0, 14, 1);
        SliderReportEntry lead = new SliderReportEntry("Lead", fragmentManager, "ppm", 0, 1000, 1);
        zipcodeEntry = new TextReportEntry("Zipcode", fragmentManager);

        TextView smellItem = (TextView) activity.findViewById(R.id.selectSmellItem);
        smellItem.setOnClickListener(smell);

        TextView tasteItem = (TextView) activity.findViewById(R.id.selectTasteItem);
        tasteItem.setOnClickListener(taste);

        TextView colorItem = (TextView) activity.findViewById(R.id.selectColorItem);
        colorItem.setOnClickListener(color);

        TextView phItem = (TextView) activity.findViewById(R.id.selectpHItem);
        phItem.setOnClickListener(ph);

        TextView leadItem = (TextView) activity.findViewById(R.id.selectLeadItem);
        leadItem.setOnClickListener(lead);

        TextView zipcodeItem = (TextView) activity.findViewById(R.id.zipcodeItem);
        zipcodeItem.setOnClickListener(zipcodeEntry);

        entries = new ArrayList<>();
        entries.add(smell);
        entries.add(taste);
        entries.add(color);
        entries.add(ph);
        entries.add(lead);
    }

    /**
     * lead (parts per million)
     * bacteria (organisms per million)
     * ph (0-14)
     * hardness (milligrams per liter)
     * chlorine (parts per million)
     * nitrates (parts per million)
     * color
     * taste
     * smell
     * zip
     */
    public JSONObject getBody() {
        JSONObject data = new JSONObject();

        try {

            JSONObject measurements = new JSONObject();
            for (ReportEntry entry : entries) {
                measurements.put(entry.key, entry.value);
            }

            data.put("measurements", measurements);
            data.put("zipcode", zipcodeEntry.value);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return data;
    }

}
