package BattleCity;

import java.awt.*;
import java.io.*;
import javax.swing.*;


public class Lista extends JFrame {
    JTextArea txtarea;
    
    public Lista() throws FileNotFoundException, IOException{
        super("Lista de Vencedores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JTextArea txtarea = new JTextArea();      
        txtarea.setEditable(false);
        
        Font font = new Font("System", Font.BOLD, 20);
        
        txtarea.setFont(font);
        
        try { 
            BufferedReader buffRead  = new BufferedReader(new FileReader("arquivos\\lista.txt"));
            int c ;
            while((c=buffRead.read()) != -1){			
            txtarea.append(String.valueOf((char)c));				
            }
            buffRead.close();
	} catch (Exception e) {
            e.printStackTrace();
	}
		add(txtarea);       
                
		setSize(new Dimension(300,300));
		setVisible(true);
	}
}
