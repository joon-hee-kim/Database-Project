package jdbcSns;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.List;

public class ChatWindow extends JFrame {
    private JTextArea messageArea;
    private JTextField messageField;
    private String userId;
    private String chatPartnerId;
    private Socket socket;
    private PrintWriter out;
    private DbAccess db;

    public ChatWindow(String userId, String chatPartnerId, DbAccess db) {
        this.userId = userId;
        this.chatPartnerId = chatPartnerId;
        this.db = db;

        messageArea = new JTextArea(8, 40);
        messageField = new JTextField(40);
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(messageField.getText());
                messageField.setText("");
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(messageField, BorderLayout.SOUTH);
        pack();
        setTitle("Chat with " + chatPartnerId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        connectToServer();
        loadMessagesFromDb(); // 초기 메시지 불러오기
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 9001);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(userId);

            new Thread(new IncomingReader()).start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "서버 연결에 실패했습니다.", "연결 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            String formattedMessage = userId + " (" + new Timestamp(System.currentTimeMillis()) + "): " + message;
            out.println(formattedMessage);
            db.saveMessage(userId, chatPartnerId, message); 
        }
    }

    private void loadMessagesFromDb() {
        List<Message> messages = db.getMessages(userId, chatPartnerId);
        for (Message message : messages) {
            messageArea.append(message.getSenderId() + " (" + message.getTimestamp() + "): " + message.getMessageText() + "\n");
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    messageArea.append(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
