package org.codebase.agoracall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

//import io.agora.agorauikit_android.AgoraConnectionData;
//import io.agora.agorauikit_android.AgoraVideoViewer;

public class AgoraUikit extends AppCompatActivity {

    // Fill the App ID of your project generated on Agora Console.
    private final String appId = "dc8407be7e7d4421a9d1421ff193191a";
    // Fill the channel name.
    private String channelName = "AgoraCodeBase";
    // Fill the temp token generated on Agora Console.
    private String token = "007eJxTYGBYtnWn5bqNz6brS1zhkmitu3zZ09/mtr+q5+efod3BG48oMKQkW5gYmCelmqeap5iYGBkmWqYYAqm0NENLY0NLw8TIVTEpDYGMDN8bfJkZGSAQxOdlcEzPL0p0zk9JdUosTmVgAACZSSL9";
    // An integer that identifies the local user.
    private int uid = 0;
    private boolean isJoined = false;

//    AgoraVideoViewer agoraVideoViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_uikit);

//        try {
//            agoraVideoViewer = new AgoraVideoViewer(this,
//                    new AgoraConnectionData("", ""));
//            this.addContentView(agoraVideoViewer, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                    FrameLayout.LayoutParams.MATCH_PARENT));
//            agoraVideoViewer.join(channelName, "", Constants.CLIENT_ROLE_BROADCASTER);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}