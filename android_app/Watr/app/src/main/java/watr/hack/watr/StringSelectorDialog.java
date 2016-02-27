package watr.hack.watr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class StringSelectorDialog extends DialogFragment {

    private QualitySelectListener mListener;
    private String items[];

    public interface QualitySelectListener {
        public void onItemClick(int index);
    }

    public StringSelectorDialog() {
        items = getArguments().getStringArray("items");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QualitySelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement the Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_quality)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {
                        mListener.onItemClick(index);
                    }
                });
        return builder.create();
    }
}
