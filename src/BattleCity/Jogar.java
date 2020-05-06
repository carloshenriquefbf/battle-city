package BattleCity;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

public class Jogar extends JFrame implements ActionListener{
    
    ElementosMapa mapafinal[][] = new ElementosMapa[13][13];
    ElementosMapa m[][] = new ElementosMapa[13][13];
    ElementosMapa m2[][] = new ElementosMapa[13][13];
    Tanque eu;
    Tanque inimigo[] = new Tanque[3];    
    String path,pathCima,pathBaixo,pathEsquerda,pathDireita,pathNome;
    JPanel PMapa;
    JButton BMapa[][],Girar,Andar,Atirar,Vidas,Abates,Sair,Fase,Score;
    int PosX, PosY;
    int QuantTanque=3, QuantAbates = 0,QuantVidas = 3,QuantFase = 1,QuantScore = 0,contT=0;
    int PosXT[] = new int[6];
    int PosYT[] = new int[6];
    int PosXTI[] = new int [6];
    int PosYTI[] = new int [6];
    int EscolhaJogador;
    
    public void EscolheMapa(){
        Random aleatorio = new Random();
        int variavel = aleatorio.nextInt(5);
        switch (variavel){
            case 0:
                path= "arquivos\\mapa1.txt";
                        break;
            case 1:
                path= "arquivos\\mapa2.txt";
                        break;
            case 2:  
              path = "arquivos\\mapa3.txt";
                        break;
            case 3:  
              path = "arquivos\\mapa4.txt";              
                        break;
            case 4:  
              path = "arquivos\\mapa5.txt";                
                        break;
        }
    }     
      
    public ElementosMapa[][] GerarMapa() throws FileNotFoundException, IOException{
        this.EscolheMapa();        
        try (BufferedReader buffRead = new BufferedReader(new FileReader(path)) 
        ) {
            String Linha ="";
            while(Linha != null){
                
                String vetor[];
                vetor=Linha.split(" ");
                switch(vetor[0]){
                    case "aco":
                        //parede indestrutivel
                        for(int i=1; i<vetor.length;i++){
                            char vetor2[];
                            vetor2=vetor[i].toCharArray();
                            
                            int y = vetor2[0]-65;  //A = 65 B = 66 C = 67 etc
                            int x;
                            if(vetor2.length>2){
                                x = 10*(vetor2[1]-48) + (vetor2[2]-48);        //0 = 48
                            }
                            else{
                                x = vetor2[1]-48;
                            }
                            mapafinal[x][y] = new ElementosMapa("parede aco");
                        }
                        break;
                    case "aguia":
                        //win condition
                        for(int i=1; i<vetor.length;i++){
                            char vetor2[];
                            vetor2=vetor[i].toCharArray();
                            
                            int y = vetor2[0]-65;  //A = 65 B = 66 C = 67 etc
                            int x;
                            if(vetor2.length>2){
                                x = 10*(vetor2[1]-48) + (vetor2[2]-48);        //0 = 48
                            }
                            else{
                                x = vetor2[1]-48;
                            }
                            
                            mapafinal[x][y] = new ElementosMapa("aguia");
                         }
                        break;
                    }
                Linha=buffRead.readLine();
                
            }
        }
        
        int QuantTanque=0;
        
        if(mapafinal[11][2]==null){        
                inimigo[QuantTanque] = new Tanque("M-1 Abrams",1,1,"Cima",1);
                inimigo[QuantTanque].id = 'X';
                mapafinal[11][2] = inimigo[QuantTanque];   
                QuantTanque++;
           }
             if(mapafinal[11][6]==null){        
                inimigo[QuantTanque] = new Tanque("M-1 Abrams",1,1,"Cima",1);
                inimigo[QuantTanque].id = 'X';
                mapafinal[11][6] = inimigo[QuantTanque];   
                QuantTanque++;
           }
             if(mapafinal[11][10]==null){        
                inimigo[QuantTanque] = new Tanque("M-1 Abrams",1,1,"Cima",1);
                inimigo[QuantTanque].id = 'X';
                mapafinal[11][10] = inimigo[QuantTanque];   
                QuantTanque++;
           }
       
           if(mapafinal[7][6]==null){  
               if(EscolhaJogador==0){
                eu = new Tanque("EE-T1 Osório",0,1,"Baixo",1);
                eu.id='P';               
                mapafinal[7][6] = eu;   }
               else if(EscolhaJogador==1){
                eu = new Tanque("M-1 Abrams",0,1,"Baixo",3);
                eu.id='P';               
                mapafinal[7][6] = eu;  
               }               
                else if(EscolhaJogador==2){
                eu = new Tanque("Centurion",0,1,"Baixo",1);
                eu.id='P';               
                mapafinal[7][6] = eu;
           }
        }
           Random seed = new Random();
           int QuantParede=0;
        do{
           int i = seed.nextInt(13);
           int j = seed.nextInt(13);
           if(mapafinal[i][j]==null){
                mapafinal[i][j]= new ElementosMapa("parede tijolo");
                QuantParede++;
           }
        }while(QuantParede<25);
        
        for(int i = 0;i<13;i++){
            for(int j = 0;j<13;j++){
                if(mapafinal[i][j]==null){
                    mapafinal[i][j]=new ElementosMapa("caminho");
                }
            }
        }return mapafinal;
    }
    
