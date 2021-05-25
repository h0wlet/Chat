import java.io.*;
import java.net.Socket;

public class ChatCommonConnection {

    private final Socket socket;
    private final Thread rxThread;
    private final ConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ChatCommonConnection(ConnectionListener eventListener, String ip, int port) throws IOException {
        this(eventListener, new Socket(ip, port));
    }

    public ChatCommonConnection(ConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventListener.connect(ChatCommonConnection.this);
                    while(!rxThread.isInterrupted()){
                        eventListener.sendString(ChatCommonConnection.this, in.readLine());
                    }
                } catch (IOException ex){
                    eventListener.exception(ChatCommonConnection.this, ex);
                } finally {
                    eventListener.disconnect(ChatCommonConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendMessage(String str){
        try {
            out.write(str + "\n");
            out.flush();
        } catch (IOException ex){
            eventListener.exception(ChatCommonConnection.this, ex);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        rxThread.interrupted();
        try {
            socket.close();
        } catch (IOException ex){
            eventListener.exception(ChatCommonConnection.this, ex);
        }
    }

}
