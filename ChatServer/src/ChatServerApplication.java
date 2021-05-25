import java.io.IOException;

public class ChatServerApplication {
    public static void main(String[] args){
        try {
            new ChatServer(5555);

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
