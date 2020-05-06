package BattleCity;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {  
  
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        class Menu extends JFrame implements ActionListener{
        JLabel fundo;
        JButton botaoJogar, botaoDebug, botaoLista,botaoSair;
            public Menu(){
                
                super("Battle City");
                setLayout(null);
                setSize(800,600);
                setResizable(false);
                fundo = new JLabel(new ImageIcon("Imagem\\Background.png"));
                fundo.setBounds(0,0,800,600);
                
                botaoJogar = new JButton ("Jogar");                
                botaoJogar.addActionListener(this);                        
                        
                botaoDebug = new JButton ("Debug");
                botaoDebug.addActionListener(this); 
                
                botaoLista = new JButton ("Lista");
                botaoLista.addActionListener(this); 
                
                botaoSair  = new JButton ("Sair");
                botaoSair.addActionListener(this);                
                        
                botaoJogar.setBounds(350,300,100,50);
                botaoDebug.setBounds(350,360,100,50);
                botaoLista.setBounds(350,420,100,50);
                botaoSair.setBounds(350,480,100,50);
                
                fundo.add(botaoJogar);
                fundo.add(botaoDebug);
                fundo.add(botaoLista);
                fundo.add(botaoSair);
                
                getContentPane().add(fundo);

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                }
        @Override
        public void actionPerformed(ActionEvent e){
        Object object = e.getSource();
        
            if (object==botaoJogar){
            dispose();    
            
            Object[] tanques = {"Osório", "Abrams", "Centurion"};

            int n = JOptionPane.showOptionDialog(null,"Qual tanque você deseja ?", "Escolha um tanque",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,tanques,tanques[0]);
            
            try {
                Jogar Teste = new Jogar(n);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
            else if(object==botaoDebug){
              dispose();    
            
            Object[] tanques = {"Osório", "Abrams", "Centurion"};

            int n = JOptionPane.showOptionDialog(null,"Qual tanque você deseja ?", "Escolha um tanque",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,tanques,tanques[0]);
            
            try {
                Debug Teste = new Debug(n);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            }
            
            else if(object==botaoLista){            
           
            try {
                Lista l = new Lista();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
          
            else if(object==botaoSair){
             dispose();
            } 
        
        }    
    
    }
        Menu m = new Menu();
 }
}



    
