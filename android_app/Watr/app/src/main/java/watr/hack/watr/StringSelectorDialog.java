package watr.hack.watr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class StringSelectorDialog extends DialogFragment {

    private StringSelectListener mListener;

    public interface StringSelectListener {
        void onItemClick(int index);
    }

    public void addListener(StringSelectListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_quality)
                .setItems(getArguments().getStringArray("items"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {
                        mListener.onItemClick(index);
                    }
                });
        return builder.create();
    }
}
