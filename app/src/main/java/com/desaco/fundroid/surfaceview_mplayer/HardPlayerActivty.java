package com.desaco.fundroid.surfaceview_mplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.desaco.fundroid.R;
import com.desaco.fundroid.utils.TimeUtils;


/**
 * 硬解视频和解码4K视频：使用原生MediaPlayer
 * 4K视频的播放
 *
 * @dengwen
 *
 *
 */
public class HardPlayerActivty extends Activity {

    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private String mVideoUrl; // 正片
    private String mAdVideoUrl; // 广告
    private ImageView mPlaceIv;
    private TextView mTotalVideoTimeTv;
    private TextView mCurrentVideoTimeTv;
    private ProgressBar mLoadingPbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_hard_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();
//        if (AndPermission.hasPermissions(this, Permission.Group.STORAGE)) {
//            initData();
//        } else {
//            AndPermission.with(this)
//                    .runtime()
//                    .permission(Permission.Group.STORAGE)
//                    .onGranted(permissions -> { // allow
//                        initData();
//                    })
//                    .onDenied(permissions -> {
//                        // Storage permission are not allowed.
//
//                    })
//                    .start();
//        }
        initData();
    }

    private SeekBar mVideoSeekBar;

    private void initView() {
        /*
            向player中设置dispay，也就是SurfaceHolder。
            但此时有可能SurfaceView还没有创建成功，所以需要监听SurfaceView的创建事件
         */
        mSurfaceView = (SurfaceView) this.findViewById(R.id.video_surface);
        mPlaceIv = (ImageView) this.findViewById(R.id.place_img);
        mVideoSeekBar = (SeekBar) this.findViewById(R.id.video_seekbar);
        mVideoSeekBar.setMax(1000);

        mTotalVideoTimeTv = (TextView) findViewById(R.id.total_video_time_tv);
        mCurrentVideoTimeTv = (TextView) findViewById(R.id.current_video_time_tv);
//
        mLoadingPbar = (ProgressBar) findViewById(R.id.app_video_loading);
        mLoadingPbar.setVisibility(View.VISIBLE);
    }


    private void initData() {
        // 开始实例化MediaPlayer对象
        mMediaPlayer = new MediaPlayer();
        // 设置数据数据源，也就播放文件地址，可以是网络地址
        // https://cmdctv.oss-cn-hangzhou.aliyuncs.com/%E8%A7%86%E9%A2%91%E5%89%8D%E8%B4%B4%E7%89%87.mp4
        // https://test-1258759011.cos.ap-guangzhou.myqcloud.com/前贴片/1566009434356_682_ad201901.mp4

        // https://test-1258759011.cos.ap-guangzhou.myqcloud.com/launcher/4kdemovideo/HEVC_bodyPainting_4K_tag30fps_20M.ts // 4K视频
//        mAdVideoUrl = "https://test-1258759011.cos.ap-guangzhou.myqcloud.com/launcher/4kdemovideo/HEVC_bodyPainting_4K_tag30fps_20M.ts";
        // https://cmdctv.oss-cn-hangzhou.aliyuncs.com/%E6%AD%A3%E7%89%87.mp4
//        mVideoUrl = "https://cmdctv.oss-cn-hangzhou.aliyuncs.com/%E6%AD%A3%E7%89%87.mp4";
        // http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
        mVideoUrl= "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

        // /storage/emulated/0video_test/video_4k.ts

//        Log.e("desaoc", "mVideoUrl="+mVideoUrl);
//        File videoFile = new File(Environment.getExternalStorageDirectory().toString()
//                + File.separator + "/video_test/video_4k.ts");
//        Uri videoUri = null;
//        if (videoFile.exists() && videoFile.isFile()) {
//            videoUri = Uri.fromFile(videoFile);
//        }

        try {
//            mMediaPlayer.setDataSource(this, videoUri); // 本地路径
            mMediaPlayer.setDataSource(mVideoUrl); // 网络路径
            // 异步缓冲当前视频文件，也有一个同步接口
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 将播放器和SurfaceView关联起来
                mMediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        // 只有当播放器准备好了之后才能够播放，所以播放的出发只能在触发了prepare之后
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
                long pos = 1000L * mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();
                mVideoSeekBar.setProgress((int) pos);

                long curCommonTime = mVideoSeekBar.getProgress() * mMediaPlayer.getDuration();
                mMediaPlayer.seekTo((int) curCommonTime / 1000);

                mCurrentVideoTimeTv.setText(TimeUtils.generateTime(mMediaPlayer.getCurrentPosition()));
                mTotalVideoTimeTv.setText(" / " + TimeUtils.generateTime(mMediaPlayer.getDuration()));

                mCommonVideoHandler.postDelayed(mCommonVideoRunnable, 1000);

                mLoadingPbar.setVisibility(View.GONE);
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(HardPlayerActivty.this, "视频播放完成！", Toast.LENGTH_SHORT).show();
//                mPlaceIv.setVisibility(View.VISIBLE);
//                playNextVideo(mVideoUrl);
            }
        });
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int i1) {
//                Toast.makeText(HardPlayerActivty.this, "视频OnInfo what="+what +",,i1="+i1, Toast.LENGTH_SHORT).show();
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLoadingPbar.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mLoadingPbar.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(HardPlayerActivty.this, "视频OnError 出错了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // mVideoSeekBar
        mVideoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static final int DRAG_SEEKBAR_MSG = 100;
    Handler mCommonVideoHandler = new Handler() {//实时更新进度条的handler
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DRAG_SEEKBAR_MSG:
                    if (mMediaPlayer.getDuration() == 0) {
                        mVideoSeekBar.setProgress((int) (0));
                    } else {
                        mVideoSeekBar
                                .setProgress((int) (mMediaPlayer.getCurrentPosition() * 1000 / mMediaPlayer.getDuration()));
                    }
                    mCurrentVideoTimeTv.setText(TimeUtils.generateTime(mMediaPlayer.getCurrentPosition()));
                    break;
                default:
                    break;
            }

        }

    };

    Runnable mCommonVideoRunnable = new Runnable() {
        @Override
        public void run() {
            mCommonVideoHandler.sendEmptyMessage(DRAG_SEEKBAR_MSG);
            try {
                mCommonVideoHandler.postDelayed(this, 1000);
            } catch (Exception e) {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void playNextVideo(String videoUrl) {
        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(videoUrl);
            // 异步缓冲当前视频文件，也有一个同步接口
            mMediaPlayer.prepareAsync();
        } catch (Exception ex) {

        }

    }
}
