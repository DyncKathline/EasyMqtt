package com.kathline.mqttlibs;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.android.service.MqttTraceHandler;


/**
 * 使用方法：
 *  请见README.md
 */

public class MqttManager {

    private static MqttManager instance = null;


    private static final String TAG = "ConnectManager";
    public static Context sCx;
    private ConnectCommand mConnect;
    private MqttCallback messageListener;
    private MqttTraceHandler mTraceListener;
    private boolean mTraceEnable;

    private MqttManager() {

    }

    public synchronized static MqttManager getInstance() {
        if (instance == null) {
            synchronized (MqttManager.class) {
                if (instance == null) {
                    instance = new MqttManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        sCx = context;
    }

    public void connect(ConnectCommand connect, IMqttActionListener listener) throws MqttException {
        if (null != messageListener) {
            connect.setMessageListener(messageListener);
        }
        if (null != mTraceListener){
            connect.setTraceCallback(mTraceListener);
        }
        connect.setTraceEnabled(mTraceEnable);
        this.mConnect = connect;
        connect.execute(listener);
    }

    public void registerMessageListener(MqttCallback listener) {
        this.messageListener = listener;
    }

    public void registerTraceListener(MqttTraceHandler traceHandler) {
        this.mTraceListener = traceHandler;
    }

    public void setTraceEnable(boolean enable) {
        mTraceEnable = enable;
        if (null == mConnect){
            return;
        }
        getConnect().setTraceEnabled(enable);
    }

    public void disConnect(DisconnectCommand command,IMqttActionListener listener) throws MqttException {
        command.execute(listener);
    }

    public void publish(PubCommand command, IMqttActionListener listener) throws MqttException {
        command.execute(listener);
    }

    public void subscribe(SubCommand command, IMqttActionListener listener) throws MqttException {
        command.execute(listener);
    }

    public void unSub(UnsubCommand command,IMqttActionListener listener) throws MqttException {
        command.execute(listener);
    }

    public ConnectCommand getConnect() {
        return mConnect;
    }

    public boolean isConneect() {
        if (getConnect() == null){
            return false;
        }
        if (null == getConnect().getClient()){
            return false;
        }
        try {
            return getConnect().getClient().isConnected();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 断连重试机制
     * @param repeatTime
     * @param triggerTime
     */
    public void keepConnect(long repeatTime,long triggerTime) {
        if (null == getConnect()){
            return;
        }
        if (null == getConnect().getClient()){
            return;
        }
        try {
            getConnect().getClient().startKeepConnect(repeatTime, triggerTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否保持连接
     * @return
     */
    public boolean isKeepConnect() {
        if (getConnect() == null){
            return false;
        }
        if (null == getConnect().getClient()){
            return false;
        }
        return getConnect().getClient().isKeepConnect();
    }

    public void stopKeepConnect() {
        if (isKeepConnect()){
            Log.d(TAG, "stopKeepConnect() will stop keep-connect-service");
            getConnect().getClient().stopKeepConnect();

        }
    }
}
