package workspace.callback;
import java.io.File;

public interface DownloadCallback {
    void onResult(File file, Throwable error);
}
