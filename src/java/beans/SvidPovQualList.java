package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SvidPovQualList {
    
     private ArrayList<SvidPovQual> svidPovQualList = new ArrayList<SvidPovQual>();

    private ArrayList<SvidPovQual> getSvidPovQual() {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from svid_povqual");) {
            while (rs.next()) {
                SvidPovQual svidPovQual = new SvidPovQual();
                svidPovQual.setManId(rs.getInt("man_id"));
                svidPovQual.setImage(rs.getString("image"));
                svidPovQual.setContent(rs.getString("content"));
                svidPovQualList.add(svidPovQual);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SvidPovQualList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return svidPovQualList;
    }

    public ArrayList<SvidPovQual> getSvidPovQualList() {
        if (!svidPovQualList.isEmpty()) {
            return svidPovQualList;
        } else {
            return getSvidPovQual();
        }
    }
    
}