    public Jogar(int n) throws IOException{  
    
    EscolhaJogador=n; // 0 = osório 1 = abrams 2 = centurion
    PMapa = new JPanel(new GridLayout(13,13));   
    BMapa = new JButton[13][13];    
    m2 = this.GerarMapa();
    
    pathNome="T-1 Osório";
    pathCima="imagem\\euCima.png";
    pathBaixo="imagem\\euBaixo.png";
    pathEsquerda="imagem\\euEsquerda.png";
    pathDireita="imagem\\euDireita.png";
    
        switch (n) {
            case 0:
                pathNome="T-1 Osório";
                pathCima="imagem\\euCima.png";
                pathBaixo="imagem\\euBaixo.png";
                pathEsquerda="imagem\\euEsquerda.png";
                pathDireita="imagem\\euDireita.png";
                break;
            case 1:
                pathNome="M-1 Abrams";
                pathCima="imagem\\tanqueCima.png";
                pathBaixo="imagem\\tanqueBaixo.png";
                pathEsquerda="imagem\\tanqueEsquerda.png";
                pathDireita="imagem\\tanqueDireita.png";
                break;
            case 2:
                pathNome="Centurion";
                pathCima="imagem\\inimigoCima.png";
                pathBaixo="imagem\\inimigoBaixo.png";
                pathEsquerda="imagem\\inimigoEsquerda.png";
                pathDireita="imagem\\inimigoDireita.png";
                break;
            default:
                break;
        }
           
        int x=0,y=0;        
        for(int i=0;i<13;i++){
            for(int j = 0; j<13;j++){
               
                switch (mapafinal[i][j].id) {
                    case 'X':
                        BMapa[i][j]= new JButton(new ImageIcon("imagem\\tanqueCima.png")); 
                        BMapa[i][j].setBounds(x,y,60,60);
                        m2[i][j] = new Tanque("M-1 Abrams",1,1,"Cima",1);
                        PosXT[contT]=i;
                        PosYT[contT]=j;
                        PosXTI[contT]=i;
                        PosYTI[contT]=j;
                        contT++;
                        x=x+60;
                        y=y+60;
                        
                        break;
                    case '*':
                        BMapa[i][j]= new JButton(new ImageIcon("imagem\\aguia.png"));
                        BMapa[i][j].setBounds(x,y,60,60);
                        m2[i][j] = new ElementosMapa("aguia");
                        x=x+60;
                        y=y+60;
                        break;
                    case 'P':
                        BMapa[i][j]= new JButton(new ImageIcon(pathCima));
                        BMapa[i][j].setBounds(x,y,60,60);
                        m2[i][j]= new Tanque(pathNome,0,1,"Cima",1);
                        x=x+60;
                        y=y+60;
                        PosX=i;
                        PosY=j;
                        break;
                    case '1':
                        BMapa[i][j]= new JButton(new ImageIcon("imagem\\paredeT.png"));
                        BMapa[i][j].setBounds(x,y,60,60);
                        m2[i][j] = new ElementosMapa("parede tijolo");
                        x=x+60;
                        y=y+60;
                        break;
                    case '#':
                        BMapa[i][j]= new JButton(new ImageIcon("imagem\\paredeA.png"));
                        BMapa[i][j].setBounds(x,y,60,60);
                        m2[i][j] = new ElementosMapa("parede aco");
                        x=x+60;
                        y=y+60;
                        break;
                    case ' ':
                        BMapa[i][j]= new JButton(new ImageIcon("imagem\\caminho.png"));
                        BMapa[i][j].setBounds(x,y,60,60); 
                        m2[i][j] =  new ElementosMapa("caminho");
                        
                        x=x+60;
                        y=y+60;
                        break;
                    default:
                        break;
                }
                PMapa.add(BMapa[i][j]); 
            }
      }
      
      Girar = new JButton("Girar"); 
      Girar.setVisible(true);
      Girar.addActionListener(this);
      
      Andar = new JButton("Andar"); 
      Andar.setVisible(true);
      Andar.addActionListener(this);
      
      Atirar = new JButton("Atirar");        
      Atirar.setVisible(true);
      Atirar.addActionListener(this);
      
      
      Vidas = new JButton("Vidas: " + QuantVidas);
      Vidas.setVisible(true);
      
      Abates = new JButton("Abates: " + QuantAbates);
      Abates.setVisible(true);
      
      Fase = new JButton("Fase: " + QuantFase);
      Fase.setVisible(true);
      
      Score = new JButton("Score: " + QuantScore);
      Score.setVisible(true);
      
      Sair = new JButton("Sair");
      Sair.setVisible(true);
      Sair.addActionListener(this);
      
      
      JPanel PJogo = new JPanel(new BorderLayout()); 
      
      JPanel PBotao = new JPanel();
      
      PBotao.add(Vidas);
      PBotao.add(Abates);
      PBotao.add(Andar);
      PBotao.add(Girar);
      PBotao.add(Atirar);   
      PBotao.add(Score);
      PBotao.add(Fase);
      PBotao.add(Sair);
      
      
      getContentPane().add(PJogo);
      
      PJogo.add(PMapa,BorderLayout.CENTER);
      PJogo.add(PBotao,BorderLayout.SOUTH);         
      
      setSize(700,700);        
      setVisible(true);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }
    
