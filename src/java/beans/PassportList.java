package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassportList {

    private Passport passEmpty = new Passport();
    private Passport pass;
    private ArrayList<Passport> passportList = new ArrayList<Passport>();

    private ArrayList<Passport> getPassports(String str) {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(str);) {

            while (rs.next()) {
                Passport passport = new Passport();
                passport.setManId(rs.getInt("man_id"));
                passport.setDateOkonch(rs.getString("date_okonch"));
                passport.setImage(rs.getString("image"));
                passport.setContent(rs.getString("content"));
                passportList.add(passport);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PassportList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passportList;
    }

    public ArrayList<Passport> getPassportList() {
        if (!passportList.isEmpty()) {
            return passportList;
        } else {
            return getAllPassports();
        }
    }

    public ArrayList<Passport> getAllPassports() {
        return getPassports("select * from passport");
    }

    public Passport getPassportManId(int id) {  //Возврат паспорта по полю man_id

        for (Passport passport : getPassportList()) { // объявляем цикл по коллекции passportList. Здесь passport - локальная переменная
            if (passport.getManId() == id) { // если man_id паспорта совпадает с переданным параметром id
                pass = passport; // то переменной класса pass присваиваем значение локальной переменной, которая ссылается на нужный экземпляр класса Passport и 
                break; // выходим из цикла, т.к. man_id уникальны и на выходе у нас только один экземпляр (если id совпали)
            } else {
                pass = passEmpty; // иначе переменной класса pass присваиваем значение переменной passEmpty, т.е. объекта Passport с пустыми полями
            }
        }
        return pass;
    }

}
