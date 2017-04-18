package com.wenzhiguo.video;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private VrVideoView mVideo;
    private SeekBar mSeekBar;
    private TextView mTextView;
    private VideoTask mVideoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //切换VR的模式(一定放在主线程中要不会爆
        // (Can't create handler inside thread that has not called Looper.prepare(意思是无法创建处理程序)))
        // 参数: VrWidgetView.DisplayMode.FULLSCREEN_STEREO设备模式(手机横着放试试)
        //   VrWidgetView.DisplayMode.FULLSCREEN_MONO手机模式
        mVideo.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_MONO);
        //开启异步进行耗时操作
        mVideoTask = new VideoTask();
        mVideoTask.execute("videotest.mp4");
        //监听事件
        mVideo.setEventListener(new myVideoListener());
    }

    class VideoTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //隐藏掉VR效果左下角的信息按钮显示
            mVideo.setInfoButtonEnabled(false);
            //隐藏掉VR效果右下角全屏显示按钮
            mVideo.setFullscreenButtonEnabled(false);
            //设置视频加载的第二个参数对象
            VrVideoView.Options option = new VrVideoView.Options();
            //设置为默认的(还有一种类型是FORMAT_HLS(流媒体数据格式))
            option.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            //立体模式
            option.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
            try {
                //加载视频
                mVideo.loadVideoFromAsset(strings[0], option);
                //mVideo.loadVideo(Uri.parse(strings[0]),option);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void initView() {
        mVideo = (VrVideoView) findViewById(R.id.video);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mTextView = (TextView) findViewById(R.id.textview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停渲染和显示
        mVideo.pauseRendering();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //继续渲染和显示
        mVideo.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭渲染和显示
        mVideo.shutdown();
        if (mVideoTask!=null){
            if(!mVideoTask.isCancelled()){
                mVideoTask.cancel(true);
            }
        }
    }
    class myVideoListener extends VrVideoEventListener{
        boolean isPlay = true;
        //监听
        @Override
        public void onClick() {
            super.onClick();
            if (isPlay){
                mVideo.pauseVideo();
            }else {
                mVideo.playVideo();
            }
            isPlay = !isPlay;
            Log.d("zzz","---------------"+isPlay);
        }
        //完成时设置当前seekbar进度为0
        @Override
        public void onCompletion() {
            super.onCompletion();
            mVideo.seekTo(0);
            mSeekBar.setProgress(0);
            isPlay = true;
        }

        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
            Toast.makeText(MainActivity.this, "加载错误", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            int duration = (int) mVideo.getDuration();
            mSeekBar.setMax(duration);
        }
        //获取第一帧,动画一直播放此方法一只调用
        @Override
        public void onNewFrame() {
            super.onNewFrame();
            int currentPosition = (int) mVideo.getCurrentPosition();
            mSeekBar.setProgress(currentPosition);
            mTextView.setText("当前进度"+String.format("%.2f",currentPosition/1000.f));
        }
    }
}
