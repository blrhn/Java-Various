package common.message;

import com.google.gson.Gson;

public class MessageSerializer {
    private static final Gson gson = new Gson();

    private MessageSerializer() {}

    public static String encode(Message msg) {
        return gson.toJson(msg);
    }

    public static Message decode(String msg) {
        return gson.fromJson(msg, Message.class);
    }
}
