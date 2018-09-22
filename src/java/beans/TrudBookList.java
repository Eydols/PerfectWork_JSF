package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrudBookList {
     private ArrayList<TrudBook> trudBookList = new ArrayList<TrudBook>();

    private ArrayList<TrudBook> getTrudBooks() {

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from trud_book");) {
            while (rs.next()) {
                TrudBook trudBook = new TrudBook();
                trudBook.setManId(rs.getInt("man_id"));
                trudBook.setImage(rs.getString("image"));
                trudBook.setContent(rs.getString("content"));
                trudBookList.add(trudBook);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrudBookList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trudBookList;
    }

    public ArrayList<TrudBook> getTrudBookList() {
        if (!trudBookList.isEmpty()) {
            return trudBookList;
        } else {
            return getTrudBooks();
        }
    }
}
