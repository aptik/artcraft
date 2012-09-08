package net.minecraft;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class LauncherFrame extends Frame
{
  private static final long serialVersionUID = 1L;
  public Map<String, String> customParameters = new HashMap<String, String>();
  public Launcher launcher;
  public LoginForm LoginForm = new LoginForm(this);
  public String memoryId;
  public String clientId;
  public static String client;
  public static String memory;
  public static String zip = "client.zip";
  public static JPanel panelBg = new bg();


  public LauncherFrame()
  {

    super(setting.LauncherName);
    setResizable(false);
	try {
		Properties defaultProps = new Properties();
		FileInputStream in;
		in = new FileInputStream(Util.getWorkingDirectory() + "/launcher.properties");
		defaultProps.load(in);

		clientId = defaultProps.getProperty("client");

		 if (clientId.trim().equals("2")){
			 client=setting.client2;
		 }else{
				 client=setting.client1;
		 }
		in.close();
		saveSetting("23");
	} catch (FileNotFoundException e) {

			try{
		 FileChannel source = new FileInputStream("src/launcher.properties").getChannel();
		 clientId = "1";
	      FileChannel destination = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties").getChannel();

	      destination.transferFrom(source, 0, source.size());

	      source.close();
	      destination.close();
			}catch (IOException e1) {
				clientId = "1";
				client=setting.client1;
	  	}

	}catch (IOException e) {
		try{
	 FileChannel source = new FileInputStream("src/launcher.properties").getChannel();

      FileChannel destination = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties").getChannel();

      destination.transferFrom(source, 0, source.size());

      source.close();
      destination.close();
		}catch (IOException e1) {
			clientId = "1";
			client=setting.client1;
  	}
	}

        try {
		Properties defaultProps = new Properties();
		FileInputStream in;
		in = new FileInputStream(Util.getWorkingDirectory() + "/launcher.properties");
		defaultProps.load(in);

		memoryId = defaultProps.getProperty("memory");

		in.close();
		saveSetting2("1024");
	} catch (FileNotFoundException e) {

			try{
		 FileChannel source = new FileInputStream("src/launcher.properties").getChannel();
		 memoryId = "1024";
	      FileChannel destination = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties").getChannel();

	      destination.transferFrom(source, 0, source.size());

	      source.close();
	      destination.close();
			}catch (IOException e1) {
				memoryId = "1024";
	  	}

	}catch (IOException e) {
		try{
	 FileChannel source = new FileInputStream("src/launcher.properties").getChannel();

      FileChannel destination = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties").getChannel();

      destination.transferFrom(source, 0, source.size());

      source.close();
      destination.close();
		}catch (IOException e1) {
			memoryId = "1024";
  	}
	}


    Thread thr = new Thread(new InitSplash());
    thr.start();
    setBackground(Color.BLACK);


    panelBg.setLayout(new BorderLayout());

    panelBg.setPreferredSize(new Dimension(854, 482));

    JPanel LauncherFormAll = new LoginForm(this);

    panelBg.add(LauncherFormAll);





    setLayout(new BorderLayout());
    add(panelBg);

    pack();
    setLocationRelativeTo(null);
    try
    {
      setIconImage(ImageIO.read(LauncherFrame.class.getResource("favicon.png")));
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent arg0) {
        new Thread() {
          public void run() {
            try {
              Thread.sleep(30000L);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println("FORCING EXIT!");
            System.exit(0);
          }
        }
        .start();
        if (launcher != null) {
          launcher.stop();
          launcher.destroy();
        }
        System.exit(0);
      } } );
  }


  public void playCached(String userName) {
    try {
      if ((userName == null) || (userName.length() <= 0)) {
        userName = "Player";
      }

          String applicationData = System.getenv("APPDATA");
          String m = applicationData + "/." + setting.mineFolder + "/mods";
          File n = new File(m);
          delete(n);

          String g = applicationData + "/." + setting.mineFolder + "/texturepacks";
          File f = new File(g);
          delete(f);

          String j = applicationData + "/." + setting.mineFolder + "/config";
          File l = new File(j);
          delete(l);

      launcher = new Launcher();
      launcher.customParameters.putAll(customParameters);
      launcher.customParameters.put("userName", userName);
      launcher.init();
      removeAll();
      add(launcher, "Center");
      validate();
      launcher.start();
      LoginForm = null;
      setTitle("Minecraft");
    } catch (Exception e) {
      e.printStackTrace();
      showError(e.toString());
    }
  }

  public void login(String userName, String password) {
    String f = LauncherFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath(); // path to launcher
    URL url = LauncherFrame.class.getProtectionDomain().getCodeSource().getLocation();

    String launcherFile = new File(f).getName();
    String launcherHash = "";
    int pos = launcherFile.lastIndexOf('.');
    String launcherExt = launcherFile.substring(pos+1);
    try{
      MessageDigest md5  = MessageDigest.getInstance("MD5");
      launcherHash = calculateHashFromURL(md5, url);
    }catch (Exception e) {
	 showError("Невозможно создать hash лаунчера");
	 return;
    }

	    try {

	    	URL localURL = new URL(setting.authLink + URLEncoder.encode(userName, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&version=" + setting.version+"&launcher="+launcherExt+"&hash="+launcherHash);
	    	BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
	    	String result = localBufferedReader.readLine();
	      if (result == null) {
	        showError("Невозможно подключится к серверу!");
	        return;
	      }

	      if (!result.contains(":")) {
	        if (result.trim().equals("Bad login")) {
	          showError("Неправильный логин или пароль!");
	          return;
	        } else if (result.trim().equals("Bad version")) {
	          showError("Необходимо обновить лаунчер");
	          return;
	        } else {
	          showError(result);
	          return;
	        }
	      }


	     BuildProfilePanelForm(result);

	      return;
	    } catch (Exception e) {

	      e.printStackTrace();
	      showError(e.toString());
	    }
	  }

  public void BuildProfilePanelForm(String result) {
	  JPanel asdasd = LoginForm.profile(result);
	  panelBg.removeAll();
	asdasd.setBounds(0, 0, 854, 480);
	panelBg.add(asdasd);
    validate();
    repaint();
  }

  private void showError(String error) {
	  JPanel asdasd = LoginForm.buildOfflinePanel(error);
	  panelBg.removeAll();
	asdasd.setBounds(0, 0, 854, 480);
	panelBg.add(asdasd);
    validate();
    repaint();
  }

  public boolean canPlayOffline(String userName) {
    Launcher launcher = new Launcher();
    launcher.customParameters.putAll(customParameters);
    launcher.init(userName, null, null, null);
    return launcher.canPlayOffline();
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException) {
    }
    LauncherFrame launcherFrame = new LauncherFrame();
    launcherFrame.setVisible(true);
    launcherFrame.customParameters.put("stand-alone", "true");

    if (args.length >= 3) {
      String ip = args[2];
      String port = "25565";
      if (ip.contains(":")) {
        String[] parts = ip.split(":");
        ip = parts[0];
        port = parts[1];
      }

      launcherFrame.customParameters.put("server", ip);
      launcherFrame.customParameters.put("port", port);
    }

    if (args.length >= 1) {
      launcherFrame.LoginForm.userName.setText(args[0]);
      if (args.length >= 2) {
        launcherFrame.LoginForm.password.setText(args[1]);
        launcherFrame.LoginForm.doLogin();
      }
    }
  }

  public void Startminecraft (String result){
      try {
	  setResizableLol();
	  md5s();
          md5szip();

          String applicationData = System.getenv("APPDATA");
          String m = applicationData + "/." + setting.mineFolder + "/mods";
          File n = new File(m);
          delete(n);

          String g = applicationData + "/." + setting.mineFolder + "/texturepacks";
          File f = new File(g);
          delete(f);

          String j = applicationData + "/." + setting.mineFolder + "/config";
          File l = new File(j);
          delete(l);

          UnZip();

      String[] values = result.split(":");

      launcher = new Launcher();
      launcher.customParameters.putAll(customParameters);
      launcher.customParameters.put("userName", values[2].trim());
      launcher.customParameters.put("latestVersion", values[0].trim());
      launcher.customParameters.put("downloadTicket", values[1].trim());
      launcher.customParameters.put("sessionId", values[3].trim());
      launcher.init();

      removeAll();
      add(launcher, "Center");
      validate();
      launcher.start();
      LoginForm.loginOk();
      LoginForm = null;
      setTitle("Minecraft");
      } catch (Exception e) {
      e.printStackTrace();
      showError(e.toString());
    }

      return;
  }
  private void md5s(){
	  String applicationData = System.getenv("APPDATA");
      String  f = applicationData + "/." + setting.mineFolder + "/bin/"+ client +".jar";


 try{
  MessageDigest md5  = MessageDigest.getInstance("MD5");
String p = calculateHash(md5, f);
	try {
					URL localURL = new URL(setting.hashLink + p + "&check=" + client);
					BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
					String result = localBufferedReader.readLine();
					if (result.trim().equals("1")){
						GameUpdater.forceUpdate = true;
						return;
					}
					if (result.trim().equals("2")){
						GameUpdater.forceUpdate = false;
						return;
					}
				}catch (Exception e) {
					 return;
				 }

          String m = applicationData + "/." + setting.mineFolder + "/mods";
          File n = new File(m);
          delete(n);

          String g = applicationData + "/." + setting.mineFolder + "/texturepacks";
          File o = new File(g);
          delete(o);

          String j = applicationData + "/." + setting.mineFolder + "/config";
          File l = new File(j);
          delete(l);

          UnZip();

 }catch (Exception e) {
	 GameUpdater.forceUpdate = true;
	 return;
  }
  }

  private void md5szip(){
	  String applicationData = System.getenv("APPDATA");
      String  k = applicationData + "/." + setting.mineFolder + "/bin/"+ zip;


 try{
  MessageDigest md5  = MessageDigest.getInstance("MD5");
String p = calculateHash(md5, k);
	try {
					URL localURL = new URL(setting.hashLink + p + "&check=" + "zip");
					BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
					String result = localBufferedReader.readLine();
					if (result.trim().equals("1")){
						GameUpdater.forceUpdate = true;
						return;
					}
					if (result.trim().equals("2")){
						GameUpdater.forceUpdate = false;
						return;
					}
				}catch (Exception e) {
					 return;
				 }

          String m = applicationData + "/." + setting.mineFolder + "/mods";
          File n = new File(m);
          delete(n);

          String g = applicationData + "/." + setting.mineFolder + "/texturepacks";
          File f = new File(g);
          delete(f);

          String j = applicationData + "/." + setting.mineFolder + "/config";
          File l = new File(j);
          delete(l);

          UnZip();

 }catch (Exception e) {
	 GameUpdater.forceUpdate = true;
	 return;
  }
  }



  public static String calculateHashFromURL(MessageDigest algorithm,URL url) throws Exception{
      InputStream is = url.openStream();
      BufferedInputStream bis = new BufferedInputStream(is);
      DigestInputStream  dis = new DigestInputStream(bis, algorithm);

      while (dis.read() != -1);
            byte[] hash = algorithm.digest();

      return byteArray2Hex(hash);
  }

  public static String calculateHash(MessageDigest algorithm,String fileName) throws Exception{
      FileInputStream    fis = new FileInputStream(fileName);
      BufferedInputStream bis = new BufferedInputStream(fis);
      DigestInputStream  dis = new DigestInputStream(bis, algorithm);

      while (dis.read() != -1);
            byte[] hash = algorithm.digest();

      return byteArray2Hex(hash);
  }
private static String byteArray2Hex(byte[] hash) {
      Formatter formatter = new Formatter();
      for (byte b : hash) {
          formatter.format("%02x", b);
      }
      return formatter.toString();
  }

public static void saveSetting(String prop) throws IOException{

		Properties defaultProps = new Properties();
		FileInputStream in;
		in = new FileInputStream(Util.getWorkingDirectory() + "/launcher.properties");
		defaultProps.load(in);
		defaultProps.setProperty("client", prop);
        FileOutputStream output = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties");
        defaultProps.store(output, "Saved settings");
        in.close();
        output.close();

}

public static void saveSetting2(String prop) throws IOException{

		Properties defaultProps = new Properties();
		FileInputStream in;
		in = new FileInputStream(Util.getWorkingDirectory() + "/launcher.properties");
		defaultProps.load(in);
		defaultProps.setProperty("memory", prop);
        FileOutputStream output = new FileOutputStream(Util.getWorkingDirectory() + "/launcher.properties");
        defaultProps.store(output, "Saved settings");
        in.close();
        output.close();

}

public void setResizableLol(){
	setResizable(true);
}
public void delete(File file)
  {
    if(!file.exists())
      return;
    if(file.isDirectory())
    {
      for(File f : file.listFiles())
        delete(f);
      file.delete();
    }
    else
    {
      file.delete();
    }
  }

  /**
 * Разархивирует файл client.zip из папки bin в .minecraft
 * @author ddark008
 * @throws PrivilegedActionException
 */
protected void UnZip() throws PrivilegedActionException
  {
    String szZipFilePath;
    String szExtractPath;
    String path = (String)AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
        public Object run() throws Exception {
          return Util.getWorkingDirectory() + File.separator;
        }
      });
    int i;

    szZipFilePath = path + "bin" + File.separator + "client.zip";

    File f = new File(szZipFilePath);
    if(!f.exists())
    {
      System.out.println(
	"\nNot found: " + szZipFilePath);
    }

    if(f.isDirectory())
    {
      System.out.println(
	"\nNot file: " + szZipFilePath);
    }

    System.out.println(
      "Enter path to extract files: ");
    szExtractPath = path;

    File f1 = new File(szExtractPath);
    if(!f1.exists())
    {
      System.out.println(
	"\nNot found: " + szExtractPath);
    }

    if(!f1.isDirectory())
    {
      System.out.println(
	"\nNot directory: " + szExtractPath);
    }

    ZipFile zf;
    Vector zipEntries = new Vector();

    try
    {
      zf = new ZipFile(szZipFilePath);
      Enumeration en = zf.entries();

      while(en.hasMoreElements())
      {
        zipEntries.addElement(
	  (ZipEntry)en.nextElement());
      }

      for (i = 0; i < zipEntries.size(); i++)
      {
        ZipEntry ze =
	  (ZipEntry)zipEntries.elementAt(i);

        extractFromZip(szZipFilePath, szExtractPath,
	  ze.getName(), zf, ze);
      }

      zf.close();
      System.out.println("Done!");
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }
  }

  // ============================================
  // extractFromZip
  // ============================================
  static void extractFromZip(
    String szZipFilePath, String szExtractPath,
    String szName,
    ZipFile zf, ZipEntry ze)
  {
    if(ze.isDirectory())
      return;

    String szDstName = slash2sep(szName);

    String szEntryDir;

    if(szDstName.lastIndexOf(File.separator) != -1)
    {
      szEntryDir =
        szDstName.substring(
	  0, szDstName.lastIndexOf(File.separator));
    }
    else
      szEntryDir = "";

    System.out.print(szDstName);
    long nSize = ze.getSize();
    long nCompressedSize =
      ze.getCompressedSize();

    System.out.println(" " + nSize + " (" +
      nCompressedSize + ")");

    try
    {
       File newDir = new File(szExtractPath +
	 File.separator + szEntryDir);

       newDir.mkdirs();

       FileOutputStream fos =
	 new FileOutputStream(szExtractPath +
	 File.separator + szDstName);

       InputStream is = zf.getInputStream(ze);
       byte[] buf = new byte[1024];

       int nLength;

       while(true)
       {
         try
         {
	   nLength = is.read(buf);
         }
         catch (EOFException ex)
         {
	   break;
	 }

         if(nLength < 0)
	   break;
         fos.write(buf, 0, nLength);
       }

       is.close();
       fos.close();
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
      //System.exit(0);
    }
  }
  // ============================================
  // slash2sep
  // ============================================
  static String slash2sep(String src)
  {
    int i;
    char[] chDst = new char[src.length()];
    String dst;

    for(i = 0; i < src.length(); i++)
    {
      if(src.charAt(i) == '/')
        chDst[i] = File.separatorChar;
      else
        chDst[i] = src.charAt(i);
    }
    dst = new String(chDst);
    return dst;
  }


}