    public void MoverAliado(int newX,int newY,int oldX,int oldY) throws IOException{    
        
        if(newX <=12 && newY<=12 && newX>=0 && newY>=0)
        
        switch (((Tanque)(m2[oldX][oldY])).Direcao){
            
            case "Baixo":
                if(m2[newX][newY].Tipo.equals("caminho")){
                    
                BMapa[newX][newY].setIcon(new ImageIcon(pathBaixo)); 
                m2[newX][newY]=m2[oldX][oldY];
                
                PosX=newX;
                PosY=newY;
                
                BMapa[oldX][oldY].setIcon(new ImageIcon("imagem\\caminho.png"));
                m2[oldX][oldY] = new ElementosMapa("caminho");
                }
                
                break;
            case "Cima":
                if(m2[newX][newY].Tipo.equals("caminho")){
                    
                BMapa[newX][newY].setIcon(new ImageIcon(pathCima));                
                m2[newX][newY]=m2[oldX][oldY];
                
                PosX=newX;
                PosY=newY;
                
                BMapa[oldX][oldY].setIcon(new ImageIcon("imagem\\caminho.png"));
                m2[oldX][oldY] = new ElementosMapa("caminho");
                }
                break;
            case "Direita":
                if(m2[newX][newY].Tipo.equals("caminho")){
                    
                BMapa[newX][newY].setIcon(new ImageIcon(pathDireita));
                m2[newX][newY]=m2[oldX][oldY];
                
                PosX=newX;
                PosY=newY;
                
                BMapa[oldX][oldY].setIcon(new ImageIcon("imagem\\caminho.png"));
                m2[oldX][oldY] = new ElementosMapa("caminho");
                }         
                
                break;
            case "Esquerda":
                if(m2[newX][newY].Tipo.equals("caminho")){
                    
               BMapa[newX][newY].setIcon(new ImageIcon(pathEsquerda));
               m2[newX][newY]=m2[oldX][oldY];
               
                PosX=newX;
                PosY=newY;
                
                BMapa[oldX][oldY].setIcon(new ImageIcon("imagem\\caminho.png"));
                m2[oldX][oldY] = new ElementosMapa("caminho");
                }
               
                break;
            default:
                break;
        }
    }
    
