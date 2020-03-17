package org.eclipse.easymqtt.engine;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.kathline.mqttlibs.ConnectCommand;
import com.kathline.mqttlibs.DisconnectCommand;
import com.kathline.mqttlibs.MqttManager;
import com.kathline.mqttlibs.PubCommand;
import com.kathline.mqttlibs.SubCommand;
import com.kathline.mqttlibs.UnsubCommand;

import java.util.UUID;

/**
 * Created by fighter_lee on 2017/10/17.
 */

public class MqttEngine {

    private static final String TAG = "MqttEngine";
    private static MqttEngine mqttEngine;

    private MqttEngine() {
    }

    public static MqttEngine getInstance() {
        if (null == mqttEngine)
            mqttEngine = new MqttEngine();
        return mqttEngine;
    }

    public void connect() throws MqttException {
        MqttManager.getInstance()
                .connect(new ConnectCommand()
                                .setClientId(getClientId())
                                .setServer("192.168.43.190")
                                .setPort(1883)
//                                .setUserNameAndPassword("admin", "password")
                                .setKeepAlive(30)
                                .setTimeout(10)
                                .setCleanSession(false)
                        , new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d(TAG, "onSuccess() ");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.d(TAG, "onFailure() ");
                                Log.e(TAG, String.valueOf(exception));
                            }
                        });
    }

    public void disConnect() throws MqttException {
        MqttManager.getInstance().disConnect(new DisconnectCommand()
                .setQuiesceTimeout(10), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d(TAG, "disConnect onSuccess() ");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(TAG, "disConnect onFailure() " + String.valueOf(exception));
            }
        });
    }

    public void publish() throws MqttException {
        MqttManager.getInstance().publish(new PubCommand()
                .setMessage("哈哈哈，我来了")
                .setQos(2)
                .setTopic("test")
                .setRetained(false), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d(TAG, "publish onSuccess() ");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(TAG, "publish onFailure() " + String.valueOf(exception));
            }
        });
    }

    public void subscribe() throws MqttException {
        MqttManager.getInstance().subscribe(new SubCommand()
                .setQos(2)
                .setTopic("test"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d(TAG, "subscribe onSuccess() ");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(TAG, "subscribe onFailure() " + String.valueOf(exception));
            }
        });
    }

    public void unSubscribe() throws MqttException {
        MqttManager.getInstance().unSub(new UnsubCommand()
                .setTopic("test"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d(TAG, "unSubscribe onSuccess() ");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(TAG, "unSubscribe onFailure() " + String.valueOf(exception));
            }
        });
    }

    private String getClientId() {
        Log.d(TAG, "getClientId() " + Build.SERIAL);
        String clientId = Build.SERIAL;
        if (TextUtils.isEmpty(clientId)) {
            clientId = UUID.randomUUID().toString();
        }
        return clientId;
    }
}
