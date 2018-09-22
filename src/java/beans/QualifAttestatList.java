package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QualifAttestatList {

    private ArrayList<QualifAttestat> qualifAttestatList = new ArrayList<QualifAttestat>();

    private ArrayList<QualifAttestat> getQualifAttestats(String str) {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(str);) {

            while (rs.next()) {
                QualifAttestat qualifAttestat = new QualifAttestat();
                qualifAttestat.setManId(rs.getInt("man_id"));
                qualifAttestat.setDateOkonch(rs.getString("date_okonch"));
                qualifAttestat.setImage(rs.getString("image"));
                qualifAttestat.setContent(rs.getString("content"));
                qualifAttestatList.add(qualifAttestat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QualifAttestatList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualifAttestatList;
    }

    public ArrayList<QualifAttestat> getQualAttestatList() {
        if (!qualifAttestatList.isEmpty()) {
            return qualifAttestatList;
        } else {
            return getAllQualifAttestats();
        }
    }

    public ArrayList<QualifAttestat> getAllQualifAttestats() {
        return getQualifAttestats("select * from qualif_attestat");
    }
}
