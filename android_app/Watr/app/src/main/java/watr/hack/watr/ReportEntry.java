package watr.hack.watr;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by peter on 2/27/16.
 */
abstract class ReportEntry implements View.OnClickListener {

    protected final String key;
    public Object value;

    ReportEntry(String key) {
        this.key = key;
    }
}

class ChoosableReportEntry extends ReportEntry implements StringSelectorDialog.StringSelectListener {

    private String items[];
    private StringSelectorDialog dialog;
    private FragmentManager fragmentManager;
    private TextView label;

    ChoosableReportEntry(String key, FragmentManager fragmentManager, String items[],
                         TextView label) {
        super(key);
        this.label = label;
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
        label.setText(items[index]);
    }

    @Override
    public void onClick(View v) {
        dialog.show(fragmentManager, "selector_fragment");
    }
}

class TextReportEntry extends ReportEntry implements ZipcodeDialog.ZipcodeListener {

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

class SliderReportEntry extends ReportEntry implements SliderDialog.SliderListener {

    private SliderDialog dialog;
    private FragmentManager fragmentManager;
    private TextView label;

    SliderReportEntry(String key, FragmentManager fragmentManager,
                      String units, int min, int max, int unit,
                      TextView label) {
        super(key);
        this.fragmentManager = fragmentManager;
        this.label = label;
        Bundle args = new Bundle();
        args.putString("label", key);
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
    public void onSliderComplete(int value) {
        label.setText("" +value);
        this.value = value;
    }
}

