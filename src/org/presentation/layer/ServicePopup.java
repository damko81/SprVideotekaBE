package org.presentation.layer;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.model.layer.Film;
import org.persistent.layer.ServicePersistent;
import org.persistent.layer.XMLParser;

@SuppressWarnings("serial")
public class ServicePopup extends JPanel {
	
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemShow;
	private JMenuItem menuItemRemoveName;
	private JMenuItem menuItemRemoveNameFromDisc;
	private JMenuItem menuItemRemoveDisc;
	
    public ServicePopup(final JTable table,final String xmlFile,final Main frame) {
    	
    	popupMenu = new JPopupMenu(); 
    	
    	menuItemShow = new JMenuItem("Podroben pogled");
        menuItemEdit = new JMenuItem("Popravi filem");
        menuItemRemoveName = new JMenuItem("Zbriši podfilem");
        menuItemRemoveNameFromDisc = new JMenuItem("Zbriši filem z podfilmi");
        menuItemRemoveDisc = new JMenuItem("Zbriši disk z filmi");
        
        menuItemEdit.addActionListener(new ActionListener() {
	       	 
	           public void actionPerformed(ActionEvent e)
	           {
			        ServicePersistent sp = new ServicePersistent();
			        ServiceFrame s = new ServiceFrame();
		
			        String disc = table.getValueAt(table.getSelectedRow(), 0).toString();
				    String nameFromDisc = table.getValueAt(table.getSelectedRow(), 1).toString();
				    String name = table.getValueAt(table.getSelectedRow(), 2).toString();
				    
				    Film f = sp.getObjectName(xmlFile, nameFromDisc, name, disc);
				    frame.setVisible(false);
				    s.editMovieXMLFrame(xmlFile,f);
	           }   
	       });   
        menuItemShow.addActionListener(new ActionListener() {
	       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	    ServicePersistent sp = new ServicePersistent();
	        	    ServiceFrame s = new ServiceFrame();
	        	   
	        	    String disc = table.getValueAt(table.getSelectedRow(), 0).toString();
				    String nameFromDisc = table.getValueAt(table.getSelectedRow(), 1).toString();
				    String name = table.getValueAt(table.getSelectedRow(), 2).toString();
				    
				    Film f = sp.getObjectName(xmlFile, nameFromDisc, name, disc);
				    
				    s.showMovieXMLFrame(xmlFile,f);
	           }   
	       }); 
       menuItemRemoveName.addActionListener(new ActionListener() {
	       	 
	           public void actionPerformed(ActionEvent e)
	           {	     
	        	   int[] selectedRows = table.getSelectedRows();
	        	   int selectedRow;
	        	  
	        	   String disc = null;
				   String nameFromDisc = null;
				   String name = null;
				   
				   int res = JOptionPane.showConfirmDialog(null, "Brisanje podfilma/ov ?");
	
				   if(res == 0)
				   {
					   frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					   
					   XMLParser p = new XMLParser();
					   DefaultTableModel model = (DefaultTableModel)table.getModel();

					   int i = 0;
					   while(i < selectedRows.length)
					   {
						   selectedRow = selectedRows[i] - i;
						   
						   disc = table.getValueAt(selectedRow, 0).toString();
						   nameFromDisc = table.getValueAt(selectedRow, 1).toString();
						   name = table.getValueAt(selectedRow, 2).toString();
						   
						   model.removeRow(selectedRow);
						   
			        	   p.removeNameXML(xmlFile, disc, nameFromDisc, name);
						  
			        	   ++i;
					   }
					   frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				   } 
				   
	           }   
	       }); 
       menuItemRemoveNameFromDisc.addActionListener(new ActionListener() {
	       	 
           public void actionPerformed(ActionEvent e)
           {	
        	   int[] selectedRows = table.getSelectedRows();
        	   int selectedRow = table.getSelectedRow();
        	   
        	   String disc = null;
			   String nameFromDisc = null;
			   
			   int res = JOptionPane.showConfirmDialog(null, "Brisanje filma/ov z podfilmi ?");

			   if(res == 0)
			   {
				   frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				   
				   XMLParser p = new XMLParser();
				   DefaultTableModel model = (DefaultTableModel)table.getModel();
				   
				   int i = 0;
				   while(i < selectedRows.length)
				   {
					   selectedRow = selectedRows[i] - i;
					   
					   disc = table.getValueAt(selectedRow, 0).toString();
					   nameFromDisc = table.getValueAt(selectedRow, 1).toString();
		        	   
					   model.removeRow(selectedRow);
					      
	                   p.removeNameFromDiscXML(xmlFile, disc, nameFromDisc);
	                   
	                   ++i;
				   }
				   frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			   } 
			   
           }   
       });
       menuItemRemoveDisc.addActionListener(new ActionListener() {
	       	 
           public void actionPerformed(ActionEvent e)
           {	  
        	   int selectedRow = 0;
        	   
        	   String disc = table.getValueAt(table.getSelectedRow(), 0).toString();
			  
			   int res = JOptionPane.showConfirmDialog(null, "Brisanje diska " + disc  + " z filmi?");

			   if(res == 0)
			   {
				   frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				   
				   XMLParser p = new XMLParser();
				   DefaultTableModel model = (DefaultTableModel)table.getModel();
				   
				   String discTemp = disc;

	        	   while(selectedRow < table.getRowCount() && discTemp.equals(disc))
	        	   {
	        		   discTemp = table.getValueAt(selectedRow, 0).toString();
	        		   
	        		   if(discTemp.equals(disc))
	        		   {   
	        		       model.removeRow(selectedRow);
	        		       --selectedRow;
	        		       
	        		   }
	        		   
	        		   ++selectedRow;
	        	   }
	        	   
                   p.removeDiscXML(xmlFile, disc);
                   frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			   } 
			   
           }   
       });
    
       popupMenu.add(menuItemShow);
       popupMenu.add(menuItemEdit);
       popupMenu.add(menuItemRemoveName);
       popupMenu.add(menuItemRemoveNameFromDisc);
       popupMenu.add(menuItemRemoveDisc);

       addMouseListener(new MousePopupListener());
    }
    
    public JPopupMenu getJPopupMenu(){
    	return this.popupMenu;
    }
    
    class MousePopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) { checkPopup(e); }
        public void mouseClicked(MouseEvent e) { checkPopup(e); }
        public void mouseReleased(MouseEvent e) { checkPopup(e); }

        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
            	popupMenu.show(ServicePopup.this, e.getX(), e.getY());
            }
        }
    }
	
}
