
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatClient extends JFrame implements ActionListener, ConnectionListener {
    private static final String IP = "";
    private static final int PORT = 5555;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private final JPanel panel = new JPanel();
    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("Nickname");
    private final JTextField fieldIn = new JTextField();

    private ChatCommonConnection client;

    ChatClient() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        log.setEnabled(false);

        //log.setForeground(new Color(170,236,219));
        ImageIcon icon = new ImageIcon(getClass().getResource("img.png"));
        setIconImage(icon.getImage());
        setTitle("Chat");

        fieldIn.addActionListener(this);

        add(fieldNickname, BorderLayout.NORTH);
        fieldNickname.setForeground(new Color(170,26,219));

        add(log, BorderLayout.CENTER);
        //log.setForeground(new Color(255,0,0));

        add(fieldIn, BorderLayout.SOUTH);

        setVisible(true);

        try{
            client = new ChatCommonConnection(this, IP, PORT);
        } catch (IOException ex){
            sendMessage("Connection exception:" + ex);
            ex.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String str = fieldIn.getText();
        if (str.equals("")) {return;}
        fieldIn.setText(null);
        client.sendMessage(fieldNickname.getText() + ": " + str);
    }

    @Override
    public void connect(ChatCommonConnection connection) { sendMessage("Connection ready:"); }

    @Override
    public void disconnect(ChatCommonConnection connection) { sendMessage("Connection close:"); }

    @Override
    public void sendString(ChatCommonConnection connection, String str) { sendMessage(str); }

    @Override
    public void exception(ChatCommonConnection connection, IOException ex) { sendMessage("Connection exception:" + ex); }

    private synchronized void sendMessage(String str){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(str + "\n");
            }
        });
    }
}
