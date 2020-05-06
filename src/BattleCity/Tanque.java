package BattleCity;

public class Tanque extends ElementosMapa {
    String Nome;
    int Aliado; // 0 = jogador 1 = inimigo
    int VidaTanque;
    String Direcao;
    int Alcance;
    
    public Tanque(String pNome, int pAliado, int pVidaTanque, String Dir, int pAlcance){
        super("tanque");
        pAliado = Aliado;        
        Direcao = Dir;
        Alcance = pAlcance;
        Nome = pNome;
    }
}
