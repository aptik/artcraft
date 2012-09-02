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

public class SendPass extends JDialog
{
  private static final long serialVersionUID = 1L;
  private TransparentLabel errorReasonLabel = new TransparentLabel("", 0);
  private TransparentLabel doneLabel = new TransparentLabel("Пароль успешно отправлен", 0);

  private JPanel BackgroundBg = new BackgroundsendpassPanel();
  static final Color LABELCOLORREG = new Color(255, 255, 255);
  static final Color LABELCOLORDONE = new Color(45, 118, 214);
  static final Color LABELCOLORERROR = new Color(181, 30, 30);
  private final Font LabelReason = new Font("Tahoma", Font.BOLD, 16);



  public SendPass(Frame parent)
  {
    super(parent);
    setTitle("Восстановление пароля");
    setSize(400, 380);
    setResizable(false);
    setModal(true);
    setLocationRelativeTo(parent);

    BackgroundBg.setLayout(new BorderLayout());




    add(BackgroundBg);
    BackgroundBg.setBorder(new EmptyBorder(24, 24, 24, 24));
    sendpassPanelForm ();

  }

  public void sendpassPanelForm (){
	  BackgroundBg.removeAll();
	  BackgroundBg.add(new sendpassLogo(), "North");
	    final Font fontLabelsendpass = new Font("Tahoma", Font.BOLD, 13);
	    final TextField userField = new TextField();
	    JLabel userLabel = new JLabel("Ник: ", 2);
	    userLabel.setForeground(LABELCOLORREG);
	    userLabel.setFont(fontLabelsendpass);
	    final TextField MailField = new TextField();
	    MailField.setColumns(30);
	    final JLabel MailLabel = new JLabel("E-mail: ", 2);
	    MailLabel.setForeground(LABELCOLORREG);
	    MailLabel.setFont(fontLabelsendpass);


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
	    values.add(MailField);
	    titles.add(MailLabel);

	    BackgroundBg.add(values, "East");
	    BackgroundBg.add(titles, "West");


	    JButton doneButton = new TransparentButton("Отправить пароль");
	    doneButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
		  	    String user = userField.getText();
			    String eMail = MailField.getText();

	    	  sendpass(user, eMail);



	      }
	    });


	    doneButton.setBorder(new EmptyBorder(16, 16, 16, 16));
	    ButtonOK.setBorder(new EmptyBorder(16, 0, 0, 0));
	    ButtonOK.add(doneButton);
	    BackgroundBg.add(ButtonOK, "South");

	    BackgroundBg.repaint();
	    BackgroundBg.revalidate();
  }

public void sendpass(String name, String mail) {

	  try {
	    URL localURL = new URL(setting.forgetPassword + URLEncoder.encode(name, "UTF-8") + "&email=" + URLEncoder.encode(mail, "UTF-8"));
		BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
  	    String resultSite = localBufferedReader.readLine();
  	    System.out.println(resultSite);
  	    if (resultSite.equals("done")){
  	    	donesendpass();
  	    }else{
  	    	if (resultSite.equals("ErrorPovtor")){
  	  	    	failsendpass ("Неправильный ник или email");
  	  	    }else if (resultSite.equals("errorField")){
  	  	    	failsendpass ("Заполнены не все поля");
  	  	    }else {
	  	    	failsendpass ("Неизвестная ошибка");
	  	    }
  	    }





  	    return;

		} catch (Exception e) {
			e.printStackTrace();
		}
}

  public void failsendpass (String errorReason){

	  BackgroundBg.removeAll();
	  BackgroundBg.add(new sendpassLogo(), "North");
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
	    	  sendpassPanelForm ();
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

  public void donesendpass (){

	  BackgroundBg.removeAll();
	  BackgroundBg.add(new sendpassLogo(), "North");
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