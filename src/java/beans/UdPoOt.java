package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UdPoOt {

    public UdPoOt() {
    }

    private int man_id;
    private String date_okonch;
    private String image;
    private String content;

    public int getManId() {
        return man_id;
    }

    public void setManId(int man_id) {
        this.man_id = man_id;
    }

    public String getDateOkonch() {
        return date_okonch;
    }

    public void setDateOkonch(String date_okonch) {
        this.date_okonch = date_okonch;
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

    public int getOkonchDays() { //Высчисляет количество дней, которое осталось до окончания срока действия аттестата
        Date currentDate = new Date(); // Возвращает текущую дату
        Date dateOkonch = null;

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //Класс SimpleDataFormat используется для того, чтобы отображать дату и время в удобном формате
        try {
            dateOkonch = format.parse(date_okonch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long diffrent = currentDate.getTime() - dateOkonch.getTime(); // Количество миллисекунд между датами
        int days = (int) (diffrent / (24 * 3600 * 1000)); // Количество дней до окончания срока действия аттестата

        return days;
    }

}
