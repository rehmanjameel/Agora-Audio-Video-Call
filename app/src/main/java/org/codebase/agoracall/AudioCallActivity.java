package org.codebase.agoracall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;

public class AudioCallActivity extends AppCompatActivity {

    // Fill the App ID of your project generated on Agora Console.
    private final String appId = "dc8407be7e7d4421a9d1421ff193191a";
    // Fill the channel name.
    private String channelName = "AgoraCodeBase";
    // Fill the temp token generated on Agora Console.
    private String token = "007eJxTYGBYtnWn5bqNz6brS1zhkmitu3zZ09/mtr+q5+efod3BG48oMKQkW5gYmCelmqeap5iYGBkmWqYYAqm0NENLY0NLw8TIVTEpDYGMDN8bfJkZGSAQxOdlcEzPL0p0zk9JdUosTmVgAACZSSL9";
    // An integer that identifies the local user.
    private int uid = 0;
    private boolean isJoined = false;

    // Agora engine instance
    private RtcEngine agoraEngine;
    // UI elements
    private TextView infoText;
    private Button joinLeaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_call);

        setupVoiceSDKEngine();

        // Set up access to the UI elements
        joinLeaveButton = findViewById(R.id.joinLeaveButton);
        infoText = findViewById(R.id.infoText);
    }

    private void setupVoiceSDKEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the remote user joining the channel.
        public void onUserJoined(int uid, int elapsed) {
            runOnUiThread(()->infoText.setText("Remote user joined: " + uid));
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            // Successfully joined a channel
            isJoined = true;
            showMessage("Joined Channel " + channel);
            runOnUiThread(()->infoText.setText("Waiting for a remote user to join"));
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            // Listen for remote users leaving the channel
            showMessage("Remote user offline " + uid + " " + reason);
            if (isJoined) runOnUiThread(()->infoText.setText("Waiting for a remote user to join"));
        }

        @Override
        public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
            // Listen for the local user leaving the channel
            runOnUiThread(()->infoText.setText("Press the button to join a channel"));
            isJoined = false;
        }
    };


    private void joinChannel() {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        // Set both clients as the BROADCASTER.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        // Set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        agoraEngine.joinChannel(token, channelName, uid, options);
    }

    public void joinLeaveChannel(View view) {
        if (isJoined) {
            agoraEngine.leaveChannel();
            joinLeaveButton.setText("Join");
        } else {
            joinChannel();
            joinLeaveButton.setText("Leave");
        }
    }

    void showMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }

    protected void onDestroy() {
        super.onDestroy();
        agoraEngine.leaveChannel();

        // Destroy the engine in a sub-thread to avoid congestion
        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }

}