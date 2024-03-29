package org.presentation.layer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Progress implements Runnable
{
  private JFrame frameProgress;
 
  public Progress(String title)
  {
	  this.frameProgress = new JFrame(title);
	  ImageIcon loading = new ImageIcon("./ajax-loader.gif");
	  this.frameProgress.add(new JLabel("loading... ", loading, JLabel.CENTER));
	  this.frameProgress.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.frameProgress.setSize(400, 300);
	  this.frameProgress.setVisible(true);
  }

  public void start()
  {
    new Thread(this).start();
  }

  public void close()
  {
    this.frameProgress.dispose();
    this.frameProgress = null;
  }
 
  @Override
  public void run()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // do nothing, because progress bar is indeterminate
      }
    });
  }
}
