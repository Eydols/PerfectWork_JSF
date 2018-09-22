package enums;

public enum SearchType {
  
    ALL_FIRM(""), 
    PERFECT ("Перфектподряд"), 
    KSR ("КлассикСтройРемонт");
    
    private String firm_name;
    
    SearchType (String f) {
    firm_name = f;
    }
    
    public String getFirmName() {
    return firm_name;
    }
}

   



