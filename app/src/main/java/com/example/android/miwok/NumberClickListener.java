package com.example.android.miwok;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

/**
 * Created by asus on 07-Oct-17.
 */

public class NumberClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Open the number activity", Toast.LENGTH_SHORT).show();
    }
}