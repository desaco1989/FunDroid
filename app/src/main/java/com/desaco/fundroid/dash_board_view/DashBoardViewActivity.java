package com.desaco.fundroid.dash_board_view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.desaco.fundroid.R;

/**
 * @author dengwen
 * @date 2019/12/24.
 */
public class DashBoardViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 网络测速 仪表盘
//        mDashBoardView = view.findViewById(R.id.test_speed_view);
//        mDashBoardView.setCompleteDegree(32.25f);
//        handler.post(randomRun);
    }

    private static final int MSG_SPEED = 100;
    private int count = 0;

    Runnable randomRun = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(MSG_SPEED);
            handler.postDelayed(this, 500);
        }
    };


    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SPEED:
                    int num = (int) (100 * Math.random());//随机生成0-100的数
//                    mDashBoardView.setCompleteDegree(num);
                    if (count == 1000) {
                        handler.removeCallbacks(randomRun);
                        handler.removeMessages(MSG_SPEED);
                        return;
                    }
                    count ++;
                    break;
                default:
                    break;
            }
        }
    };
}
