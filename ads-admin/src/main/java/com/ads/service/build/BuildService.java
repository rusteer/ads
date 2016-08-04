package com.ads.service.build;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class BuildService {
    private static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
    private static final String driverPrefix = isWin ? "E:" : "";
    private static final String projectRoot = driverPrefix + "/workspace/ads/code/";
    private static final String sdkPrefix = "ads";
    public static void main(String args[]) {
        //start("ads-android", "hysdk.cn", "com.hy.ads", "hads");
        start("ads-android", "ads.yousdk.com", "com.fly.ads", "fads");
    }
    public static void start(String projectName, String hostName, String newPackageName, String sdkShortName) {
        try {
            File newProjectRoot = new File(driverPrefix + "/tmp/ads-android-build/" + sdkShortName);
            FileUtils.deleteDirectory(newProjectRoot);
            FileUtils.copyDirectory(new File(projectRoot + projectName), newProjectRoot);
            {
                File file = new File(newProjectRoot + "/src/com/ads/android/setting/Setting.java");
                FileUtils.write(file, FileUtils.readFileToString(file).replace("localhost", hostName));
            }
            {
                File file = new File(newProjectRoot + "/.project");
                FileUtils.write(file, FileUtils.readFileToString(file).replace(projectName, projectName+"-build-" + sdkShortName));
            }
            String newProejctPath = newProjectRoot.getAbsolutePath();
            FileUtils.moveDirectory(new File(newProejctPath + "/src/com/export"), new File(newProejctPath + "/src/" + newPackageName.replace('.', '/')));
            replaceKeyworks(newProejctPath, newPackageName, sdkShortName);
            replacePrefix(newProejctPath, newPackageName, sdkShortName);
            ConstantReplacer.replace(newProejctPath);
            File genFile = new File(newProjectRoot + "/gen");
            if (genFile.exists()) {
                FileUtils.deleteDirectory(genFile);
            }
            generateApk(newProejctPath);
            generateSdk(newProejctPath, sdkShortName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void replacePrefix(String root, String newPackageName, String newSdkPrefix) throws IOException {
        File file = new File(root);
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                replacePrefix(child.getAbsolutePath(), newPackageName, newSdkPrefix);
            }
        } else {
            String name = file.getName();
            String parent = file.getParent();
            if (name.startsWith(sdkPrefix)) {
                String newPath = parent + "/" + name.replace(sdkPrefix, newSdkPrefix);
                try {
                    FileUtils.moveFile(file, new File(newPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void replaceKeyworks(String root, String newPackageName, String newSdkPrefix) throws IOException {
        File file = new File(root);
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                replaceKeyworks(child.getAbsolutePath(), newPackageName, newSdkPrefix);
            }
        } else {
            String name = file.getName();
            if (name.endsWith(".java") || name.endsWith(".xml") || name.endsWith(".txt")) {
                String content = FileUtils.readFileToString(file)//
                        .replaceAll("com.export", newPackageName)//
                        //.replaceAll(sdkPackageName, newPackageName)//
                        .replace(sdkPrefix + "_", newSdkPrefix + "_");
                FileUtils.write(file, content);
            }
        }
    }
    private static String generateApk(String projectPath) throws IOException {
        if (!isWin) {
            String localPropertiesFile = projectPath + "/local.properties";
            FileUtils.write(new File(localPropertiesFile), "sdk.dir=/usr/local/android/sdk");
            CmdUtil.exeCmd("sh " + projectPath + "/build.sh");
        } else {
            CmdUtil.exeCmd(projectPath + "/build.bat");
            CmdUtil.exeCmd(projectPath + "/extract.bat");
        }
        return null;
    }
    private static String generateSdk(String projectPath, String newSdkPrefix) throws IOException {
        String folder = driverPrefix + "/workspace/ads/code/sdk-release/" + newSdkPrefix;
        FileUtils.deleteDirectory(new File(folder));
        FileUtils.copyFile(new File(projectPath + "/bin/apk-extract/classes_dex2jar.jar"), new File(folder + "/libs/" + newSdkPrefix + ".jar"));
        FileUtils.copyDirectory(new File(projectPath + "/res"), new File(folder + "/res"));
        FileUtils.copyDirectory(new File(projectPath + "/assets"), new File(folder + "/assets"));
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
}
