package beans;

import db.Database;
import enums.SearchType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManList {

    private ArrayList<Man> manList = new ArrayList<Man>();

    private ArrayList<Man> getMen(String sql) {
        manList.clear();

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Man man = new Man();
                man.setId(rs.getInt("id"));
                man.setName(rs.getString("name"));
                man.setSurname(rs.getString("surname"));
                man.setOtchestvo(rs.getString("otchestvo"));
                man.setBirthDate(rs.getString("birth_date"));
                man.setFirm(rs.getString("firm"));
                man.setDoljnost(rs.getString("doljnost"));
                man.setFirm2(rs.getString("firm2"));
                man.setDoljnost2(rs.getString("doljnost2"));
                man.setPhoto(rs.getString("photo"));
                manList.add(man);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return manList;
    }

    public ArrayList<Man> getAllMan() {
        return getMen("select m.id, m.name, m.surname, m.otchestvo, m.birth_date, m.photo, sf.firm as firm, sd.doljnost as doljnost, sf2.firm as firm2, sd2.doljnost as doljnost2 from man m \n"
                + "inner join spr_firm sf on m.firm_id=sf.id\n"
                + "inner join spr_firm sf2 on m.firm2_id=sf2.id\n"
                + "inner join spr_doljnost sd on m.doljnost_id=sd.id\n"
                + "inner join spr_doljnost sd2 on m.doljnost2_id=sd2.id order by m.surname");
    }

    public ArrayList<Man> getManBySearch(String searchStr, String type) {

        return getMen("select m.id, m.name, m.surname, m.otchestvo, m.birth_date, m.photo, sf.firm as firm, sd.doljnost as doljnost, sf2.firm as firm2, sd2.doljnost as doljnost2 from man m \n"
                + "inner join spr_firm sf on m.firm_id=sf.id\n"
                + "inner join spr_firm sf2 on m.firm2_id=sf2.id\n"
                + "inner join spr_doljnost sd on m.doljnost_id=sd.id\n"
                + "inner join spr_doljnost sd2 on m.doljnost2_id=sd2.id\n"
                + "where (m.surname like '%" + searchStr.toUpperCase() + "%' and sf.firm like '%" + type.toUpperCase() + "%') or (m.surname like '%" + searchStr.toUpperCase() + "%' and sf2.firm like '%" + type.toUpperCase() + "%') order by m.surname");
    }

}
