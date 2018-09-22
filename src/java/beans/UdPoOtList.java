package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdPoOtList {
    
    private ArrayList<UdPoOt> udPoOtList = new ArrayList<UdPoOt>();

    private ArrayList<UdPoOt> getUdPoOt(String str) {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(str);) {

            while (rs.next()) {
                UdPoOt udPoOt = new UdPoOt();
                udPoOt.setManId(rs.getInt("man_id"));
                udPoOt.setDateOkonch(rs.getString("date_okonch"));
                udPoOt.setImage(rs.getString("image"));
                udPoOt.setContent(rs.getString("content"));
                udPoOtList.add(udPoOt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UdPoOtList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return udPoOtList;
    }

    public ArrayList<UdPoOt> getUdPoOtList() {
        if (!udPoOtList.isEmpty()) {
            return udPoOtList;
        } else {
            return getAllUdPoOt();
        }
    }

    public ArrayList<UdPoOt> getAllUdPoOt() {
        return getUdPoOt("select * from ud_poot");
    }
}
