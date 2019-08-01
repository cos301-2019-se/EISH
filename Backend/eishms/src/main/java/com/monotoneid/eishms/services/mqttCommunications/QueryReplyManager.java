package com.monotoneid.eishms.services.mqttcommunications;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

public class QueryReplyManager {
    List<QueryReply> pendingQueryReply;
    List<QueryReply> completeQueryReply;

    public QueryReplyManager() {
        pendingQueryReply = new ArrayList<QueryReply>();
        completeQueryReply = new ArrayList<QueryReply>();
    }

    public void register(String publishTopic, String subscribeTopic) {
        pendingQueryReply.add(new QueryReply(publishTopic, subscribeTopic));
    }

    public void setReply(String subscribeTopic, String message) {
        QueryReply currentQueryReply = null;
        for (int i=0; i < pendingQueryReply.size(); i++) {
            if (pendingQueryReply.get(i).getReplyTopic().matches(subscribeTopic)) {
                pendingQueryReply.get(i).setReplyMessage(subscribeTopic, message);
                currentQueryReply = pendingQueryReply.remove(i);
                completeQueryReply.add(currentQueryReply);
                return;
            }
        }
    }

    public boolean replyExists(String publishTopic) {
        for (int i=0; i < completeQueryReply.size(); i++) {
            if (completeQueryReply.get(i).getReplyTopic().matches(publishTopic))
                return true;
        }
        return false;
    }

    public String getReply(String publishTopic) {
        QueryReply currentQueryReply = null;

        for (int i=0; i < completeQueryReply.size(); i++) {
            if (completeQueryReply.get(i).getQueryTopic().matches(publishTopic)) {
                currentQueryReply = completeQueryReply.remove(i);
                return currentQueryReply.getReplyMessage();
            }
        }

        return null;
    }

    public int numberOfComplete() {
        return completeQueryReply.size();
    }

    public int numberOfPending() {
        return pendingQueryReply.size();
    }

}