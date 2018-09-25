package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiplomVuzList {

    private ArrayList<DiplomVuz> diplomVuzList = new ArrayList<DiplomVuz>();

    private ArrayList<DiplomVuz> getDiplomsVuz() {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from diplom_vuz");) {
            while (rs.next()) {
                DiplomVuz diplomVuz = new DiplomVuz();
                diplomVuz.setManId(rs.getInt("man_id"));
                diplomVuz.setImage(rs.getString("image"));
                diplomVuz.setContent(rs.getString("content"));
                diplomVuzList.add(diplomVuz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiplomVuzList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return diplomVuzList;
    }

    public ArrayList<DiplomVuz> getDiplomVuzList() {
        if (!diplomVuzList.isEmpty()) {
            return diplomVuzList;
        } else {
            return getDiplomsVuz();
        }
    }
}
