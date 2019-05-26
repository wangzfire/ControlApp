package control.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 段露 on 2019/5/25
 *
 * @author DUANLU
 * @version 1.0.0
 * @class SocketIntentService
 * @describe Socket连接服务.
 */
public class SocketIntentService extends IntentService {

    private static final String TAG = "SocketIntentService";

    public static void startService(@NonNull Context context, @NonNull String hostName, int port, int timeout) {
        Intent intent = new Intent(context, SocketIntentService.class);
        intent.putExtra("host_name", hostName);
        intent.putExtra("port", port);
        intent.putExtra("timeout", timeout);
        context.startService(intent);
    }

    public SocketIntentService() {
        super("debug_socket_intent_service");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SocketIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (null != intent) {
            String hostName = intent.getStringExtra("host_name");
            int port = intent.getIntExtra("port", -1);
            int timeout = intent.getIntExtra("timeout", 1000);
            boolean isConnectSuccess = SocketClickManager.getInstance().connect(hostName, port, timeout);
            Log.i(TAG, isConnectSuccess ? "连接成功" : "连接失败");
        }
    }

    @Override
    public void onDestroy() {
        //关闭Socket连接.
        SocketClickManager.getInstance().disconnect();
        super.onDestroy();
    }

}
