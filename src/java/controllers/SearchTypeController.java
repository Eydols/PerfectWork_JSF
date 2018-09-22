package controllers;

import enums.SearchType;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SearchTypeController {

    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();

    public SearchTypeController() {
        
        searchList.clear();
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("all_firm"), SearchType.ALL_FIRM);
        searchList.put(bundle.getString("perfect_name"), SearchType.PERFECT);
        searchList.put(bundle.getString("ksr_name"), SearchType.KSR);
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
}
