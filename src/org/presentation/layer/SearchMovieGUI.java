package org.presentation.layer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.model.layer.Film;
import org.persistent.layer.ServicePersistent;
import org.presentation.layer.ServicePresentation;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class SearchMovieGUI extends JFrame {

	private ArrayList<Film> filmiO;
	private JTabbedPane tabbedPane;
	private JTable someTable;
	private String xmlFile; 
	
	public SearchMovieGUI(String title,ArrayList<Film> filmiO,String xmlFile) throws HeadlessException {
		super(title);
		this.filmiO = filmiO;
		this.xmlFile = xmlFile;
		setupPanel();
	}
	
	private JPanel setupPanel()
	{
		
		JPanel panel = new JPanel(new GridLayout(2,1));
		someTable = new JTable(null);
		add(new JScrollPane(someTable));
		tabbedPane = new JTabbedPane();
	
		ServicePresentation sp = new ServicePresentation();
		
		final JTable table = sp.getJTableMovies(filmiO);
		table.setAutoCreateRowSorter(true);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {

			        int row = table.getSelectedRow();
			        int col = table.getSelectedColumn();
			        
			        String colName =  table.getColumnName(col);
			        String colValue = table.getValueAt(row, col).toString();

			        URI uri = null;
			        Film fl = new Film();
					try {
						
							ServicePersistent sp = new ServicePersistent();
							ServiceFrame s = new ServiceFrame();
							
							if(colName.equals(fl.getColumnName("url")) && colValue != null && !colValue.equals("")) {uri = new URI(colValue);}
							if(!colName.equals(fl.getColumnName("disc")) && !colName.equals(fl.getColumnName("nameFromDisc")) && !colName.equals(fl.getColumnName("url"))) {
								
							    String disc = table.getValueAt(table.getSelectedRow(), 0).toString();
							    String nameFromDisc = table.getValueAt(table.getSelectedRow(), 1).toString();
							    String name = table.getValueAt(table.getSelectedRow(), 2).toString();
							    
							    Film f = sp.getObjectName(xmlFile, nameFromDisc, name, disc);
							    
							    s.showMovieXMLFrame(xmlFile,f);
								
							}
						
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: SearchMovieGUI.setupPanel.");
					}

					 if(uri != null) {open(uri);}
					 
			      }
			    });
			
		JScrollPane scrollPane = new JScrollPane(table);
		tabbedPane.addTab("Filmi",scrollPane);
	
		add(tabbedPane);
	
		return panel;
	}
	
	private static void open(URI uri) {
		  if (Desktop.isDesktopSupported()) {
		    try {
		       Desktop.getDesktop().browse(uri);
		      } catch (IOException e) { JOptionPane.showMessageDialog(null, "Error: SearchMovieGUI.open."); }
		   } else { /* TODO: error handling */ }
	}
	
	public void turnOnWindow()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(1200,800);	
	}
	
}
