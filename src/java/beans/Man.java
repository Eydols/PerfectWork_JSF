package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Man {
    
    public Man() {
    }
    
    private int id;
    private String surname;
    private String name;  
    private String otchestvo;
    private String birth_date = "00.00.0000"; // Потому что когда высчитывается возраст, то если это поле не заполнено, то выходит ошибка, т.к. там мы вычитаем из текущей даты дату дня рождения, а ее нет
    private String firm;
    private String doljnost;
    private String firm2;
    private String doljnost2;
    private String photo;
    
    private boolean edit; // это поле отвечает за флажок напротив сотрудника на странице man.xhtml
    
    public Man(String surname) {
        this.surname = surname;
    }
    
//<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getOtchestvo() {
        return otchestvo;
    }
    
    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }
    
    public String getBirth_date() {
        return birth_date;
    }
    
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
    
    public String getFirm() {
        return firm;
    }
    
    public void setFirm(String firm) {
        this.firm = firm;
    }
    
    public String getDoljnost() {
        return doljnost;
    }
    
    public void setDoljnost(String doljnost) {
        this.doljnost = doljnost;
    }
    
    public String getFirm2() {
        return firm2;
    }
    
    public void setFirm2(String firm2) {
        this.firm2 = firm2;
    }
    
    public String getDoljnost2() {
        return doljnost2;
    }
    
    public void setDoljnost2(String doljnost2) {
        this.doljnost2 = doljnost2;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public boolean isEdit() {
        return edit;
    }
    
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
//</editor-fold>

public int getAge() { //Высчисляет возраст сотрудника через его ДР и возвращает на страницу man.jsp
    Date currentDate = new Date(); // Возвращает текущую дату
    Date birthDate = null;
         
         SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //Класс SimpleDataFormat используется для того, чтобы отображать дату и время в удобном формате
         try {
       birthDate = format.parse(birth_date);
       } catch (Exception e) {
           e.printStackTrace();
       }
       long diffrent = currentDate.getTime() - birthDate.getTime(); // Количество миллисекунд между датами
       int age = (int) (diffrent/(24*3600*1000))/365; // Количество лет между датами
       return age;
    }       

public int getAgeDays() { //Высчисляет количество дней, которое осталось до ДР сотрудника и возвращает на страницу man.jsp
    Date currentDate = new Date(); // Возвращает текущую дату
    Date birthDate = null;
         
         SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //Класс SimpleDataFormat используется для того, чтобы отображать дату и время в удобном формате
         try {
       birthDate = format.parse(birth_date);
       } catch (Exception e) {
           e.printStackTrace();
       }
       long diffrent = currentDate.getTime() - birthDate.getTime(); // Количество миллисекунд между датами
       int days = (int) ((365-((diffrent/(24*3600*1000))%365))+((diffrent/(24*3600*1000))/(365*4))); // Количество дней до следующего дня рождения от текущей даты (учитывая весокосные годы)

       return days;
    }       
}

