package control.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;

/**
 * Created on 2019/5/11
 * @author sssl
 */
public class MainActivity extends AppCompatActivity {
    //声明控件
    Button buttonConnect;//连接按钮
    EditText editTextIPAdress, editTextPort;//ip地址和端口号的编辑框


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonConnect.setOnClickListener(buttonConnectClick);//绑定id，绑定监听
        editTextIPAdress = (EditText) findViewById(R.id.ip);
        editTextPort = (EditText) findViewById(R.id.port);
    }

    /*按钮点击连接事件*/
    private View.OnClickListener buttonConnectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!TextUtils.isEmpty(editTextIPAdress.getText()) && !TextUtils.isEmpty(editTextPort.getText())) {
                            InetAddress ipAddress = InetAddress.getByName(editTextIPAdress.getText().toString());//获取IP地址
                            int port = Integer.valueOf(editTextPort.getText().toString());//获取端口号
                            //create a socket to make the connection with the server
                            //开启服务器
                            try {
                                Log.v("MainActivity", "Log.v输入日志信息" + ipAddress);
                                Log.v("MainActivity", "Log.v输入日志信息" + port);
                                Socket SocketService = new SocketService(ipAddress, port);
                                if (SocketService.isConnected()) {
                                    SocketService.setKeepAlive(true);
                                    Log.v("MainActivity", "连接成功");
//                                    Toast.makeText(MainActivity.this, "'连接成功'", Toast.LENGTH_SHORT).show();
                                 //   new AcceptSocket(mSocket).start();
//                                    //连接成功 跳转页面
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, SetActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(MainActivity.this, "'连接失败'", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "'连接失败'", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

}
