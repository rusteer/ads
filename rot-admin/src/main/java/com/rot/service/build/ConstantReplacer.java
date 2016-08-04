package com.rot.service.build;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantReplacer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ConstantReplacer.class);
    static Pattern pattern = Pattern.compile("\"([^\"]+)\"");
    public static void replace(String projectPath) throws IOException {
        int code = 10 + new Random().nextInt(Integer.MAX_VALUE - 20);
        SimpleEncoder.setCode(code);
        startReplace(projectPath);
        {
            File encoderFile = new File(projectPath + "/src/com/rot/utils/SimpleEncoder.java");
            String content = FileUtils.readFileToString(encoderFile);
            content = content.replace("228213", code + "");
            FileUtils.write(encoderFile, content);
        }
    }
    public static void startReplace(String path) throws IOException {
        File file = new File(path);
        for (File child : file.listFiles()) {
            if (child.isDirectory()) {
                startReplace(child.getAbsolutePath());
            } else if (child.getAbsolutePath().endsWith(".java") && !child.getAbsolutePath().contains("SimpleEncoder")) {
                List<String> lines = FileUtils.readLines(child);
                StringBuilder sb = new StringBuilder();
                boolean needUpdate = false;
                for (String line : lines) {
                    String oldLine = line;
                    if (oldLine.trim().startsWith("MyLogger.")) {
                        needUpdate = true;
                        sb.append("//").append(oldLine).append("\n");
                    } else {
                        if (!line.contains("@") && !line.contains("\\") && !line.contains("\"\"")) {
                            Matcher matcher = pattern.matcher(line);
                            while (matcher.find()) {
                                needUpdate = true;
                                String fullText = matcher.group(0);
                                String pureText = matcher.group(1);
                                String encodedText = SimpleEncoder.getBytesString(pureText);
                                line = line.replace(fullText, encodedText);
                            }
                        }
                        if (!oldLine.equals(line)) {
                            sb.append("//").append(oldLine).append("\n");
                        }
                        sb.append(line).append("\n");
                    }
                }
                if (needUpdate) {
                    LOGGER.info(child.toString());
                    //LogUtil.info(temp);
                    FileUtils.write(child, sb.toString());
                }
                // String regex=""
            }
        }
    }
}
