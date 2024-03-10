import java.io.Serializable;

public class Message implements Serializable {
    public String content;
    public String name;

    public Message(String s, String name) {
        content = s;
        this.name = name;
    }
}
