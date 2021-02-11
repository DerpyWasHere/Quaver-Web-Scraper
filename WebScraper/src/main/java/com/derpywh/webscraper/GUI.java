/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.derpywh.webscraper;

/**
 *
 * @author DER-PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GUI
{
    Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    JFrame frame = new JFrame("Quaver Profile Scraper");
    JPanel panel = new JPanel(gridBag); 
   
    JTextField textField = new JTextField("Profile Link");
    JTextField textField2 = new JTextField("a test box");
    
    public GUI()
    {   
        
        // Sets content pane and operation when closing the program.
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.add(textField);
        textField.addActionListener(new ProfileFieldListener(textField));
        textField.addFocusListener(new ProfileFieldListener(textField));
        frame.add(textField2);
        textField2.addActionListener(new ProfileFieldListener(textField2));
        textField2.addFocusListener(new ProfileFieldListener(textField2));
        // Packs GUI components, maximizes program to screen, and enables the visibility of the program.
        frame.pack();
        frame.setMinimumSize(new Dimension(1080, 720));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setIconImage(icon.getImage());
        frame.setVisible(true);
        
    }
    
    private class ProfileFieldListener implements ActionListener, FocusListener
    {
        JTextField f;
        public ProfileFieldListener(JTextField field)
        {
            f = field;
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("action happened");
            f.setText("cleared");
        }
        
        @Override
        public void focusGained(FocusEvent e)
        {
            f.setForeground(Color.BLACK);
            System.out.println("Focus gained");
        }
        
        @Override
        public void focusLost(FocusEvent e)
        {
            f.setForeground(new Color(115, 118, 128));
            
            System.out.println("Focus lost");
        }
    }
}
