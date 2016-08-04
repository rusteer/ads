package com.rot.jms;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsListener implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger("JmsLogger");
    private void handleAccessDate(MapMessage message) {}
    private void handlebizStat(MapMessage message) {}
    @Override
    public void onMessage(Message msg) {
        try {
            MapMessage message = (MapMessage) msg;
            switch (message.getIntProperty("bizType")) {
                case 0://SAVE_ACCESS_DATE
                    handleAccessDate(message);
                    break;
                case 1:
                    handlebizStat(message);
                    break;
            }
        } catch (Exception e) {
            logger.error("处理消息时发生异常.", e);
        }
    }
}
