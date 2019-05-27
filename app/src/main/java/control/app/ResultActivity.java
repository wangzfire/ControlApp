package control.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wangsssl on 2019/5/26.
 */
public class ResultActivity extends AppCompatActivity implements AcceptMessageObserver, View.OnClickListener {

    TextView mTvAcceptMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTvAcceptMessage = findViewById(R.id.result);

        findViewById(R.id.disconnect).setOnClickListener(this);

        SocketClickManager.getInstance().registerAcceptMessageObserver(this);
    }

    @Override
    public void acceptMessage(String message) {
        mTvAcceptMessage.setText(message);
        Toast.makeText(ResultActivity.this, "收到消息：" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (R.id.disconnect == v.getId()) {//断开连接.
            SocketClickManager.getInstance().disconnect();
        }
    }

}
