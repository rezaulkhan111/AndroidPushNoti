package com.example.entern.androidpushnoti;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
   final static String BACKEND_ACTION_ECHO="";
   Random RANDOM=new Random();
   final static String FCM_PROJECT_SENDER_ID="401366049255";
   final static String FCM_SERVER_CONNECTION="AAAAXXNH4ec:APA91bH4Hi_h3y7B4eWajRoT61sjT78gcx8iLLOFvNnRdipXtW5l8rK-JmdbuENx_t2UrxeiCa-jlnToXxpBMktEEf8gGyJaI-tMpumwNyVNxKbWXMgtpXees-kFDQxbqknDidaXj1u9";

    TextView deviceText;
    EditText editTextEcho;
    Button buttonUpstreamEcho;

    MediaSessionCompat.Token tokenService;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceText = (TextView) findViewById(R.id.tv_device);
        editTextEcho = (EditText) findViewById(R.id.et_echo);
        buttonUpstreamEcho = (Button) findViewById(R.id.bt_up_stream_echo);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
        deviceText.setText(token);

        buttonUpstreamEcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Echo Upstream message logic");
                String message = editTextEcho.getText().toString();
                Log.d(TAG, "Message: " + message + ", recipient: " + token);
                FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(FCM_PROJECT_SENDER_ID + FCM_SERVER_CONNECTION)
                        .setMessageId(Integer.toString(RANDOM.nextInt()))
                        .addData("message", message)
                        .addData("action", BACKEND_ACTION_ECHO)
                        .build());
            }
        });
    }
}
