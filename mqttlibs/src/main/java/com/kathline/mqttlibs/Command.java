package com.kathline.mqttlibs;


import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by fighter_lee on 2017/6/30.
 */

public interface Command {

    void execute(IMqttActionListener listener) throws MqttException;

}
