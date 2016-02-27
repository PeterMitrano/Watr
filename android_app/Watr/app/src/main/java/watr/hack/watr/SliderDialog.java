package watr.hack.watr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class SliderDialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener {

    private int min, max, unit;
    private String units;
    private SliderListener mListener;
    private SeekBar seekbar;
    private TextView label;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mListener.onSliderUpdate(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    public interface SliderListener {
        public void onSliderUpdate(int value);
    }

    public SliderDialog() {
        if (getArguments() != null) {
            min = getArguments().getInt("min");
            max = getArguments().getInt("max");
            unit = getArguments().getInt("unit");
            units = getArguments().getString("units");
        }
    }

    public void addListener(SliderListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.slider_dialog, null);
        seekbar = (SeekBar) layout.findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
        label = (TextView) layout.findViewById(R.id.seekbar_label);
        label.setText(getArguments().getString("label"));

        builder.setView(layout);
        return builder.create();
    }
}
