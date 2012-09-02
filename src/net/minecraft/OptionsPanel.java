package net.minecraft;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class OptionsPanel extends JDialog
{
  private static final long serialVersionUID = 1L;
  public JTextField memoryfield = new JTextField(4);
  public static int memory;
  public static String memorys;

  public OptionsPanel(Frame parent)
  {
    super(parent);

    setModal(true);

    JPanel panel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("Настройки", 0);
    label.setBorder(new EmptyBorder(0, 0, 16, 0));
    label.setFont(new Font("Default", 1, 16));
    panel.add(label, "North");

    JPanel optionsPanel = new JPanel(new BorderLayout());
    JPanel labelPanel = new JPanel(new GridLayout(0, 1));
    JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
    optionsPanel.add(labelPanel, "West");
    optionsPanel.add(fieldPanel, "Center");

    final TransparentCheckbox dlFl = new TransparentCheckbox ("Обновить клиент!");


    dlFl.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            boolean forceUpdateInfo;
            forceUpdateInfo = dlFl.isSelected();
        	if (forceUpdateInfo == true)
            	GameUpdater.forceUpdate = true;
            else
            	GameUpdater.forceUpdate = false;
        }
      });


    final JButton forceButton = new JButton("Обновить клиент!");
    forceButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        GameUpdater.forceUpdate = true;
        forceButton.setText("Клиент будет обновлен!");
        forceButton.setEnabled(false);
      }
    });

    fieldPanel.add(dlFl);
    labelPanel.add(new JLabel("Принудительно обновить клиент: ", 4));
    fieldPanel.add(forceButton);

    labelPanel.add(new JLabel("Расположение клиента на компьютере: ", 4));
    labelPanel.add(new JLabel("Сколько памяти выделять игре (в мегабайтах): ", 4));
    TransparentLabel dirLink = new TransparentLabel(Util.getWorkingDirectory().toString()) {
      private static final long serialVersionUID = 0L;

      public void paint(Graphics g) { super.paint(g);

        int x = 0;
        int y = 0;

        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(getText());
        int height = fm.getHeight();

        if (getAlignmentX() == 2.0F) x = 0;
        else if (getAlignmentX() == 0.0F) x = getBounds().width / 2 - width / 2;
        else if (getAlignmentX() == 4.0F) x = getBounds().width - width;
        y = getBounds().height / 2 + height / 2 - 1;

        g.drawLine(x + 2, y, x + width - 2, y); }

      public void update(Graphics g)
      {
        paint(g);
      }
    };
    dirLink.setCursor(Cursor.getPredefinedCursor(12));
    dirLink.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent arg0) {
        try {
          Util.openLink(new URL("file://" + Util.getWorkingDirectory().getAbsolutePath()).toURI());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    dirLink.setForeground(new Color(2105599));

    fieldPanel.add(dirLink);

    panel.add(optionsPanel, "Center");

    memory = Util.getMemorySelection();
    if (memory == 1)
    	memory = 1024;
    String memos = Integer.toString(memory);
    memoryfield.setText(memos);
    fieldPanel.add(memoryfield,"mb");

    JPanel buttonsPanel = new JPanel(new BorderLayout());
    buttonsPanel.add(new JPanel(), "Center");
    JButton doneButton = new JButton("Done");
    doneButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        memorys = memoryfield.getText();
    	int memory = Integer.parseInt(memorys);
    	if (memory != Util.getMemorySelection()) {
			Util.setMemorySelection(memory);
	        String[] args = null;
			try {
				MinecraftLauncher.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
        setVisible(false);
      }
    });
    buttonsPanel.add(doneButton, "East");
    buttonsPanel.setBorder(new EmptyBorder(16, 0, 0, 0));

    panel.add(buttonsPanel, "South");

    add(panel);
    panel.setBorder(new EmptyBorder(16, 24, 24, 24));
    pack();
    setLocationRelativeTo(parent);
  }
}