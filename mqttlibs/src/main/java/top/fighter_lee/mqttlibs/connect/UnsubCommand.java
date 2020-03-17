package top.fighter_lee.mqttlibs.connect;


import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by fighter_lee on 2017/7/2.
 */

public class UnsubCommand implements Command{
    private String mTopic;

    public UnsubCommand setTopic(String topic) {
        this.mTopic = topic;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        MqttManager.getInstance().getConnect().getClient()
                .unsubscribe(mTopic,null,listener);
    }
}
