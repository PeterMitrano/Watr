package watr.hack.watr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class QualitySelectorDialog extends DialogFragment {

    private QualitySelectListener mListener;

    public interface QualitySelectListener {
        public void onItemClick(int quality);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QualitySelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] qualities = {"Red", "Blue", "Yellow", "Teal", "Clear"};
        builder.setTitle(R.string.choose_quality)
                .setItems(qualities, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int quality) {
                        mListener.onItemClick(quality);
                    }
        });
        return builder.create();
    }
}