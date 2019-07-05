package com.monotoneid.eishms.services.mqttCommunications;

import java.util.ArrayList;
import java.util.List;

public class QueryReplyHandler {
    List<QueryReply> pendingQueryReply;
    List<QueryReply> completeQueryReply;

    public QueryReplyHandler() {
        pendingQueryReply = new ArrayList<QueryReply>();
        completeQueryReply = new ArrayList<QueryReply>();
    }

    public boolean register(QueryReply queryReply) {
        
    }

    public int numberOfComplete() {
        return completeQueryReply.size();
    }

    public int numberOfPending() {
        return pendingQueryReply.size();
    }


}