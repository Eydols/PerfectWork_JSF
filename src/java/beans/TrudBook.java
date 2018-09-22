package beans;

public class TrudBook {
    public TrudBook() {
    }
    
    private int man_id;
    private String image;
    private String content;

    public int getManId() {
        return man_id;
    }

    public void setManId(int man_id) {
        this.man_id = man_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
