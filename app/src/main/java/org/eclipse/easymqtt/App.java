package org.eclipse.easymqtt;

import android.app.Application;

import com.kathline.mqttlibs.MqttManager;

/**
 * Created by fighter_lee on 2017/10/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MqttManager.getInstance().setContext(getApplicationContext());
    }
}
