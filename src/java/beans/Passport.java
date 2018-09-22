package beans;

public class Passport {
    
    public Passport() {  
        
    }
    
   private int man_id;
   private String date_okonch;
   private String image;
   private String content;
   
    public int getManId() {
        return man_id;
    }
    
    public void setManId(int man_id) {
        this.man_id=man_id;
    }
    

    public String getDateOkonch() {
        return date_okonch;
    }
    
    public void setDateOkonch(String date_okonch) {
        this.date_okonch=date_okonch;
    }

    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image=image;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content=content;
    }
}
