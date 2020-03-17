package top.fighter_lee.easymqtt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import top.fighter_lee.easymqtt.engine.MqttEngine;
import top.fighter_lee.mqttlibs.connect.MqttManager;
import top.fighter_lee.mqttlibs.mqttv3.IMqttDeliveryToken;
import top.fighter_lee.mqttlibs.mqttv3.MqttCallback;
import top.fighter_lee.mqttlibs.mqttv3.MqttException;
import top.fighter_lee.mqttlibs.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MqttManager.getInstance().registerMessageListener(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, String.valueOf(cause));
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG, "messageArrived() topic:"+topic);
                Log.d(TAG, "messageArrived() message:"+message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void click_connect(View view) {
        try {
            MqttEngine.getInstance().connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void click_disconnect(View view) {
        try {
            MqttEngine.getInstance().disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void click_pub(View view) {
        try {
            MqttEngine.getInstance().pub();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void click_sub(View view) throws MqttException {
        MqttEngine.getInstance().sub();
    }

    public void click_unsub(View view) {
        try {
            MqttEngine.getInstance().unsub();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
