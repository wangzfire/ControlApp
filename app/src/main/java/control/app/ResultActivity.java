package control.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wangsssl on 2019/5/26.
 */ 
public class ResultActivity extends AppCompatActivity {
    //声明控件
    Button buttonDisconnect;//关闭连接按钮
    TextView showResult;//编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_result);
         buttonDisconnect = (Button) findViewById(R.id.disconnect);
         buttonDisconnect.setOnClickListener(buttonDisConnectClick);//绑定id，绑定监听
         showResult = (TextView)findViewById(R.id.result);

         SocketClickManager.getInstance().registerAcceptMessageObserver(new AcceptMessageObserver() {
             @Override
             public void acceptMessage(String message) {
                 showResult.setText(message);
                 Toast.makeText(ResultActivity.this,"收到消息："+message,Toast.LENGTH_LONG).show();
             }
         });
    }

    //点击关闭
    private View.OnClickListener buttonDisConnectClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SocketClickManager.getInstance().disconnect();
        }
    };
}
