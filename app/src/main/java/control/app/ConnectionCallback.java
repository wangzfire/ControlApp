package control.app;

/********************************
 * @name ConnectionCallback
 * @author 段露
 * @createDate 2019/05/27 14:36
 * @updateDate 2019/05/27 14:36
 * @version V1.0.0
 * @describe Socket连接回调.
 ********************************/
public interface ConnectionCallback {
    /**
     * Socket连接回调.
     *
     * @param success true连接成功,false连接失败.
     */
    void callback(boolean success);
}
