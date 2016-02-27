package watr.hack.watr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SliderDialog extends DialogFragment {

    private int min, max, unit;
    private String units;
    private SliderListener mListener;

    public interface SliderListener {
        public void onItemClick(int index);
    }

    public SliderDialog() {
        min = getArguments().getInt("min");
        max = getArguments().getInt("max");
        unit = getArguments().getInt("unit");
        units = getArguments().getString("units");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SliderListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement the Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Title");
        return builder.create();
    }
}
