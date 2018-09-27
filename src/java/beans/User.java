package beans;



import controllers.ManListController;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class User {

    private String username;
    private String password;

    public User() {
    }

    public String login() {
        
        try {
    ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(username, password);
    return "man";
        } catch (ServletException ex) {
        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage message = new FacesMessage(bundle.getString("login_password_error"));
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("login_form", message);
        }
        return "index";
    }
    
    public String logout() { // отрабатывает при нажатии на кнопку Выход, перенаправляет на страницу index.xhtml и уничтожает текущую сессию
    String result = "/index.xhtml?faces-redirect=true";
    
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    
    try {
    request.logout();
    } catch (ServletException e) {
    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
    }
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    
    return result;
    }
    
    //<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
//</editor-fold>
}
