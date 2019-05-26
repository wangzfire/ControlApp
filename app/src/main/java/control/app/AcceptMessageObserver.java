package control.app;

/**
 * Created by 段露 on 2019/5/16
 *
 * @author DUANLU
 * @version 1.0.0
 * @class AcceptMessageObserver
 * @describe 服务端消息观察者.
 */
public interface AcceptMessageObserver {
    /**
     * 接收到服务端消息.
     *
     * @param message 接收到的服务端发送的消息体.
     */
    void acceptMessage(String message);
}
