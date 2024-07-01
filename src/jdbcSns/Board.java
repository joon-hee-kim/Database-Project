package jdbcSns;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Board extends JFrame {
    private JTextField POST;
    String ID;
    String owner;
    JTextArea board;
    Board me;
    DbAccess db;
    JLabel OWNER;
    JPanel postsPanel;

    public Board(String id) {
        super("BOARD");
        this.ID = id;
        me = this;
        db = new DbAccess();
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 720, 483);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 13));
        menuBar.setBackground(Color.LIGHT_GRAY);
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("menu");
        mnNewMenu.setFont(new Font("Arial Black", Font.PLAIN, 16));
        menuBar.add(mnNewMenu);

        JMenuItem Follower = new JMenuItem("My Follower");
        Follower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Follow(me, db, ID);
            }
        });
        Follower.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        mnNewMenu.add(Follower);

        JMenuItem myBoard = new JMenuItem("My board");
        myBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newboard(ID, db.IndividualBoard(ID));
                owner = ID;
            }
        });
        mnNewMenu.add(myBoard);

        JMenuItem timelineItem = new JMenuItem("Timeline");
        timelineItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTimeline();
            }
        });
        mnNewMenu.add(timelineItem);

        myBoard.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));

        JMenuItem Search = new JMenuItem("Search");
        Search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Search frame = new Search(ID, me, db);
                frame.setVisible(true);
            }
        });
        Search.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        mnNewMenu.add(Search);

        JMenuItem Edit = new JMenuItem("PW Edit");
        Edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Edit(id, db);
            }
        });
        Edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        mnNewMenu.add(Edit);
        getContentPane().setLayout(null);

        JLabel myID = new JLabel(ID);
        myID.setHorizontalAlignment(SwingConstants.CENTER);
        myID.setFont(new Font("Arial Black", Font.PLAIN, 13));
        myID.setBackground(new Color(85, 107, 47));
        myID.setBounds(12, 14, 72, 22);
        getContentPane().add(myID);

        OWNER = new JLabel();
        OWNER.setFont(new Font("HY°ß°íµñ", Font.PLAIN, 20));
        OWNER.setHorizontalAlignment(SwingConstants.CENTER);
        OWNER.setBackground(new Color(173, 255, 47));
        OWNER.setBounds(245, 10, 204, 31);
        getContentPane().add(OWNER);

        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setBounds(119, 172, 488, 245);
        getContentPane().add(scrollPane);
        setBoard(ID);

    }

    public void setBoard(String Owner) {
        this.owner = Owner;
        String userName = db.getUserName(Owner);
        if (userName != null && !userName.trim().isEmpty()) {
            OWNER.setText(userName);
        } else {
            OWNER.setText("Timeline");
        }
        OWNER.setFont(new Font("HY°ß°íµñ", Font.PLAIN, 20));
        OWNER.setHorizontalAlignment(SwingConstants.CENTER);
        OWNER.setBackground(new Color(173, 255, 47));
        OWNER.setBounds(245, 10, 204, 31);
        getContentPane().add(OWNER);

        POST = new JTextField();
        POST.setBounds(116, 51, 491, 77);
        getContentPane().add(POST);
        POST.setColumns(10);

        JButton postB = new JButton("post");
        postB.setFont(new Font("Arial Black", Font.PLAIN, 12));
        postB.setBackground(new Color(255, 255, 255));
        postB.setBounds(508, 129, 99, 25);
        postB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (POST.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Input message");
                } else if (POST.getText().length() > 150) {
                    JOptionPane.showMessageDialog(null, "Message's length doesn't exceed 150 characters");
                } else {
                    JOptionPane.showMessageDialog(null, "Post");
                    db.addPost(owner, ID, POST.getText());
                    newboard(owner, db.IndividualBoard(owner));
                    POST.setText("");
                }
            }
        });
        postB.setFont(new Font("Arial Black", Font.PLAIN, 12));
        postB.setBackground(new Color(255, 255, 255));
        postB.setBounds(508, 129, 99, 25);
        getContentPane().add(postB);
        board = new JTextArea();
        board.setEditable(false);
        board.setText(db.IndividualBoard(ID));
        JScrollPane p = new JScrollPane(board);
        p.setBounds(119, 172, 488, 245);
        getContentPane().add(p);

        loadPostsForUser(Owner);

    }

    private void loadPostsForUser(String userId) {
        postsPanel.removeAll();
        List<Post> posts = db.getPostsForUser(userId);
        for (Post post : posts) {
            postsPanel.add(createPostPanel(post));
        }
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        JLabel postLabel = new JLabel(post.getContent());
        postLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        postPanel.add(postLabel);

        JLabel separator = new JLabel("---------------------------------");
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        postPanel.add(separator);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton likeButton = new JButton("Likes: " + post.getLikes());
        likeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.toggleLike(post.getId(), ID);
                updateLikes(post, likeButton);
            }
        });
        buttonPanel.add(likeButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.deletePost(post.getId());
                loadPostsForUser(owner);
            }
        });
        buttonPanel.add(deleteButton);

        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        postPanel.add(buttonPanel);

        return postPanel;
    }

    private void updateLikes(Post post, JButton likeButton) {
        int likes = db.getLikesForPost(post.getId());
        likeButton.setText("Likes: " + likes);
        post.setLikes(likes);
    }

    public void setTimeline() {
        board.setText(db.allBoard(ID));
        OWNER.setText("Timeline");
    }

    public void newboard(String id, String post) {
        String userName = db.getUserName(id);
        if (userName != null) {
            OWNER.setText(userName);
        } else {
            OWNER.setText(id);
        }
        board.setText(post);
    }

    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
}
