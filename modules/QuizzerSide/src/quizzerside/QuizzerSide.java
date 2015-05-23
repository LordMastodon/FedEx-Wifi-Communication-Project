package quizzerside;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPort;
import quizzerside.gui.Console;
import quizzerside.wificommunication.PortOut;

import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class QuizzerSide extends JFrame {
    public static Console console = new Console();
    public static JTextField inputField = new JTextField();

    public static Font font = new Font("Avenir-Light", Font.PLAIN, 12);

    private static PortOut sender;

    private void addComponentsToPane() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panel);
        setLayout(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        console.setEditable(false);

        JScrollPane consoleScrollPane = new JScrollPane(console);

        console.setBackground(new Color(230, 230, 250));
        console.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        console.setFont(font);

        DefaultCaret caret = (DefaultCaret) console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        inputField.setFont(font);

        inputField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterKeyPressed");
        inputField.getActionMap().put("enterKeyPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.printToConsole("[User] " + inputField.getText());

                try {
                    boolean isReceiving = sender.testReceiving();

                    if(isReceiving) {
                        OSCMessage msg = new OSCMessage(inputField.getText());
                        sender.send(msg);
                    } else {
                        System.out.println();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                inputField.setText("");
            }
        });

        getContentPane().add(inputField, BorderLayout.SOUTH);
        getContentPane().add(consoleScrollPane);
    }

    private static void createAndShowGUI() {
        QuizzerSide frame = new QuizzerSide("Quiz Creator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentsToPane();

        frame.setSize(800, 600);
        frame.setVisible(true);

        frame.setResizable(false);

        inputField.requestFocusInWindow();
    }

    public QuizzerSide(String name) {
        super(name);

        try {
            sender = new PortOut(InetAddress.getByName("127.0.0.1"), OSCPort.defaultSCOSCPort());
        } catch (UnknownHostException | SocketException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
