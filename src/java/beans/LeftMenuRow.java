package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LeftMenuRow {

    private int id;
    private String name_ru;
    private String name_en;
    private String page;

    public LeftMenuRow() {

    }

    public int getId() {
        return id;
    }

    public String getName_ru() {
        return name_ru;
    }

    public String getName_en() {
        return name_en;
    }

    public String getPage() {
        return page;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_ru(String name_ru) {
        this.name_ru = name_ru;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getName() {
        
        if (FacesContext.getCurrentInstance().getViewRoot().getLocale().toString().equals("en")) {
            return name_en;
        } else {
            return name_ru;
        }

    }
}
