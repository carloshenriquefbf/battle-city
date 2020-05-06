package BattleCity;

public class ElementosMapa {
     
    String Tipo;
    char id;
    public ElementosMapa(String pTipo){
        Tipo=pTipo;
        
        switch(Tipo){
            case "parede aco":
            id = '#';    
                break;
            case "parede tijolo":
            id = '1';   
                break;
            case "aguia":
                id = '*';
                break;
            case "caminho":
                id = ' ';
                break;    
            }            
    }
}
