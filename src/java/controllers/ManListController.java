package controllers;

import beans.Man;
import db.Database;
import enums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(eager = true)
@SessionScoped
public class ManListController implements Serializable {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    private String searchString;
    private SearchType searchType;
    private ArrayList<Man> currentManList;

    // переменные для постраничности
    private int totalManCount; // общее количество найденных сотрудников
    private int manOnPage = 2; // количество сотрудников, отображаемое на одной странице
    private int pageCount; // количество страниц
    private long selectedPageNumber = 1; // выбранный номер страницы в постраничной навигации
    private ArrayList<Integer> pageNumbersList = new ArrayList<Integer>(); //общее количество страниц

    private String currentSql;

    private boolean editMode = false; // предназначена для переключения ржимов отображения(false)/редактирования(true) данных сотрудников

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public ManListController() {

    }

    private void fillManBySQL(String sql) {

        imitateLoading();

        StringBuilder sqlBuilder = new StringBuilder(sql);
        currentSql = sql;

        try {
            conn = Database.getConnection(); // доработать, чтобы использовать блок try-catch с ресурсами
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlBuilder.toString());

            rs.last();
            totalManCount = rs.getRow();
            fillPageNumbers(totalManCount, manOnPage);

            if (totalManCount > manOnPage) {
                sqlBuilder.append(" limit ").append(selectedPageNumber * manOnPage - manOnPage).append(",").append(manOnPage);
            }

            rs = stmt.executeQuery(sqlBuilder.toString());

            currentManList = new ArrayList<Man>();

            while (rs.next()) {
                Man man = new Man();
                man.setId(rs.getInt("id"));
                man.setName(rs.getString("name"));
                man.setSurname(rs.getString("surname"));
                man.setOtchestvo(rs.getString("otchestvo"));
                man.setBirth_date(rs.getString("birth_date"));
                man.setFirm(rs.getString("firm"));
                man.setDoljnost(rs.getString("doljnost"));
                man.setFirm2(rs.getString("firm2"));
                man.setDoljnost2(rs.getString("doljnost2"));
                man.setPhoto(rs.getString("photo"));
                currentManList.add(man);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManListController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void fillManAll() {
        fillManBySQL("select m.id, m.name, m.surname, m.otchestvo, m.birth_date, m.photo, sf.firm as firm, sd.doljnost as doljnost, \n"
                + "sf2.firm as firm2, sd2.doljnost as doljnost2 from man m \n"
                + "inner join spr_firm sf on m.firm_id=sf.id \n"
                + "inner join spr_firm sf2 on m.firm2_id=sf2.id \n"
                + "inner join spr_doljnost sd on m.doljnost_id=sd.id \n"
                + "inner join spr_doljnost sd2 on m.doljnost2_id=sd2.id order by m.surname");
    }

    public void fillManBySearch() {

        StringBuilder sql = new StringBuilder("select m.id, m.name, m.surname, m.otchestvo, m.birth_date, m.photo, sf.firm as firm, sd.doljnost as doljnost, \n"
                + "sf2.firm as firm2, sd2.doljnost as doljnost2 from man m \n"
                + "inner join spr_firm sf on m.firm_id=sf.id \n"
                + "inner join spr_firm sf2 on m.firm2_id=sf2.id \n"
                + "inner join spr_doljnost sd on m.doljnost_id=sd.id \n"
                + "inner join spr_doljnost sd2 on m.doljnost2_id=sd2.id");

        if (searchType == SearchType.KSR) { // потом можно попробовать использовать оператор switch
            sql.append(" where (lower(m.surname) like '%" + searchString.toLowerCase() + "%' and lower(sf.firm) ='" + SearchType.KSR.getFirmName().toLowerCase() + "') or (\n"
                    + "lower(m.surname) like '%" + searchString.toLowerCase() + "%' and lower(sf2.firm) ='" + SearchType.KSR.getFirmName().toLowerCase() + "')order by m.surname");

        } else if (searchType == SearchType.PERFECT) {
            sql.append(" where (lower(m.surname) like '%" + searchString.toLowerCase() + "%' and lower(sf.firm) ='" + SearchType.PERFECT.getFirmName().toLowerCase() + "')or ( \n"
                    + "lower(m.surname) like '%" + searchString.toLowerCase() + "%' and lower(sf2.firm) ='" + SearchType.PERFECT.getFirmName().toLowerCase() + "')order by m.surname");

        } else if (searchType == SearchType.ALL_FIRM) {
            sql.append(" where lower(m.surname) like '%" + searchString.toLowerCase() + "%' order by m.surname");
        }

        fillManBySQL(sql.toString());

    }

    // методы для постраничности
    public void selectPage() {
        cancelEdit();
        imitateLoading();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.valueOf(params.get("page_number"));
        fillManBySQL(currentSql);
    }

    private void fillPageNumbers(long totalBooksCount, int booksOnPage) { //определяет количество страниц и создает соответствующую коллекцию целых чисел

        pageCount = 0; //количество страниц
        if (totalBooksCount == 0) {
            pageCount = 0;
        } else if (totalBooksCount % booksOnPage == 0) {
            pageCount = (int) totalBooksCount / booksOnPage;
        } else {
            pageCount = (int) totalBooksCount / booksOnPage + 1;
        }

        pageNumbersList.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbersList.add(i);
        }
    }
    
    public void manOnPageChanged(ValueChangeEvent e) { //выполняется при выборе (из выпадающего списка) пользователем количества отображаемых на одной странице сотрудников 
    imitateLoading();
    cancelEdit();
    manOnPage = Integer.valueOf(e.getNewValue().toString()).intValue();
    selectedPageNumber = 1;
    fillManBySQL(currentSql);
    
    }
    

    private void imitateLoading() { // имитация загрузки процесса
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException ex) {
            Logger.getLogger(ManListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateMan() { // обновляет измененные данные сотудников после редактирования
        imitateLoading();

        try (Connection conn = Database.getConnection();
                PreparedStatement prepStmt = conn.prepareStatement("update man set name=?, surname=?, otchestvo=?, birth_date=? where id=?");
                ResultSet rs = null;) {
            for (Man man : currentManList) {
                if (!man.isEdit()) {
                    continue; // для сотрудников, напротив которых флажок не был нажат, обновление данных не происходит
                }
                prepStmt.setString(1, man.getName());
                prepStmt.setString(2, man.getSurname());
                prepStmt.setString(3, man.getOtchestvo());
                prepStmt.setString(4, man.getBirth_date());
                prepStmt.setInt(5, man.getId());
                prepStmt.addBatch();
            }
            prepStmt.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(ManListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        switchEditMode();
        return "man";
    }

    public void switchEditMode() {
        editMode = !editMode;
    }

    public void cancelEdit() { // выполнятся при нажатии кнопки Отмена в режиме редактирования на странице man.xhtml
        editMode = false;
        for (Man man : currentManList) {
            man.setEdit(false);
        }
    }

    public void searchTypeChanged(ValueChangeEvent e) { // благодаря данному методу сохраняется выбранный тип поиска при переключении языка
        searchType = (SearchType) e.getNewValue();
    }

    public void searchStringChanged(ValueChangeEvent e) { //благодаря данному методу сохраняются символы, введенные в поисковую строку, при переключении языка
        searchString = (String) e.getNewValue();
    }

    //<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public SearchType getSearchType() {
        return searchType;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void setCurrentManList(ArrayList<Man> currentManList) {
        this.currentManList = currentManList;
    }

    public ArrayList<Man> getCurrentManList() {
        return currentManList;
    }

    public int getTotalManCount() {
        return totalManCount;
    }

    public void setTotalManCount(int totalManCount) {
        this.totalManCount = totalManCount;
    }

    public int getManOnPage() {
        return manOnPage;
    }

    public void setManOnPage(int manOnPage) {
        this.manOnPage = manOnPage;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(long selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public ArrayList<Integer> getPageNumbersList() {
        return pageNumbersList;
    }

    public void setPageNumbersList(ArrayList<Integer> pageNumbersList) {
        this.pageNumbersList = pageNumbersList;
    }
//</editor-fold>

}
