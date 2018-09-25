package beans;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocaleChanger implements Serializable {

private Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public LocaleChanger() {
    }
    
    public void changeLocale(String localeCode) {
    currentLocale = new Locale(localeCode);
    }
    
    public Locale getCurrentLocale() {
    return currentLocale;
    }
    
    
}
