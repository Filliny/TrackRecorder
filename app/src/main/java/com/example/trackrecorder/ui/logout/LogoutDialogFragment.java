package com.example.trackrecorder.ui.logout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.trackrecorder.R;
import com.example.trackrecorder.helpers.OnLogoutConfirm;

public class LogoutDialogFragment extends DialogFragment {

    OnLogoutConfirm callback;
    Boolean logout = false;

    LogoutDialogFragment(OnLogoutConfirm callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(R.string.logout_dialog_message)
                .setPositiveButton(R.string.logout_dialog_ok, (dialog, which) -> {

                    logout = true;

                })
                .setNegativeButton(R.string.logout_dialog_cancel, (dialog, which) -> {

                    logout = false;

                })
                .create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (logout) {
            callback.Confirm();
        } else {
            callback.Cancel();
        }

    }
}
