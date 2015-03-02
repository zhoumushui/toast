package com.zms.toast;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by AlexZhou on 2015/3/2.
 * 14:51
 */
public class CustomDialog extends Dialog {
    private static int defaultWidth = 200;
    private static int defaultHeight = 160;
    private int progress = 0;
    private TextView dialogText;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int layout, int style) {
        this(context, defaultWidth, defaultHeight, layout, style);
    }

    public float getDensity(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.density;
    }

    public CustomDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        // 设置内容
        setContentView(layout);
        // 设置窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置宽度、高度、密度、对齐方式
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        dialogText = (TextView) findViewById(R.id.dialogText);
        new Thread(new TimeThread()).start();
    }

    final Handler timeHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (progress < 100) {
                        progress = progress + new Random().nextInt(10);
                        if (progress <= 100) {
                            dialogText.setText("更新进度" + progress + "%");
                        } else {
                            dialogText.setText("更新完成");
                        }
                    } else {
                        dismiss();
                    }
            }
            super.handleMessage(msg);
        }
    };

    public class TimeThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    timeHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
