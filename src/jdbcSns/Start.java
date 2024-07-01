package jdbcSns;

import javax.swing.SwingUtilities;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login(); // 로그인 창을 시작
            }
        });
    }
}
