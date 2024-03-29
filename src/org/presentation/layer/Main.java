package org.presentation.layer;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.model.layer.Film;
import org.business.layer.ServiceBusiness;
import org.model.layer.Film;
import org.model.layer.ServiceModel;
import org.persistent.layer.ServicePersistent;
import org.persistent.layer.XMLParser;
import org.presentation.layer.ServicePresentation;

import java.awt.HeadlessException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

@SuppressWarnings({ "unused", "serial" })
public class Main extends JFrame{

	private static ArrayList<Film> filmiO;
	private static String xmlFile = "./Filmi.xml";
	private static Main frame;
	private JPanel aPanel;
	private JTabbedPane tabbedPane;
	private JTable someTable;

	public Main(String title) throws HeadlessException {
		super(title);
		aPanel = setupPanel();
	}
	
	private JPanel setupPanel()
	{
		XMLParser ps = new XMLParser();
		filmiO = ps.readXML(xmlFile);
		filmiO = ServiceModel.sort(filmiO);
		
		JPanel panel = new JPanel(new GridLayout(2,1));
		someTable = new JTable(null);
		add(new JScrollPane(someTable));
		tabbedPane = new JTabbedPane();
		
		final ServicePresentation sp = new ServicePresentation();
		
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
						JOptionPane.showMessageDialog(null, "Error: Main.setupPanel.");
					}
					
			        if(uri != null) {sp.open(uri);}
			        
			      }
			    });
		
		int rowCount = table.getRowCount();
		
		if(rowCount > 1)
		{	
			ServicePopup pm = new ServicePopup(table,xmlFile,this);
			table.setRowSelectionInterval(0, 0);
	        table.setComponentPopupMenu(pm.getJPopupMenu());
		}    
		
		JScrollPane scrollPane = new JScrollPane(table);
		tabbedPane.addTab("Filmi",scrollPane);
		
		JPanel p = new JPanel(new GridLayout(2, 4, 5, 5));
		
		ServiceButton b = new ServiceButton();
		
        JButton btn = b.searchMovieXMLButton(xmlFile,this,filmiO);
        p.add(btn);
       
        btn = b.addMovieXMLButton(xmlFile,this);
        p.add(btn); 
        
        btn = b.editMovieXMLButton(xmlFile,this);
        p.add(btn); 
        
        btn = b.removeNameFromDiscXMLButton(xmlFile,this);
        p.add(btn); 
       
        btn = b.updateDiscXMLButton(xmlFile,this);
        p.add(btn);
        
        btn = b.removeDiscXMLButton(xmlFile,this);
        p.add(btn);
        
        btn = b.createXMLButton(xmlFile,this);
        p.add(btn);
        
        btn = b.reloadDiscXMLButton(xmlFile,this);
        p.add(btn);
       
		tabbedPane.addTab("Akcije", p);

        Author a = new Author();
		tabbedPane.addTab("Domov", a.getPanel());
		
		add(tabbedPane);
	
		return panel;
	}
		
	public void turnOnWindow()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(1200,800);	
	}
	
	public static void main(String[] args) throws IOException, Exception {

		frame = new Main("Videoteka");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.turnOnWindow();	
	
	}
}
