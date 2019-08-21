package com.monotoneid.eishms.services.mqttcommunications;

public class QueryReply {
    private String queryTopic;
    private String replyTopic;
    private String replyMessage;
    private boolean overwritten;

    public QueryReply(String publishTopic, String subscribeTopic) {
        queryTopic = publishTopic;
        replyTopic = subscribeTopic;
        replyMessage = "";
        overwritten = false;
    }


    public void setQueryTopic(String publishTopic) {
        queryTopic = publishTopic;
    }

    public void setReplyTopic(String subscribeTopic) {
        replyTopic = subscribeTopic;
    }

    public String getQueryTopic() {
        return queryTopic;
    }

    public String getReplyTopic() {
        return replyTopic;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public boolean setReplyMessage(String subscribeTopic, String replyMessage) { //Return true if successfully set else false
        if (replyTopic.matches(subscribeTopic)) {
            if (replyExists())
                overwritten = true; 
            setReplyMessage(replyMessage);
            return true;
        }

        return false;    
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public boolean replyExists() {
        return replyMessage.length() != 0;
    }

    public boolean isOverwritten() {
        return overwritten;
    }
}