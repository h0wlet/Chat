import javax.swing.*;
import java.io.IOException;

public class ChatClientApplication {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ChatClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
