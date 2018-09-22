package controllers;

import beans.LeftMenuRow;
import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class LeftMenuController {

    private ArrayList<LeftMenuRow> leftList;

    public LeftMenuController() {
        fillLeftMenu();
    }

    private void fillLeftMenu() {

        leftList = new ArrayList<LeftMenuRow>();
        
        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from left_menu");) {
            while (rs.next()) {
                LeftMenuRow leftMenuRow = new LeftMenuRow();
                leftMenuRow.setId(rs.getInt("id"));
                leftMenuRow.setName_ru(rs.getString("name_ru"));
                leftMenuRow.setName_en(rs.getString("name_en"));
                leftMenuRow.setPage(rs.getString("page"));
                leftList.add(leftMenuRow);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeftMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<LeftMenuRow> getLeftList() {      
            return leftList;
        }
    }

