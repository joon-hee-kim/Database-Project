package jdbcSns;

public class Post {
    private String id;
    private String content;
    private String writer;
    private String date;
    private int likes;

    public Post(String id, String content, String writer, String date) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.date = date;
        this.likes = 0;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
