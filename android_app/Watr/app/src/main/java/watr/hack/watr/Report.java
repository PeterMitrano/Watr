package watr.hack.watr;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/26/16.
 */
public class Report {

    private List<ReportEntry> entries;
    private ReportEntry zipcodeEntry;

    public Report(Activity activity, Resources res, FragmentManager fragmentManager) {
        ChoosableReportEntry smell = new ChoosableReportEntry("Smell",
                fragmentManager,
                res.getStringArray(R.array.smells),
                (TextView) activity.findViewById(R.id.selectSmellValue));

        ChoosableReportEntry taste = new ChoosableReportEntry("Tastes",
                fragmentManager,
                res.getStringArray(R.array.tastes),
                (TextView) activity.findViewById(R.id.selectTasteValue));

        ChoosableReportEntry color = new ChoosableReportEntry("Colors",
                fragmentManager,
                res.getStringArray(R.array.colors),
                (TextView) activity.findViewById(R.id.selectColorValue));

        SliderReportEntry ph = new SliderReportEntry("pH",
                fragmentManager, "ppm", 14, 1,
                (TextView) activity.findViewById(R.id.selectpHValue));

        SliderReportEntry lead = new SliderReportEntry("Lead",
                fragmentManager, "ppm", 1000, 1,
                (TextView) activity.findViewById(R.id.selectLeadValue));

        zipcodeEntry = new TextReportEntry("Zipcode", fragmentManager,
                (TextView) activity.findViewById(R.id.selectZipcodeValue));

        LinearLayout smellItem = (LinearLayout) activity.findViewById(R.id.selectSmellItem);
        smellItem.setOnClickListener(smell);

        LinearLayout tasteItem = (LinearLayout) activity.findViewById(R.id.selectTasteItem);
        tasteItem.setOnClickListener(taste);

        LinearLayout colorItem = (LinearLayout) activity.findViewById(R.id.selectColorItem);
        colorItem.setOnClickListener(color);

        LinearLayout phItem = (LinearLayout) activity.findViewById(R.id.selectpHItem);
        phItem.setOnClickListener(ph);

        LinearLayout leadItem = (LinearLayout) activity.findViewById(R.id.selectLeadItem);
        leadItem.setOnClickListener(lead);

        LinearLayout zipcodeItem = (LinearLayout) activity.findViewById(R.id.selectZipcodeItem);
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
