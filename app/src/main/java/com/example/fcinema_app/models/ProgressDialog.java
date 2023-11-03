package com.example.fcinema_app.models;

import android.app.Dialog;

public class ProgressDialog {
    private Dialog mDialog;

    public ProgressDialog(Dialog dialog) {
        mDialog = dialog;
    }
    public void DialogShowing(){
        mDialog.show();
    }
    public void DialogDismiss(){
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
