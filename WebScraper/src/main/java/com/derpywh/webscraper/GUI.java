/*
 * imagine having license headers kekw
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

import java.util.List;
import java.util.Arrays;

public class GUI
{
    Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    private final Color GRAY = new Color(115, 118, 128);
    String[] dataTableColNames = {"Map URL", "Artist", "Song Name", 
                                  "Difficulty Name", "Grade", "Performance Rating",
                                  "Accuracy", "Speed Rate", "Mods", 
                                  "Score", "Scroll Speed", "Max Combo", 
                                  "Ratio"
                                 };
    
    JFrame frame = new JFrame("Quaver Profile Scraper");
    JPanel panel = new JPanel(gridBag); 
   
    JLabel error = new JLabel("Malformed URL, please enter a valid Quaver profile link. (ex. https://quavergame.com/user/12446)");
    JTextField textField = new JTextField("Profile Link", 20);
    //JTextField testField = new JTextField("testy boi", 20);
    
    DefaultTableModel defaultDataTable = new DefaultTableModel();
    JTable dataTable = new JTable(defaultDataTable);
    public GUI()
    {   
        // Sets content pane and operation when closing the program.
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textField.setForeground(GRAY);
        frame.add(textField, c);
        textField.addActionListener(new ProfileFieldListener(textField));
        textField.addFocusListener(new ProfileFieldListener(textField));
        
        //frame.add(testField, c);
        //dataTable.setBounds(30, 40, 200, 300);
        
        frame.add(dataTable, c);
        
        // Packs GUI components, maximizes program to screen, and enables the visibility of the program.
        frame.pack();
        frame.setMinimumSize(new Dimension(1080, 720));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setIconImage(icon.getImage());
        frame.setVisible(true);
        
    }
    
    private class ProfileFieldListener implements ActionListener, FocusListener
    {
        private boolean firstClick = true;
        private final JTextField f;
        public ProfileFieldListener(JTextField field)
        {
            f = field;
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //System.out.println("action happened");
            try
            {  
                frame.remove(error); // why doesnt this throw a nullpointererror
                
                //System.out.println(f.getText());
                WebScraper w = new WebScraper(f.getText());
                String[][] s = w.returnProfileData();
                defaultDataTable.setRowCount(0);
                defaultDataTable = new DefaultTableModel(s, dataTableColNames) 
                {
                    @Override
                    public boolean isCellEditable(int row, int col) 
                    {
                        return false;
                    }
                };
                //for(String[] col : s)
                //{
                    //defaultDataTable.addRow(col);
                    //System.out.println(Arrays.deepToString(col));
                //}
                defaultDataTable.insertRow(0, dataTableColNames);
                //defaultDataTable.addColumn(dataTableColNames);
                //defaultDataTable.setColumnCount(15);
                frame.remove(dataTable);
                dataTable = new JTable(defaultDataTable);
                
                //dataTable.setFocusable(false);
                dataTable.setRowSelectionAllowed(false);
                
                // TEMPORARY SOLUTION - WILL FIND A BETTER ONE LATER
                dataTable.getColumnModel().getColumn(0).setPreferredWidth(300);
                dataTable.getColumnModel().getColumn(1).setPreferredWidth(250);
                dataTable.getColumnModel().getColumn(2).setPreferredWidth(200);
                dataTable.getColumnModel().getColumn(3).setPreferredWidth(200);
                dataTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                dataTable.getColumnModel().getColumn(5).setPreferredWidth(115);
                dataTable.getColumnModel().getColumn(6).setPreferredWidth(75);
                dataTable.getColumnModel().getColumn(7).setPreferredWidth(75);
                dataTable.getColumnModel().getColumn(8).setPreferredWidth(50);
                dataTable.getColumnModel().getColumn(9).setPreferredWidth(75);
                dataTable.getColumnModel().getColumn(10).setPreferredWidth(80);
                dataTable.getColumnModel().getColumn(11).setPreferredWidth(75);
                dataTable.getColumnModel().getColumn(12).setPreferredWidth(50);
                // TEMPORARY SOLUTION - WILL FIND A BETTER ONE LATER
                
                
                dataTable.doLayout();
                frame.add(dataTable);
                dataTable.repaint();
                //System.out.print("Repainted");
                frame.revalidate();
                frame.repaint();
                
                f.setText("");
                //System.out.println("Row Count: " + dataTable.getRowCount() + ". Column Count: " + dataTable.getColumnCount());
            }
            catch(IOException ioException)
            {
                // Do nothing
            }
            catch(IllegalArgumentException malformedURL)
            {
                frame.add(error, c);
                frame.revalidate();
            }
        }
        
        @Override
        public void focusGained(FocusEvent e)
        {
            if(firstClick)
            {
                f.setText("");
                firstClick = false;
            }
            f.setForeground(Color.BLACK);
            System.out.println("Focus gained");
        }
        
        @Override
        public void focusLost(FocusEvent e)
        {
            f.setForeground(GRAY);
            
            System.out.println("Focus lost");
        }
    }
}
