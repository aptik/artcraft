package net.minecraft;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class TransparentCheckbox extends JCheckBox
{
  private static final long serialVersionUID = 1L;

  public TransparentCheckbox(String string)
  {
    super(string);
                        String path1 = "CheckBox1.png";
	                URL imgURL1 = TransparentCheckbox.class.getResource(path1);
			setIcon(new ImageIcon(imgURL1));                        
                        String path2 = "CheckBox2.png";
	                URL imgURL2 = TransparentCheckbox.class.getResource(path2);
			setRolloverIcon(new ImageIcon(imgURL2));
                        String path3 = "CheckBox3.png";
	                URL imgURL3 = TransparentCheckbox.class.getResource(path3);
			setSelectedIcon(new ImageIcon(imgURL3));
                        String path4 = "CheckBox4.png";
	                URL imgURL4 = TransparentCheckbox.class.getResource(path4);
			setRolloverSelectedIcon(new ImageIcon(imgURL4));
		



	
    
    setForeground(Color.decode("#3ba4d1"));
    setOpaque(false);
  }
  
  

}