    public void Atira(int TiroX, int TiroY) throws IOException{   
        
        OUTER:
        OUTER_1:
        OUTER_2:
        OUTER_3:
        switch (((Tanque) (m2[TiroX][TiroY])).Direcao) {
            case "Cima":
                if(TiroX > 0 && TiroX-1<=12 && TiroY>0 && TiroY <=12)
                switch (m2[TiroX-1][TiroY].Tipo) {
                    case "parede aco":
                        switch (((Tanque) (m2[TiroX][TiroY])).Nome) {
                            case "Centurion":
                                m2[TiroX-1][TiroY] = new ElementosMapa("caminho");
                                BMapa[TiroX-1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                break;
                            default:                               
                                break OUTER; 
                        }
                        break OUTER;
                    case "parede tijolo":
                        m2[TiroX-1][TiroY] = new ElementosMapa("caminho");
                        BMapa[TiroX-1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                        break OUTER;
                    case "tanque":
                        Renasce(TiroX-1,TiroY);
                        m2[TiroX-1][TiroY] = new ElementosMapa("caminho");
                        BMapa[TiroX-1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                        QuantAbates++;
                        Abates.setText("Abates: " + QuantAbates);
                        QuantScore+=10;
                        Score.setText("Score: " + QuantScore);
                        break OUTER;
                    case "aguia":
                        String vencedor = JOptionPane.showInputDialog("Digite seu nome: ");
                        BufferedWriter bw = new BufferedWriter(new FileWriter("arquivos\\lista.txt", true)); 
                            bw.newLine();  
                            bw.write(vencedor + " " + QuantScore);
                            bw.close();  
                            this.dispose();
                            break;
                    default:
                        break;
                }        
            case "Direita":
                if(TiroX > 0 && TiroX<=12 && TiroY+1>0 && TiroY+1<=12)
                switch (m2[TiroX][TiroY+1].Tipo) {
                    case "parede aco":
                        switch (((Tanque) (m2[TiroX][TiroY])).Nome) {
                            case "Centurion":
                                m2[TiroX][TiroY+1] = new ElementosMapa("caminho");
                                BMapa[TiroX][TiroY+1].setIcon(new ImageIcon("imagem\\caminho.png"));
                                break;
                            default:                               
                                break OUTER_1; 
                        }
                        break OUTER_1;
                    case "parede tijolo":
                        m2[TiroX][TiroY+1] = new ElementosMapa("caminho");
                        BMapa[TiroX][TiroY+1].setIcon(new ImageIcon("imagem\\caminho.png"));
                        break OUTER_1;
                    case "tanque":
                        Renasce(TiroX,TiroY+1);
                        m2[TiroX][TiroY+1] = new ElementosMapa("caminho");
                        BMapa[TiroX][TiroY+1].setIcon(new ImageIcon("imagem\\caminho.png"));
                        QuantAbates++;
                       Abates.setText("Abates: " + QuantAbates);
                        QuantScore+=10;
                        Score.setText("Score: " + QuantScore);
                        break OUTER_1;
                    case "aguia":
                        String vencedor = JOptionPane.showInputDialog("Digite seu nome: ");
                        BufferedWriter bw = new BufferedWriter(new FileWriter("arquivos\\lista.txt", true)); 
                            bw.newLine();  
                            bw.write(vencedor + " " + QuantScore);
                            bw.close(); 
                            this.dispose();
                            break;
                    default:
                        break;
                }
                break;
            case "Baixo":
                if(TiroX+1 > 0 && TiroX+1<=12 && TiroY>0 && TiroY <=12)
                switch (m2[TiroX+1][TiroY].Tipo) {
                    case "parede aco":
                           switch (((Tanque) (m2[TiroX][TiroY])).Nome) {
                            case "Centurion":
                                m2[TiroX+1][TiroY] = new ElementosMapa("caminho");
                                BMapa[TiroX+1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                break;
                            default:                               
                                break OUTER_2; 
                        }
                        break OUTER_2;
                    case "parede tijolo":
                        m2[TiroX+1][TiroY] = new ElementosMapa("caminho");
                        BMapa[TiroX+1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                        break OUTER_2;
                    case "tanque":
                        Renasce(TiroX+1,TiroY);
                        m2[TiroX+1][TiroY] = new ElementosMapa("caminho");
                        BMapa[TiroX+1][TiroY].setIcon(new ImageIcon("imagem\\caminho.png"));
                        QuantAbates++;
                        Abates.setText("Abates: " + QuantAbates);
                        QuantScore+=10;
                        Score.setText("Score: " + QuantScore);
                        break OUTER_2;
                    case "aguia":
                        String vencedor = JOptionPane.showInputDialog("Digite seu nome: ");
                        BufferedWriter bw = new BufferedWriter(new FileWriter("arquivos\\lista.txt", true)); 
                            bw.newLine();  
                            bw.write(vencedor + " " + QuantScore);
                            bw.close();      
                            this.dispose();
                            break;
                    default:
                        break;
                }
                break;
            case "Esquerda":
                if(TiroX > 0 && TiroX<=12 && TiroY-1>0 && TiroY-1 <=12)
                switch (m2[TiroX][TiroY-1].Tipo) {
                    case "parede aco":    switch (((Tanque) (m2[TiroX][TiroY])).Nome) {
                            case "Centurion":
                                m2[TiroX][TiroY-1] = new ElementosMapa("caminho");
                                BMapa[TiroX][TiroY-1].setIcon(new ImageIcon("imagem\\caminho.png"));
                                break;
                            default:                               
                                break OUTER_3; 
                        }
                        break OUTER_3;
                    case "parede tijolo":
                        m2[TiroX][TiroY-1] = new ElementosMapa("caminho");
                        BMapa[TiroX][TiroY-1].setIcon(new ImageIcon("imagem\\caminho.png"));
                        break OUTER_3;
                    case "tanque":
                        Renasce(TiroX,TiroY-1);
                        m2[TiroX][TiroY-1] = new ElementosMapa("caminho");
                        BMapa[TiroX][TiroY-1].setIcon(new ImageIcon("imagem\\caminho.png"));
                        QuantAbates++;
                        Abates.setText("Abates: " + QuantAbates);
                        QuantScore+=10;
                        Score.setText("Score: " + QuantScore);
                        break OUTER_3;
                    case "aguia":
                        String vencedor = JOptionPane.showInputDialog("Digite seu nome: ");
                        BufferedWriter bw = new BufferedWriter(new FileWriter("arquivos\\lista.txt", true)); 
                            bw.newLine();  
                            bw.write(vencedor + " " + QuantScore);
                            bw.close();  
                            this.dispose();
                            break;    
                    default:
                        break;
                }
            break;
            default:
                break;
        }
    }
    
    public void TurnoInimigo(int tX, int tY,int cont) throws IOException{ 
        
        Random aleatorio2 = new Random();
        int variavel2 = aleatorio2.nextInt(4); //0 = cima   1 = baixo   2 = direita    3 = esquerda     4=atirar
        OUTER:
        
        switch (((Tanque) (m2[tX][tY])).Nome) {
            case "M-1 Abrams":
                switch (variavel2) {
                    case 0:
                        if(tX-1>0){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Cima") && m2[tX-1][tY].Tipo.equals("caminho")){
                                BMapa[tX-1][tY].setIcon(new ImageIcon("imagem\\tanqueCima.png"));
                                m2[tX-1][tY] = m2[tX][tY];
                                
                                PosXT[cont]=tX-1;
                                PosYT[cont]=tY;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Cima";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\tanqueCima.png"));
                            }
                        }
                        break;
                    case 1:
                        if(tX+1<12){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Baixo") && m2[tX+1][tY].Tipo.equals("caminho")){
                                BMapa[tX+1][tY].setIcon(new ImageIcon("imagem\\tanqueBaixo.png"));
                                m2[tX+1][tY] = m2[tX][tY];
                                
                                PosXT[cont]=tX+1;
                                PosYT[cont]=tY;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Baixo";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\tanqueBaixo.png"));
                            }
                        }
                        break;
                    case 2:
                        if(tY+1<12){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Direita") && m2[tX][tY+1].Tipo.equals("caminho")){
                                BMapa[tX][tY+1].setIcon(new ImageIcon("imagem\\tanqueDireita.png"));
                                m2[tX][tY+1] = m2[tX][tY];
                                
                                PosXT[cont]=tX;
                                PosYT[cont]=tY+1;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Direita";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\tanqueDireita.png"));
                                
                            }
                        }
                        
                        break;
                    case 3:
                        if(tY-1>0){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Esquerda") && m2[tX][tY-1].Tipo.equals("caminho")){
                                BMapa[tX][tY-1].setIcon(new ImageIcon("imagem\\tanqueEsquerda.png"));
                                m2[tX][tY-1] = m2[tX][tY];
                                
                                PosXT[cont]=tX;
                                PosYT[cont]=tY-1;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Esquerda";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\tanqueEsquerda.png"));
                            }
                        }
                        break;
                    case 4:
                        switch (((Tanque) (m2[tX][tY])).Direcao) {
                            case "Cima":
                                
                                Atira(tX-1,tY);
                                break OUTER;
                            case "Direita":
                                
                                Atira(tX,tY+1);
                                break OUTER;
                            case "Baixo":
                                
                                Atira(tX+1,tY);
                                break OUTER;
                            case "Esquerda":
                                
                                Atira(tX,tY-1);
                                break OUTER;
                            default:
                                break;
                        } 
                        break;
                    default:
                        break;
                }       break;
            case "Centurion":
                switch (variavel2) {
                    case 0:
                        if(tX-1>0){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Cima") && m2[tX-1][tY].Tipo.equals("caminho")){
                                BMapa[tX-1][tY].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
                                m2[tX-1][tY] = m2[tX][tY];
                                
                                PosXT[cont]=tX-1;
                                PosYT[cont]=tY;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Cima";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
                            }
                        }
                        break;
                    case 1:
                        if(tX+1<12){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Baixo") && m2[tX+1][tY].Tipo.equals("caminho")){
                                BMapa[tX+1][tY].setIcon(new ImageIcon("imagem\\inimigoBaixo.png"));
                                m2[tX+1][tY] = m2[tX][tY];
                                
                                PosXT[cont]=tX+1;
                                PosYT[cont]=tY;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Baixo";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\inimigoBaixo.png"));
                            }
                        }
                        break;
                    case 2:
                        if(tY+1<12){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Direita") && m2[tX][tY+1].Tipo.equals("caminho")){
                                BMapa[tX][tY+1].setIcon(new ImageIcon("imagem\\inimigoDireita.png"));
                                m2[tX][tY+1] = m2[tX][tY];
                                
                                PosXT[cont]=tX;
                                PosYT[cont]=tY+1;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Direita";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\inimigoDireita.png"));
                                
                            }
                        }
                        
                        break;
                    case 3:
                        if(tY-1>0){
                            if(((Tanque) (m2[tX][tY])).Direcao.equals("Esquerda") && m2[tX][tY-1].Tipo.equals("caminho")){
                                BMapa[tX][tY-1].setIcon(new ImageIcon("imagem\\inimigoEsquerda.png"));
                                m2[tX][tY-1] = m2[tX][tY];
                                
                                PosXT[cont]=tX;
                                PosYT[cont]=tY-1;
                                
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\caminho.png"));
                                m2[tX][tY] = new ElementosMapa("caminho");
                            }
                            else{
                                ((Tanque)(m2[tX][tY])).Direcao="Esquerda";
                                BMapa[tX][tY].setIcon(new ImageIcon("imagem\\inimigoEsquerda.png"));
                            }
                        }
                        break;
                    case 4:
                        switch (((Tanque) (m2[tX][tY])).Direcao) {
                            case "Cima":
                                
                                Atira(tX-1,tY);
                                break OUTER;
                            case "Direita":
                                
                                Atira(tX,tY+1);
                                break OUTER;
                            case "Baixo":
                                
                                Atira(tX+1,tY);
                                break OUTER;
                            case "Esquerda":
                                
                                Atira(tX,tY-1);
                                break OUTER;
                            default:
                                break;
                        } 
                        break;
                    default:
                        break;
                }   break;
            default:                
                break;
        }
    }
    
    public void Renasce(int PosXMorte,int PosYMorte){
        
    Random aleatorio3 = new Random();
    int novaPosXT = aleatorio3.nextInt(10 + 1 - 2) + 2;
    int novaPosYT = aleatorio3.nextInt(10 + 1 - 2) + 2;
    
    if(!m2[novaPosXT][novaPosYT].Tipo.equals("caminho")){
        novaPosXT = aleatorio3.nextInt(10 + 1 - 2) + 2;
        novaPosYT = aleatorio3.nextInt(10 + 1 - 2) + 2;
    }
    
    for(int i=0;i<contT;i++){        
        if(PosXMorte==PosXT[i] && PosYMorte==PosYT[i] ){
           if(m2[PosXTI[i]][PosYTI[i]].Tipo.equals("caminho")) {
               novaPosXT=PosXTI[i];
               novaPosYT=PosYTI[i];
           }
        }
    }
    
    for(int i=0;i<contT;i++){
        if(PosXMorte==PosXT[i] && PosYMorte==PosYT[i] ){
            PosXT[i]=novaPosXT;
            PosYT[i]=novaPosYT;
        }            
    }
    switch(((Tanque) (m2[PosXMorte][PosYMorte])).Nome){
        case "M-1 Abrams":
            m2[novaPosXT][novaPosYT]=m2[PosXMorte][PosYMorte];
            BMapa[novaPosXT][novaPosYT].setIcon(new ImageIcon("imagem\\tanqueCima.png"));
        break;
        case "Centurion":
            m2[novaPosXT][novaPosYT]=m2[PosXMorte][PosYMorte];
            BMapa[novaPosXT][novaPosYT].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
            break;
        default:
            break;
        
    }
        
    }
        
    public void NovaFase() throws IOException{
        if(QuantAbates>9 && QuantFase==1){
            dispose();
            Jogar Mapa2 = new Jogar(EscolhaJogador);
            
            Mapa2.QuantTanque++;
            
            Mapa2.BMapa[9][10].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
            Mapa2.m2[9][10] = new Tanque("Centurion",1,1,"Cima",1);
            
            Mapa2.PosXT[contT]=9;
            Mapa2.PosYT[contT]=10;
            Mapa2.PosXTI[contT]=9;
            Mapa2.PosYTI[contT]=10;
            Mapa2.contT++;
                        
            Mapa2.QuantScore=90*QuantVidas;
            Mapa2.QuantFase=2;
            Mapa2.QuantAbates=9;
            Mapa2.Abates.setText("Abates: " + Mapa2.QuantAbates);
            Mapa2.Score.setText("Score : " + Mapa2.QuantScore);
            Mapa2.Fase.setText("Fase: " + Mapa2.QuantFase);
            
        }
        else if(QuantAbates>25 && QuantFase==2){
            dispose();
            Jogar Mapa3 = new Jogar(EscolhaJogador);
                   
            Mapa3.BMapa[0][0].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
            Mapa3.m2[0][0] = new Tanque("Centurion",1,1,"Cima",1);
            
            Mapa3.PosXT[contT]=0;
            Mapa3.PosYT[contT]=0;
            Mapa3.PosXTI[contT]=0;
            Mapa3.PosYTI[contT]=0;
            Mapa3.contT++;
            
            Mapa3.BMapa[12][12].setIcon(new ImageIcon("imagem\\inimigoCima.png"));
            Mapa3.m2[12][12] = new Tanque("Centurion",1,1,"Cima",1);
            
            Mapa3.PosXT[contT]=12;
            Mapa3.PosYT[contT]=12;
            Mapa3.PosXTI[contT]=12;
            Mapa3.PosYTI[contT]=12;
            Mapa3.contT++;            
           
            Mapa3.QuantScore=250*QuantVidas;
            Mapa3.QuantFase=3;
            Mapa3.QuantAbates=25;
            Mapa3.Abates.setText("Abates: " + Mapa3.QuantAbates);
            Mapa3.Score.setText("Score : " + Mapa3.QuantScore);
            Mapa3.Fase.setText("Fase: " + Mapa3.QuantFase);
        }
        else if(QuantAbates>50 && QuantFase==3){
            QuantScore*=QuantAbates*QuantVidas;
            String vencedor = JOptionPane.showInputDialog("Digite seu nome: ");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("arquivos\\lista.txt", true))) {
                bw.newLine();
                bw.write(vencedor + " " + QuantScore);
            }  
        this.dispose();                            
        }
    }
      
    @Override
    
    public void actionPerformed(ActionEvent e){
            
            Object object = e.getSource();
            
            if(object==Girar){
                switch (((Tanque)(m2[PosX][PosY])).Direcao) {
                    case "Cima":
                        ((Tanque)(m2[PosX][PosY])).Direcao="Direita";
                        BMapa[PosX][PosY].setIcon(new ImageIcon(pathDireita));
                        break;
                    case "Direita":
                        ((Tanque)(m2[PosX][PosY])).Direcao="Baixo";
                        BMapa[PosX][PosY].setIcon(new ImageIcon(pathBaixo));
                        break;
                    case "Baixo":
                        ((Tanque)(m2[PosX][PosY])).Direcao="Esquerda";
                        BMapa[PosX][PosY].setIcon(new ImageIcon(pathEsquerda));
                        break;
                    case "Esquerda":
                        ((Tanque)(m2[PosX][PosY])).Direcao="Cima";
                        BMapa[PosX][PosY].setIcon(new ImageIcon(pathCima));
                        break;
                    default:
                        break;
                }
            }
                
            if(object==Andar){
                switch (((Tanque) (m2[PosX][PosY])).Direcao) {
                    case "Cima":
                        try {
                            MoverAliado(PosX-1,PosY,PosX,PosY);
                        } catch (IOException ex) {
                            Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                       for(int i=0;i<contT;i++){
                    try {
                        TurnoInimigo(PosXT[i],PosYT[i],i);
                    } catch (IOException ex) {
                        Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            
                        }
                                break;
                    case "Direita":
                        try {
                            MoverAliado(PosX,PosY+1,PosX,PosY);
                        } catch (IOException ex) {
                            Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for(int i=0;i<contT;i++){
                    try {
                        TurnoInimigo(PosXT[i],PosYT[i],i);
                    } catch (IOException ex) {
                        Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                           
                        }
                        break;
                    case "Baixo":
                        try {
                            MoverAliado(PosX+1,PosY,PosX,PosY);
                        } catch (IOException ex) {
                            Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for(int i=0;i<contT;i++){
                    try {
                        TurnoInimigo(PosXT[i],PosYT[i],i);
                    } catch (IOException ex) {
                        Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            
                        }
                        break;
                    case "Esquerda":
                        try {
                            MoverAliado(PosX,PosY-1,PosX,PosY);
                        } catch (IOException ex) {
                            Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       for(int i=0;i<contT;i++){
                    try {                            
                        TurnoInimigo(PosXT[i],PosYT[i],i);
                    } catch (IOException ex) {
                        Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        }
                        break;
                    default:
                        break;
                }
            }
            
            if(object==Atirar){
                
                try {
                    Atira(PosX,PosY);
                } catch (IOException ex) {
                    Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for(int i=0;i<contT;i++){                   
                    try {                            
                        TurnoInimigo(PosXT[i],PosYT[i],i);
                    } catch (IOException ex) {
                        Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    NovaFase();
                } catch (IOException ex) {
                    Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
            
            if(object==Sair){
            this.dispose();
}
    }
        
}
        
    

