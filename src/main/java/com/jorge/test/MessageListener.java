package com.jorge.test;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageListener implements javax.jms.MessageListener {
    
    private static final Log log = LogFactory.getLog(MessageListener.class);

    public void onMessage(Message message) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append("received message");
        if (message != null) {
            builder.append(" of type ");
            builder.append(message.getClass().getName());
            try {
                builder.append(" from ");
                builder.append(message.getJMSDestination());
                builder.append("\nJMSCorrelationID=");
                builder.append(message.getJMSCorrelationID());
                builder.append("\nJMSDeliveryMode=");
                builder.append(message.getJMSDeliveryMode());
                builder.append("\nJMSDestination=");
                builder.append(message.getJMSDestination());
                builder.append("\nJMSExpiration=");
                builder.append(message.getJMSExpiration());
                builder.append("\nJMSMessageID=");
                builder.append(message.getJMSMessageID());
                builder.append("\nJMSPriority=");
                builder.append(message.getJMSPriority());
                builder.append("\nJMSRedelivered=");
                builder.append(message.getJMSRedelivered());
                builder.append("\nJMSReplyTo=");
                builder.append(message.getJMSReplyTo());
                builder.append("\nJMSTimestamp=");
                builder.append(message.getJMSTimestamp());
                builder.append("\nJMSType=");
                builder.append(message.getJMSType());
                
                final Enumeration<?> e = message.getPropertyNames();
                if (e != null) {
                    while (e.hasMoreElements()) {
                        final Object s = e.nextElement();
                        final Object v = message.getStringProperty((String) s);
                        builder.append("\n");
                        builder.append(s);
                        builder.append("=");
                        builder.append(v);
                    }
                }
                builder.append("\n");
                builder.append("{");
                if (message instanceof TextMessage) {
                    final TextMessage textMessage = (TextMessage) message;
                    builder.append(textMessage.getText());
                }
                builder.append("}");
            } catch (JMSException e) {
                if (log.isErrorEnabled()) {
                    log.error("error printing received JMS message", e);
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info(builder.toString());
        }
    }
    
}
