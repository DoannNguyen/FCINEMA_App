package com.example.fcinema_app.models;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fcinema_app.R;

public class ProgressDialog {
    private Dialog mDialog;
    private TextView message;
    private boolean cancelable;

    public ProgressDialog(Dialog dialog, String title) {
        mDialog = dialog;
        this.message = dialog.findViewById(R.id.idDialog);
        this.message.setText(title);
    }
    public void DialogShowing(){
        mDialog.show();
    }
    public void DialogDismiss(){
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        mDialog.setCancelable(cancelable);
    }

}
