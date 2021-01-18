package com.example.eyezo.hockeyapp;

import android.app.Activity;
import android.app.Application;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UtilityHelper extends Application {

            public static Toast customToast(Activity context, String message)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) context.findViewById(R.id.toast_layout));
            TextView tvToast = (TextView) layout.findViewById(R.id.tv_toast);
            ImageView toastImage = (ImageView) layout.findViewById(R.id.image);
            tvToast.setText(message) ;
            toastImage.setImageResource(R.mipmap.logo_2);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0,0);
            toast.setView(layout);
            toast.show();
            return toast;
        }


}
