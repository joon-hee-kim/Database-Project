package jdbcSns;

import java.sql.Timestamp;

public class Message {
    private String senderId;
    private String messageText;
    private Timestamp timestamp;

    public Message(String senderId, String messageText, Timestamp timestamp) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
