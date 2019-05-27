package control.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created on 2019/5/11
 *
 * @author wangsssl
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtIpAddress;
    private EditText mEdtPort;//ip地址和端口号的编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtIpAddress = findViewById(R.id.ip);
        mEdtPort = findViewById(R.id.port);

        findViewById(R.id.connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.connect == id) {
            //判断是否有输入内容
            String ipAddress = mEdtIpAddress.getText().toString().trim();//获取IP地址
            String strPort = mEdtPort.getText().toString().trim();
            int port = 0;//获取端口号
            if (!strPort.isEmpty()) {
                port = Integer.valueOf(strPort);
            }

            if (checkParams(ipAddress, port)) {
                connection(ipAddress, port, 5000);
            }
        }
    }

    private boolean checkParams(String hostName, int port) {
        if (null == hostName || hostName.isEmpty()) {
            toast("请输入IP地址");
        } else if (port <= 0) {
            toast("请输入端口号");
        } else {
            return true;
        }
        return false;
    }

    private void connection(String hostName, int port, int timeout) {
        SocketClickManager.getInstance().connect(this, hostName, port, timeout, new ConnectionCallback() {
            @Override
            public void callback(boolean success) {
                if (success) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SetActivity.class);
                    startActivity(intent);
                } else {
                    toast("连接失败");
                }
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

}
