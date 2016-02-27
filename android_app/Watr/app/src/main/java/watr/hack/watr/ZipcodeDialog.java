package watr.hack.watr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ZipcodeDialog extends DialogFragment {

    private ZipcodeListener mListener;
    private EditText edit;

    public interface ZipcodeListener {
        public void onTextSubmit(String value);
    }

    public void addListener(ZipcodeListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.zipcode_dialog, null);
        edit = (EditText) layout.findViewById(R.id.zipcode_edit);

        builder.setView(layout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onTextSubmit(edit.getText().toString());
            }
        });
        return builder.create();
    }
}
