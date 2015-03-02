package com.zms.toast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Main extends ActionBarActivity {
    private Context context;
    private Button btnToast;
    private Button btnNotify;
    private Button btnDialog;
    private final static int NOTIFICATION_ID = 0x555;

    /**
     * 通知是否已经显示
     */
    private boolean isShow = false;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnToast = (Button) findViewById(R.id.btnToast);
        btnNotify = (Button) findViewById(R.id.btnNotify);
        btnDialog = (Button) findViewById(R.id.btnDialog);
        btnToast.setOnClickListener(new onClickListenerImp());
        btnNotify.setOnClickListener(new onClickListenerImp());
        btnDialog.setOnClickListener(new onClickListenerImp());
    }

    private class onClickListenerImp implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btnToast) {
                LayoutInflater inflater = LayoutInflater.from(Main.this);
                View myView = inflater.inflate(R.layout.mytoast,
                        (ViewGroup) findViewById(R.id.toast_ll_main));
                TextView toast_tv = (TextView) myView.findViewById(R.id.toast_tv);
                TextView toast_time = (TextView) myView.findViewById(R.id.toast_time);
                SpannableString spannableString = new SpannableString("Hello,Android! -zhoumushui");
                spannableString.setSpan(new ForegroundColorSpan(Color.YELLOW),
                        0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.CYAN),
                        14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                toast_tv.setText(spannableString);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
                toast_time.setText(sdf.format(new Date()));

                Toast myToast = new Toast(Main.this);
                myToast.setDuration(Toast.LENGTH_SHORT);
                myToast.setView(myView);
                myToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0); // 显示位置:底部中间
                myToast.show();
            } else if (v == btnNotify) {
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification(
                        R.drawable.ic_launcher, "Hello,Android!--zhoumushui",
                        System.currentTimeMillis());
                if (!isShow) {
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    Intent intent = new Intent(Main.this, AfterClickNotify.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent contentIntent = PendingIntent
                            .getActivity(Main.this, 0, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setLatestEventInfo(Main.this, "Hello,Android!", "zhoumushui.",
                            contentIntent);
                    //notificationManager.notify(R.string.app_name, notification);
                    notificationManager.notify(NOTIFICATION_ID, notification);
                    isShow = true;
                } else {
                    //notificationManager.cancelAll(); // 取消所有通知
                    notificationManager.cancel(NOTIFICATION_ID); // 根据ID取消通知
                    isShow = false;
                }
            } else if (v == btnDialog) {
                customDialog = new CustomDialog(Main.this, R.layout.custom_dialog,
                        R.style.CustomDialogTheme);
                if (!customDialog.isShowing()) {
                    customDialog.show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
