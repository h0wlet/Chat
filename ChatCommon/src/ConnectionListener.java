import java.io.IOException;

public interface ConnectionListener {
    void connect(ChatCommonConnection connection);
    void disconnect(ChatCommonConnection connection);
    void sendString(ChatCommonConnection connection, String str);
    void exception(ChatCommonConnection connection, IOException ex);

}
