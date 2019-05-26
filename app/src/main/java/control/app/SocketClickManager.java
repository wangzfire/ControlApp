package control.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 段露 on 2019/5/25
 *
 * @author DUANLU
 * @version 1.0.0
 * @class SocketClickManager
 * @describe Socket管理器.
 */
public class SocketClickManager {

    private static final String TAG = "SocketClickManager";

    private static volatile SocketClickManager sInstance;

    private Socket mSocket;
    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;

    private static final int WHAT_ACCEPT_MESSAGE = 0x01;

    private ObserverHandler mObserverHandler;

    private List<AcceptMessageObserver> mAcceptMessageObserverList;

    private SocketClickManager() {
        mSocket = new Socket();
        mObserverHandler = new ObserverHandler(this);
        mAcceptMessageObserverList = new ArrayList<>();
    }

    /**
     * 获取管理器实例.
     *
     * @return 管理器实例.
     */
    public static SocketClickManager getInstance() {
        if (null == sInstance) {
            synchronized (SocketClickManager.class) {
                if (null == sInstance) {
                    sInstance = new SocketClickManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * Socket是否和服务端连接.
     *
     * @return true连接, false未连接.
     */
    public boolean isConnected() {
        return null != mSocket && mSocket.isConnected() && !mSocket.isClosed();
    }

    /**
     * 连接服务端.
     *
     * @param hostName 主机名.
     * @param port     端口号.
     * @param timeout  超时时间.
     * @return true连接成功, false连接失败.
     */
    public boolean connect(String hostName, int port, int timeout) {
        return _connect(hostName, port, timeout);
    }

    private boolean _connect(String hostName, int port, int timeout) {
        disconnectIfConnect();
        try {
            mSocket.connect(new InetSocketAddress(hostName, port), timeout);
            if (mSocket.isConnected()) {
                mSocket.setKeepAlive(true);
                mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));

                observerAcceptMessage();

                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            Log.e(TAG, "连接失败：" + e.getMessage());
            return false;
        }
    }

    private void disconnectIfConnect() {
        disconnect();
    }

    /**
     * 取消和服务端连接.
     *
     * @return true取消成功, false关闭连接失败.
     */
    public boolean disconnect() {
        if (isConnected()) {
            try {
                mSocket.close();
                mSocket = null;
                return true;
            } catch (IOException e) {
                Log.e(TAG, "关闭连接失败：" + e.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * 注册服务端消息观察者.
     *
     * @param observer 服务端消息观察者.
     */
    public void registerAcceptMessageObserver(AcceptMessageObserver observer) {
        this.mAcceptMessageObserverList.add(observer);
    }

    /**
     * 移除服务端消息观察者.
     *
     * @param observer 服务端消息观察者.
     */
    public void unregisterAcceptMessageObserver(AcceptMessageObserver observer) {
        this.mAcceptMessageObserverList.remove(observer);
    }

    /**
     * 发送消息.
     *
     * @param message 需要发送的消息.
     * @return true发送成功(注 ： 空消息也返回true ， 但是实际未发送), false发送失败.
     */
    public boolean sendMessage(String message) {
        if (null != message && !message.isEmpty()) {
            return _sendMessage(message);
        }
        return true;
    }

    private boolean _sendMessage(String message) {
        message = wrapperMessage(message);
        try {
            mBufferedWriter.write(message, 0, message.length());
            mBufferedWriter.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "发送数据失败：" + e.getMessage());
            return false;
        }
    }

    private String wrapperMessage(String message) {
        return message + "\n";
    }

    private void observerAcceptMessage() {
        if (isConnected()) {
            try {
                String line = mBufferedReader.readLine();
                while (null != line) {
                    Log.i(TAG, "客户端收到服务端消息：" + line);

                    line = mBufferedReader.readLine();

                    //通过handler将消息发送到主线程.
                    mObserverHandler.obtainMessage(WHAT_ACCEPT_MESSAGE, line)
                            .sendToTarget();
                }
            } catch (IOException e) {
                Log.i(TAG, "客户端接收服务端消息失败!!!");
            }
        }
    }

    private void notifyObserve(String message) {
        if (mAcceptMessageObserverList.size() > 0) {
            AcceptMessageObserver[] observerArray = new AcceptMessageObserver[mAcceptMessageObserverList.size()];
            mAcceptMessageObserverList.toArray(observerArray);
            for (AcceptMessageObserver observer : observerArray) {
                observer.acceptMessage(message);
            }
        }
    }

    private static class ObserverHandler extends Handler {

        private SocketClickManager manager;

        private ObserverHandler(SocketClickManager manager) {
            this.manager = manager;
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (WHAT_ACCEPT_MESSAGE == msg.what) {
                manager.notifyObserve((String) msg.obj);
            }
        }
    }

}
