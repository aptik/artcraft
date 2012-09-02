package net.minecraft;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MinecraftLauncher
{
public static String memoryId;

  public static void main(String[] args)
  
    throws Exception
  {
    float heapSizeMegs = (float)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
    
    try {
        Properties defaultProps = new Properties();
		FileInputStream in;
		in = new FileInputStream(Util.getWorkingDirectory() + "/launcher.properties");
		defaultProps.load(in);

		memoryId = defaultProps.getProperty("memory");
                        
		in.close();
        }catch (IOException e1){
        }

    if (heapSizeMegs > 511.0F)
      LauncherFrame.main(args);
    else
      try {
    	  
    	  String pathToJar = MinecraftLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        ArrayList<String> params = new ArrayList<String>();

        if (Util.getPlatform() == Util.OS.windows) {
			params.add("javaw");
		} else {
			params.add("java");
		}
        params.add("-Xmx" + memoryId + "m");
        params.add("-Dsun.java2d.noddraw=true");
        params.add("-Dsun.java2d.d3d=false");
        params.add("-Dsun.java2d.opengl=false");
        params.add("-Dsun.java2d.pmoffscreen=false");
        if (System.getProperty("net.minecraft.server") != null)
				params.add("-Dnet.minecraft.server="
						+ System.getProperty("net.minecraft.server"));
        params.add("-classpath");
        params.add(pathToJar);
        params.add("net.minecraft.LauncherFrame");

        ProcessBuilder pb = new ProcessBuilder(params);
        Process process = pb.start();
        if (process == null) throw new Exception("!");
        System.exit(0);
      } catch (Exception e) {
        e.printStackTrace();
        LauncherFrame.main(args);
      }
  }
}