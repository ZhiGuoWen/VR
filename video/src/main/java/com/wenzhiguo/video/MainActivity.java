package com.wenzhiguo.video;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.vr.sdk.widgets.common.VrWidgetView;
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
        mVideoTask.execute("congo_2048.mp4");
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
                //mVideo.loadVideoFromAsset(strings[0], option);
                mVideo.loadVideo(Uri.parse(strings[0]),option);
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
}
