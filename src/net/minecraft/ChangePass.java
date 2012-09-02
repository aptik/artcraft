package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChangePass extends JDialog
{
  private static final long serialVersionUID = 1L;
  private TransparentLabel errorReasonLabel = new TransparentLabel("", 0);
  private TransparentLabel doneLabel = new TransparentLabel("Смена пароля успешно завершена", 0);

  private JPanel BackgroundBg = new BackgroundChangePassPanel();
  static final Color LABELCOLORREG = new Color(255, 255, 255);
  static final Color LABELCOLORDONE = new Color(45, 118, 214);
  static final Color LABELCOLORERROR = new Color(181, 30, 30);
  private final Font LabelReason = new Font("Tahoma", Font.BOLD, 16);



  public ChangePass(Frame parent)
  {
    super(parent);
    setTitle("Смена пароля");
    setSize(400, 380);
    setResizable(false);
    setModal(true);
    setLocationRelativeTo(parent);

    BackgroundBg.setLayout(new BorderLayout());




    add(BackgroundBg);
    BackgroundBg.setBorder(new EmptyBorder(24, 24, 24, 24));
    ChangePassPanelForm ();

  }

  public void ChangePassPanelForm (){
	  BackgroundBg.removeAll();
	  BackgroundBg.add(new ChangePassLogo(), "North");
	    final Font fontLabelChangePass = new Font("Tahoma", Font.BOLD, 11);
	    final TextField userField = new TextField();
	    JLabel userLabel = new JLabel("Ник: ", 2);
	    userLabel.setForeground(LABELCOLORREG);
	    userLabel.setFont(fontLabelChangePass);
	    final TextField oldpassField = new TextField();
	    JLabel oldpassLabel = new JLabel("Старый пароль: ", 2);
	    oldpassLabel.setForeground(LABELCOLORREG);
	    oldpassLabel.setFont(fontLabelChangePass);
	    final TextField newpassField = new TextField();
	    JLabel newpassLabel = new JLabel("Новый пароль: ", 2);
	    newpassLabel.setForeground(LABELCOLORREG);
	    newpassLabel.setFont(fontLabelChangePass);
	    final TextField newpass2Field = new TextField();
	    newpass2Field.setColumns(30);
	    final JLabel newpass2Label = new JLabel("Повтор нового пароля: ", 2);
	    newpass2Label.setForeground(LABELCOLORREG);
	    newpass2Label.setFont(fontLabelChangePass);


	    GridLayout FieldText = new GridLayout(0, 1);
	    FieldText.setVgap(10);
	    FieldText.setHgap(10);
	    GridLayout FieldText2 = new GridLayout(0, 1);
	    FieldText2.setVgap(10);
	    FieldText2.setHgap(10);
	    GridLayout EnterButton = new GridLayout(0, 1);


	    TransparentPanel titles = new TransparentPanel(FieldText);
	    TransparentPanel values = new TransparentPanel(FieldText2);
	    TransparentPanel ButtonOK = new TransparentPanel(EnterButton);


	    values.add(userField);
	    titles.add(userLabel);
	    values.add(oldpassField);
	    titles.add(oldpassLabel);
	    values.add(newpassField);
	    titles.add(newpassLabel);
	    values.add(newpass2Field);
	    titles.add(newpass2Label);

	    BackgroundBg.add(values, "East");
	    BackgroundBg.add(titles, "West");


	    JButton doneButton = new TransparentButton("Изменить пароль");
	    doneButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
		  	    String user = userField.getText();
			    String oldpass = oldpassField.getText();
			    String newpass = newpassField.getText();
			    String newpass2 = newpass2Field.getText();

	    	  ChangePass(user, oldpass, newpass, newpass2);



	      }
	    });


	    doneButton.setBorder(new EmptyBorder(16, 16, 16, 16));
	    ButtonOK.setBorder(new EmptyBorder(16, 0, 0, 0));
	    ButtonOK.add(doneButton);
	    BackgroundBg.add(ButtonOK, "South");

	    BackgroundBg.repaint();
	    BackgroundBg.revalidate();
  }

public void ChangePass(String name, String oldpass, String newpass, String newpass2) {

	  try {
	    URL localURL = new URL(setting.changePassword + URLEncoder.encode(name, "UTF-8") + "&oldpass=" + URLEncoder.encode(oldpass, "UTF-8") + "&newpass=" + URLEncoder.encode(newpass, "UTF-8") + "&newpass2=" + URLEncoder.encode(newpass2, "UTF-8"));
		BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
  	    String resultSite = localBufferedReader.readLine();
  	    System.out.println(resultSite);
  	    if (resultSite.equals("done")){
  	    	doneChangePass();
  	    }else{
  	    	if (resultSite.equals("passErrorSymbol")){
  	  	    	failChangePass ("Пароль содержит запрещенные символы");
  	  	    }else if (resultSite.equals("errorPassSmall")){
  	  	    	failChangePass ("Пароль должен содержать 6-20 символов");
  	  	    }else if (resultSite.equals("ErrorPovtor")){
  	  	    	failChangePass ("Неправильный логин или пароль");
                    }else if (resultSite.equals("errorPassToPass")){
  	  	    	failChangePass ("Пароли не совпадают");
  	  	    }else if (resultSite.equals("errorField")){
  	  	    	failChangePass ("Заполнены не все поля");
  	  	    }else {
	  	    	failChangePass ("Неизвестная ошибка");
	  	    }
  	    }





  	    return;

		} catch (Exception e) {
			e.printStackTrace();
		}
}

  public void failChangePass (String errorReason){

	  BackgroundBg.removeAll();
	  BackgroundBg.add(new ChangePassLogo(), "North");
	  errorReasonLabel.setText(errorReason);
	  errorReasonLabel.setForeground(LABELCOLORERROR);
	  errorReasonLabel.setFont(LabelReason);
	  BackgroundBg.add(errorReasonLabel,"Center");


    GridLayout EnterButton = new GridLayout(0, 1);
    EnterButton.setVgap(2);

	TransparentPanel ButtonPanel = new TransparentPanel(EnterButton);

	    JButton doneButton = new TransparentButton("Попробовать еще раз");
	    doneButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  ChangePassPanelForm ();
	      }
	    });
	    JButton closeButton = new TransparentButton("Закрыть");
	    closeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  setVisible(false);
	      }
	    });
	    doneButton.setBorder(new EmptyBorder(6, 0, 6, 0));
	    closeButton.setBorder(new EmptyBorder(6, 0, 6, 0));
	    ButtonPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
	    ButtonPanel.add(closeButton);
	    ButtonPanel.add(doneButton);
	  BackgroundBg.add(ButtonPanel, "South");

	    BackgroundBg.repaint();
	    BackgroundBg.revalidate();


  }

  public void doneChangePass (){

	  BackgroundBg.removeAll();
	  BackgroundBg.add(new ChangePassLogo(), "North");
	  doneLabel.setForeground(LABELCOLORDONE);
	  doneLabel.setFont(LabelReason);
	  BackgroundBg.add(doneLabel,"Center");


    GridLayout EnterButton = new GridLayout(0, 1);
    EnterButton.setVgap(2);

	TransparentPanel ButtonPanel = new TransparentPanel(EnterButton);

	    JButton closeButton = new TransparentButton("Закрыть");
	    closeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  setVisible(false);
	      }
	    });
	    closeButton.setBorder(new EmptyBorder(16, 0, 16, 0));
	    ButtonPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
	    ButtonPanel.add(closeButton);
	  BackgroundBg.add(ButtonPanel, "South");

	    BackgroundBg.repaint();
	    BackgroundBg.revalidate();


  }


}