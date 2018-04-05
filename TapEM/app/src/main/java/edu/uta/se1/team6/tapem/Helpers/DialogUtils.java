package edu.uta.se1.team6.tapem.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.R;

/**
 * Created by yashodhan on 3/24/18.
 */

public class DialogUtils {

    public static Dialog AlertDialog(final Activity activity,
                                     String alertTitle, final CallBack callBack) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);

        Button closeBtn = (Button) dialog .findViewById(R.id.cancelAction);
        Button okBtn = (Button) dialog.findViewById(R.id.confirmAction);

        ((TextView) dialog.findViewById(R.id.prompt)).setText(alertTitle);
//        ((TextView) dialog.findViewById(R.id.msgTv)).setText(alertMsg);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.onCallBack(null, null);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

}
