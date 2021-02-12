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
import javax.swing.table.DefaultTableModel;

import java.io.IOException;

import java.util.Arrays;

public class GUI
{
    Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    String[] dataTableColNames = {"Map URL", "Artist", "Song Name", "Difficulty Name", "Grade", "Performance Rating",
                                  "Accuracy", "Speed Rate", "Mods", "Score", "Scroll Speed", "Max Combo", "Ratio"};
    JFrame frame = new JFrame("Quaver Profile Scraper");
    JPanel panel = new JPanel(gridBag); 
   
    JTextField textField = new JTextField("Profile Link", 20);
    
    DefaultTableModel defaultDataTable = new DefaultTableModel();
    JTable dataTable= new JTable(defaultDataTable);
    public GUI()
    {   
        // Sets content pane and operation when closing the program.
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.add(textField);
        textField.addActionListener(new ProfileFieldListener(textField));
        textField.addFocusListener(new ProfileFieldListener(textField));
        
        //dataTable.setBounds(30, 40, 200, 300);
        
        frame.add(dataTable);
        
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
            try
            {  
                System.out.println(f.getText());
                WebScraper w = new WebScraper(f.getText());
                String[][] s = w.returnProfileData();
                defaultDataTable.setRowCount(0);
                defaultDataTable = new DefaultTableModel(s, dataTableColNames);
                for(String[] col : s)
                {
                    //defaultDataTable.addRow(col);
                    System.out.println(Arrays.deepToString(col));
                }
                defaultDataTable.insertRow(0, dataTableColNames);
                //defaultDataTable.addColumn(dataTableColNames);
                //defaultDataTable.setColumnCount(15);
                frame.remove(dataTable);
                dataTable = new JTable(defaultDataTable);
                frame.add(dataTable);
                dataTable.repaint();
                System.out.print("Repainted");
                frame.revalidate();
                frame.repaint();
                System.out.println("Row Count: " + dataTable.getRowCount() + ". Column Count: " + dataTable.getColumnCount());
            }
            catch(IOException error)
            {
                // Do nothing
            }
            f.setText("");
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
