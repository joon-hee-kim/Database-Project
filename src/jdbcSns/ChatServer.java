package jdbcSns;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 9001;
    private static ExecutorService pool = Executors.newFixedThreadPool(50);
    private static Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                Socket clientSocket = listener.accept();
                pool.execute(new Handler(clientSocket));
            }
        } finally {
            listener.close();
            pool.shutdown();
        }
    }

    private static class Handler implements Runnable {
        private Socket socket;
        private String clientId;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // 클라이언트 식별자 또는 초기 설정 메시지를 받습니다.
                clientId = in.readLine();
                clients.put(clientId, out);

                String input;
                while ((input = in.readLine()) != null) {
                    for (PrintWriter writer : clients.values()) {
                        writer.println(clientId + ": " + input); // 메시지 전송
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (clientId != null) {
                    clients.remove(clientId);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}