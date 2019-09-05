//package com.cmdc.sd.tvlauncher;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.SurfaceTexture;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.Surface;
//import android.view.TextureView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//
//import com.example.test_webview_demo.R;
//
//import java.io.IOException;
//
///**
// * com.cmdc.sd.tvlauncher.TestActivty
// */
//public class TestActivty extends Activity {
//
//    private String mVideoUrl = "";
//    private TextureView mTextureView;
//
//    private SurfaceTexture mSurfaceTexture;
//    private Surface mSurface;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //
//        setContentView(R.layout.test_activity);
//
//        mTextureView = (TextureView)findViewById(R.id.textureView);
//
//        initMediaPlayer();
////        initTextureView();
//        mTextureView.setSurfaceTextureListener(surfaceTextureListener);
////        play();
//    }
//
//    private void play() {
//        if (mMediaPlayer == null){
//            return;
//        }
//        if (mMediaPlayer.isPlaying()){
//            return;
//        }
//        try {
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(this, Uri.parse(mVideoUrl));
//            mMediaPlayer.prepareAsync();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    private void initTextureView() {
////        mTextureView.setAlpha(0.8f);
////        mTextureView.setScaleX(1);
////        mTextureView.setScaleY(0.8f);
////    }
//
//    private void initMediaPlayer() {
//        AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        int mVolumn = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        if (mMediaPlayer == null) {
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mMediaPlayer.start();
//                }
//            });
//            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
////                    Toast.makeText(this,"播放完成！继续从头播放", Toast.LENGTH_SHORT).show();
////                    restart();
//                }
//            });
//            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    return false;
//                }
//            });
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
//                @Override
//                public void onSeekComplete(MediaPlayer mp) {
//
//                }
//            });
//            mMediaPlayer.setVolume(mVolumn, mVolumn);
//
//        } else {
//            mMediaPlayer.reset();
//        }
//    }
//
//
//    private MediaPlayer mMediaPlayer;
////    private SurfaceTexture surfaceTexture;
//
//    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
//
//        @Override
//        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//            if (mMediaPlayer == null){
//                return;
//            }
//            // mTextureView  mMediaPlayer.setSurface(new Surface(surface));
////            mMediaPlayer.setSurface(surface.);
////            mMediaPlayer.setSurface(new Surface(surface));
//
//            if (mSurfaceTexture == null) {
//                mSurfaceTexture = surface;
//                try {
//                    mMediaPlayer.setDataSource(mVideoUrl);
//                    if (mSurface == null) {
//                        mSurface = new Surface(mSurfaceTexture);
//                    }
//                    mMediaPlayer.setSurface(mSurface);
//                    mMediaPlayer.prepareAsync();
//                } catch (Exception ex) {
//
//                }
//
//
//            } else {
//                mTextureView.setSurfaceTexture(surface);
//            }
//
//        }
//
//        @Override
//        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//        }
//
//        @Override
//        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//            if (mMediaPlayer != null){
//                mMediaPlayer.release();
//                mMediaPlayer = null;
//            }
////            mSurfaceTexture = null;
//            return false;
//        }
//
//        @Override
//        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//        }
//    };
//
//}
