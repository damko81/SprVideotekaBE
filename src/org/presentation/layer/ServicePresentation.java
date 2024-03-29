package org.presentation.layer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.model.layer.Film;
import org.model.layer.ServiceModel;

public class ServicePresentation {

	// Zastavica prikaza autocomplet seznama filmov v celici.
	final private boolean noShowAllChildMovie;	
	public static String nameFromDiscTemp = "";
	
	public ServicePresentation(boolean noShowAllChildMovie){
		this.noShowAllChildMovie = noShowAllChildMovie;
	}
	public ServicePresentation(){
		this.noShowAllChildMovie = true; //Default konstruktor ne prikazuje razširjenega seznama podfilmov.
	}
	
	@SuppressWarnings("serial")
	public JTable getJTableMovies(ArrayList<Film> filmiO){
		
		if(this.noShowAllChildMovie){filmiO = ServiceModel.ReduceMovieList(filmiO);}
		
		final String[] columnNames = Film.getColumnNames();
		
		ArrayList<ArrayList<String>> dataAL = new ArrayList<ArrayList<String>>();
		ArrayList<String> attribAL = null;
				
		for(Film f : filmiO)
		{	
			attribAL = new ArrayList<String>();
			
			attribAL.add(f.getDisc());
			attribAL.add(f.getNameFromDisc());
			attribAL.add(f.getName());
			attribAL.add(f.getGenre());
			attribAL.add(f.getRating());
			attribAL.add(f.getDirector());
			attribAL.add(f.getStars());
			attribAL.add(f.getDuration());
			attribAL.add(f.getReleaseDate());
			attribAL.add(f.getDescription());
			attribAL.add(f.getUrl());
			attribAL.add(f.getImageSrc()); // Trenutno je slikca v celici nesmiselna. Mogoèe nekoè...
			
			dataAL.add(attribAL);	
		}			
		
		Object[][] data = new Object[dataAL.size()][11];
		
		int i = 0;
		while(i < dataAL.size()){
			
			attribAL = dataAL.get(i);
			
			data[i][0] = attribAL.get(0); // Disc
			data[i][1] = attribAL.get(1); // NameFromDisc
			data[i][2] = attribAL.get(2); // Name
			data[i][3] = attribAL.get(3); // Genre
			data[i][4] = attribAL.get(4); // Rating
			data[i][5] = attribAL.get(5); // Director
			data[i][6] = attribAL.get(6); // Stars
			data[i][7] = attribAL.get(7); // Duration
			data[i][8] = attribAL.get(8); // ReleaseDate
			data[i][9] = attribAL.get(9); // Description
			data[i][10] = attribAL.get(10); // Url
			//data[i][11] = attribAL.get(11); // ImageSrc
			
			++i;
		}
	
		Film fl = new Film();
		
		FontUIResource font = new FontUIResource("Italic", Font.ITALIC, 12);
		
		final DefaultTableModel model = new DefaultTableModel(data, columnNames);
		final JTable table = new JTable(model);
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("disc"))).setCellRenderer(this.getCellRenderer(new Font("", Font.BOLD, 14)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("disc"))).setPreferredWidth(5);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("nameFromDisc"))).setPreferredWidth(110);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("name"))).setCellRenderer(this.getCellRenderer(new Font("Italic", Font.BOLD, 13)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("name"))).setPreferredWidth(110);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("genre"))).setCellRenderer(this.getCellRenderer(new Font("Italic", Font.ITALIC, 13)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("genre"))).setPreferredWidth(20);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("rating"))).setCellRenderer(this.getCellRenderer(new Font("Roman", Font.ROMAN_BASELINE, 13)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("rating"))).setPreferredWidth(10);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("duration"))).setCellRenderer(this.getCellRenderer(new Font("Roman", Font.ROMAN_BASELINE, 13)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("duration"))).setPreferredWidth(10);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("description"))).setPreferredWidth(100);
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("url"))).setCellRenderer(this.getCellRenderer(new Font("", Font.CENTER_BASELINE, 12)));
		table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex(fl.getColumnName("url"))).setPreferredWidth(5);
		
		table.getTableHeader().setBackground(Color.PINK);
		table.getTableHeader().setFont(font);
		
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		    	
		    	if(table.getColumnName(column).equals(columnNames[1])){
		    		
		    		if(!table.getValueAt(row, column).toString().equals(nameFromDiscTemp)){
		    
		    			nameFromDiscTemp = table.getValueAt(row, column).toString();
		    			c.setBackground(Color.CYAN);
		    			table.setRowHeight(row, 15);
		    		}
		    		else{c.setBackground(Color.WHITE);}	
		    	}
		    	else{c.setBackground(Color.WHITE);}
		        return c;
		    }
		});
		return table;
	}

   public void open(URI uri) {
		  if (Desktop.isDesktopSupported()) {
		    try {
		       Desktop.getDesktop().browse(uri);
		      } catch (IOException e) { JOptionPane.showMessageDialog(null, "Error: ServicePresentation.open.");}
		   } else { /* TODO: error handling */ }
	}
   
   @SuppressWarnings("serial")
private DefaultTableCellRenderer getCellRenderer(final Font font){
	   
		DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
		   
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		                row, column);
		        setFont(font);
		        return this;
		    }

		};
	   
		return r;
   }
   
   public ArrayList<String> getDiscs(){
	   
	    ArrayList<String> disc = new ArrayList<String>();
	   
	    JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Izberi mapo z filmi");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Integer showOpenDialog = fileChooser.showOpenDialog(null);
		
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
           return null;
        }
       File[] uploadDir = fileChooser.getSelectedFiles();
       for(File f : uploadDir){
       	  disc.add(f.getName());
       }
	   
	   return disc;
   }
}
