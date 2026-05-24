package common.message;

public record Message(
        MessageType messageType,
        String sender,
        String content,
        String fileName,
        String timestamp) {}
