package watr.hack.watr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class SliderDialog extends DialogFragment {

    private SliderListener mListener;
    private DiscreteSeekBar seekbar;
    private TextView label;

    public interface SliderListener {
        public void onSliderComplete(int value);
    }

    public void addListener(SliderListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        int max = getArguments().getInt("max");
        int unit = getArguments().getInt("unit");
        String units = getArguments().getString("units");
        View layout = inflater.inflate(R.layout.slider_dialog, null);
        seekbar = (DiscreteSeekBar) layout.findViewById(R.id.seekbar);
        seekbar.setMax(max);
        label = (TextView) layout.findViewById(R.id.seekbar_label);
        label.setText(getArguments().getString("label") + units);

        builder.setView(layout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onSliderComplete(seekbar.getProgress());
            }
        });
        return builder.create();
    }
}
