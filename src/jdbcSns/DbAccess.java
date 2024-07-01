package jdbcSns;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DbAccess {
    Connection con;

    public DbAccess() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/mydb", "hurki", "!Nerdman1154");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean disconnect(String id, String follower) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from follow where ID = '" + id + "'and f_id = '" + follower + "';");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void toggleLike(String postId, String userId) {
        try {
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM Likes WHERE post_id = ? AND user_id = ?");
            checkStmt.setString(1, postId);
            checkStmt.setString(2, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM Likes WHERE post_id = ? AND user_id = ?");
                deleteStmt.setString(1, postId);
                deleteStmt.setString(2, userId);
                deleteStmt.executeUpdate();
                deleteStmt.close();
            } else {
                PreparedStatement insertStmt = con.prepareStatement("INSERT INTO Likes (post_id, user_id) VALUES (?, ?)");
                insertStmt.setString(1, postId);
                insertStmt.setString(2, userId);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
            checkStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLikesForPost(String postId) {
        int likes = 0;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) AS LikeCount FROM Likes WHERE post_id = ?");
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                likes = rs.getInt("LikeCount");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    public List<Post> getPostsForUser(String userId) {
        List<Post> posts = new ArrayList<>();
        try {
            String query = "SELECT id, post, writer, date FROM Board WHERE writer = ? ORDER BY date DESC;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String content = rs.getString("post");
                String writer = rs.getString("writer");
                String date = rs.getString("date");
                Post post = new Post(id, content, writer, date);
                post.setLikes(getLikesForPost(id));
                posts.add(post);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> getTimelinePosts(String userId) {
        List<Post> posts = new ArrayList<>();
        try {
            String query = "SELECT b.id, b.post, b.writer, b.date FROM Board b " +
                           "JOIN follow f ON b.writer = f.f_id " +
                           "WHERE f.id = ? OR b.writer = ? " +
                           "ORDER BY b.date DESC;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String postid = rs.getString("id");
                String content = rs.getString("post");
                String writer = rs.getString("writer");
                String date = rs.getString("date");
                posts.add(new Post(postid, content, writer, date));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public String getUserName(String userId) {
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT name FROM User WHERE id = ?");
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveMessage(String senderId, String receiverId, String message) {
        String sql = "INSERT INTO Messages (sender_id, receiver_id, message_text, timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, senderId);
            pstmt.setString(2, receiverId);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // 현재 시간 추가
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages(String userId, String chatPartnerId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender_id, message_text, timestamp FROM Messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, chatPartnerId);
            pstmt.setString(3, chatPartnerId);
            pstmt.setString(4, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String senderId = rs.getString("sender_id");
                    String messageText = rs.getString("message_text");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    messages.add(new Message(senderId, messageText, timestamp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }    
    

    public void pwEdit(String id, String newPW) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("update User set pw = '" + newPW + "' where id = '" + id + "';");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String follow(String id) {
        String follow = "";
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery("select f_id from Follow where id = '" + id + "';");
            ResultSetMetaData rsmd = rs.getMetaData();

            int colnum = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= colnum; i++)
                    follow = follow + rs.getString(i) + "\n";
            }
            rs.close();
            stmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return follow;
    }

    public String Recommend(String id) {
        String recommend = "";
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT f_id FROM Follow WHERE id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                recommend += rs.getString("id") + " / ";
                recommend += "\n-----------------------------------\n";
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return recommend;
    }

    public String allBoard(String id) {
        String nn = "";
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery("select date, writer, post from Board where ID = '" + id + "' or ID in "
                    + "(select f_id from Follow where ID = '" + id + "') order by date desc;");
            ResultSetMetaData rsmd = rs.getMetaData();

            int colnum = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= colnum; i++)
                    nn = nn + rs.getString(i) +  "\n";
                nn += "--------------------------------------------\n";
            }
            rs.close();
            stmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nn;
    }

    public String individualInform(String id) {
        String nn = "";
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery("select id, name, birth from User where ID = '" + id + "';");
            ResultSetMetaData rsmd = rs.getMetaData();

            int colnum = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= colnum; i++)
                    nn = nn + rs.getString(i) + " / ";
            }
            rs.close();
            stmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nn;
    }

    public String IndividualBoard(String id) {
        String nn = "";
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2
                    .executeQuery("select date,writer, post from Board where ID = '" + id + "' order by date desc;");
            ResultSetMetaData rsmd = rs.getMetaData();

            int colnum = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= colnum; i++){
                    if(i == 2 && !rs.getString(i).equals(id)){
                        nn += "@"+ rs.getString(i) + "\n";
                        continue;
                    }
                    nn = nn + rs.getString(i) + "\n";
                }
                nn += "--------------------------------------------\n";
            }

            rs.close();
            stmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nn;
    }

    public void addUser(String id, String name, int birth, String pw) {
        try {
            PreparedStatement pStmt = con.prepareStatement("insert into User values (?,?,?,?)");
            pStmt.setString(1, id);
            pStmt.setString(2, name);
            pStmt.setInt(3, birth);
            pStmt.setString(4, pw);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addfollow(String id, String fid) {
        try {
            PreparedStatement pStmt = con.prepareStatement("insert into Follow values (?,?)");
            pStmt.setString(1, id);
            pStmt.setString(2, fid);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DbAccess a = new DbAccess();
        System.out.println(a.individualInform("a2c222"));
    }

    public void addPost(String owner, String writer, String postContent) {
        String uniqueID = generateUniqueID();
        try {
            CallableStatement pStmt = con.prepareCall("{call addpost(?,?,?,?)}");
            pStmt.setString(1, uniqueID);
            pStmt.setString(2, owner);
            pStmt.setString(3, writer);
            pStmt.setString(4, postContent);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    public void deletePost(String postId) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Board WHERE id = ?");
            pstmt.setString(1, postId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(String senderId, String receiverId, Timestamp timestamp) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Messages WHERE sender_id = ? AND receiver_id = ? AND timestamp = ?");
            pstmt.setString(1, senderId);
            pstmt.setString(2, receiverId);
            pstmt.setTimestamp(3, timestamp);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(String id, String pw) {
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM User WHERE ID = ? AND PW = ?");
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUser(String id) {
        try {
            PreparedStatement pstmt = con.prepareStatement("select * from User where ID = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkfollower(String id, String fid) {
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Follow WHERE ID = ? AND f_id = ?");
            pstmt.setString(1, id);
            pstmt.setString(2, fid);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
