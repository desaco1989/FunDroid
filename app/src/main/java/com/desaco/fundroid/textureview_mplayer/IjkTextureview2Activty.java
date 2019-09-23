package com.desaco.fundroid.textureview_mplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.desaco.fundroid.R;

import java.io.IOException;

/**
 * 使用ijkplayer中的TextureView
 * 硬解视频
 *
 */
public class IjkTextureview2Activty extends Activity {

    //    private String mVideoUrl = "http://www.w3school.com.cn/example/html5/mov_bbb.mp4";
    private String mVideoUrl = "https://test-1258759011.cos.ap-guangzhou.myqcloud.com/前贴片/1566009434356_682_ad201901.mp4";
    // https://test-1258759011.cos.ap-guangzhou.myqcloud.com/前贴片/1566009434356_682_ad201901.mp4
    private TextureView mTextureView;
    private TextureRenderView renderView;
    // pbar ProgressBar
    private ProgressBar mPBar;
    private FrameLayout mFlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.ijk_textureview_activity);

//        mTextureView = (TextureView) findViewById(R.id.textureView);
        mPBar = (ProgressBar) findViewById(R.id.pbar);
        mPBar.setVisibility(View.VISIBLE);
        mFlayout = (FrameLayout)findViewById(R.id.root_layout);

        initMediaPlayer();
        initTextureView();
        mTextureView.setSurfaceTextureListener(surfaceTextureListener);
        play();
    }

    private void addTextureView() {
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        mTextureView = new TextureView(this);
//        mFlayout.addView(mTextureView, params);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        renderView = new TextureRenderView(this);
        mFlayout.addView(renderView, params);

//         renderView = new TextureRenderView(this);
//        if (mMediaPlayer != null) {
//            renderView.getSurfaceHolder().bindToMediaPlayer(mMediaPlayer);
//            renderView.setVideoSize(mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
//            renderView.setVideoSampleAspectRatio(mMediaPlayer.getVideoSarNum(), mMediaPlayer.getVideoSarDen());
//            renderView.setAspectRatio(mCurrentAspectRatio);
//        }
//        setRenderView(renderView);
    }

    private void play() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            return;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this, Uri.parse(mVideoUrl));
            mMediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTextureView() {
        addTextureView();
        mTextureView.setAlpha(0.8f);
        mTextureView.setScaleX(1);
        mTextureView.setScaleY(0.8f);
    }

    private void initMediaPlayer() {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int mVolumn = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPBar.setVisibility(View.GONE);
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    Toast.makeText(this,"播放完成！继续从头播放", Toast.LENGTH_SHORT).show();
//                    restart();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {

                }
            });
            mMediaPlayer.setVolume(mVolumn, mVolumn);

        } else {
            mMediaPlayer.reset();
        }
    }


    private MediaPlayer mMediaPlayer;
    private SurfaceTexture surfaceTexture;

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if (mMediaPlayer == null) {
                return;
            }
            mMediaPlayer.setSurface(new Surface(surface));
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

}
