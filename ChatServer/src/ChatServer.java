import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements ConnectionListener{

    private final ArrayList<ChatCommonConnection> clientList = new ArrayList<>();
    private ChatCommonConnection connection;

    public ChatServer(int port) throws IOException  {
        System.out.println("Server running");
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                try{
                    connection = new ChatCommonConnection(this, serverSocket.accept());
                } catch (IOException ex){
                    System.out.println("Connection exception: " + ex);
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void connect(ChatCommonConnection connection) {
        clientList.add(connection);
        sendToAll("New client connected:" + connection);
    }

    public void sendToAll(String str) {
        System.out.println(str);
        for (ChatCommonConnection connection : clientList) {
            connection.sendMessage(str);
        }
    }

    @Override
    public void disconnect(ChatCommonConnection connection) {
        clientList.remove(connection);
        sendToAll("Client disconnected:" + connection);
    }

    @Override
    public void sendString(ChatCommonConnection connection, String str) { sendToAll(str); }

    @Override
    public void exception(ChatCommonConnection connection, IOException ex) { System.out.println("Connection exception: " + ex); }
}
