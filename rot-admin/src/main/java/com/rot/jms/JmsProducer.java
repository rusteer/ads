package com.rot.jms;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsProducer {
    private JmsTemplate jmsTemplate;
    private Destination notifyQueue;
    private Destination notifyTopic;
    public void saveAccessDate(final long clientId) {
        jmsTemplate.send(notifyQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setLong("clientId", clientId);
                message.setIntProperty("bizType", BizType.SAVE_ACCESS_DATE.ordinal());
                return message;
            }
        });
    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    public void setNotifyQueue(Destination notifyQueue) {
        this.notifyQueue = notifyQueue;
    }
    public void setNotifyTopic(Destination nodifyTopic) {
        notifyTopic = nodifyTopic;
    }
}
