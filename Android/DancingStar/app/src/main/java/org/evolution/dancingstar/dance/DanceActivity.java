package org.evolution.dancingstar.dance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.evolution.dancingstar.R;
import org.evolution.dancingstar.dance.Preview;

public class DanceActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private TextureView mTextureView;
    private Preview mPreview;

    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance);

        mVideoView = findViewById(R.id.dance_video_view);
        mTextureView = findViewById(R.id.dance_texture_view);
        mPreview = new Preview(this, mTextureView);

        playVideo();
        TextView tvUser = findViewById(R.id.dance_tv_user);
        tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreview.takePicture();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i=0;i<permissions.length;i++){
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED){
                            mTextureView = findViewById(R.id.learn_texture_view);
                            mPreview = new Preview(this, mTextureView);
                        } else {
                            Toast.makeText(this, "Should have camera permission to run", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }

    public void playVideo() {
        String path = "android.resource://org.evolution.dancingstar/"+R.raw.dance_example;

        Uri uri = Uri.parse(path);

        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }

    protected void takePicture() {

    }
}
