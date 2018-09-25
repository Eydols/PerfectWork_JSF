package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiplomSsuzList {

    private ArrayList<DiplomSsuz> diplomSsuzList = new ArrayList<DiplomSsuz>();

    private ArrayList<DiplomSsuz> getDiplomsSsuz() {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from diplom_ssuz");) {
            while (rs.next()) {
                DiplomSsuz diplomSsuz = new DiplomSsuz();
                diplomSsuz.setManId(rs.getInt("man_id"));
                diplomSsuz.setImage(rs.getString("image"));
                diplomSsuz.setContent(rs.getString("content"));
                diplomSsuzList.add(diplomSsuz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiplomSsuzList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return diplomSsuzList;
    }

    public ArrayList<DiplomSsuz> getDiplomSsuzList() {
        if (!diplomSsuzList.isEmpty()) {
            return diplomSsuzList;
        } else {
            return getDiplomsSsuz();
        }
    }
}
