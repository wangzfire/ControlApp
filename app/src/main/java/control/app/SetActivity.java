package control.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import java.net.Socket;

/**
 * Created by ac0b on 2019/5/23.
 */

public class SetActivity extends AppCompatActivity {
    private static final String TAG = "SetActivity";
    private static final String[] m = {"A", "B", "C", "D"};
    private Spinner spinner_one, spinner_two, spinner_three;
    private ImageView image;
    //记录开关状态
    private boolean lean = false;
    private ArrayAdapter<String> adapter;
    Button buttonSetting;//设置按钮
    String[] postArr = new String[5];// 创建一个大小为3的数组
    private Socket mSocket;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        buttonSetting = (Button) findViewById(R.id.setting);
        buttonSetting.setOnClickListener(buttonSettingClick);//绑定id，绑定监听
        spinner_one = findViewById(R.id.spinner_one);
        spinner_two = findViewById(R.id.spinner_two);
        spinner_three = findViewById(R.id.spinner_three);
        image = findViewById(R.id.image);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_one.setAdapter(adapter);
        spinner_two.setAdapter(adapter);
        spinner_three.setAdapter(adapter);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i).toString();
                postArr[0] = name;
                Log.i("spinner_one", name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i).toString();
                postArr[1] = name;
                Log.i("spinner_two", name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_three.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i).toString();
                postArr[2] = name;
                Log.i("spinner_three", name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lean){
                    lean= true;
                    image.setImageResource(R.mipmap.kaiguan2);
                }else {
                    lean = false;
                    image.setImageResource(R.mipmap.kaiguan1);
                }
            }
        });
    }

    /*按钮点击提交事件*/
    private View.OnClickListener buttonSettingClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(SocketClickManager.getInstance().isConnected()){
                boolean isSendSuccess = SocketClickManager.getInstance().sendMessage(postArr[2]);
                Log.i(TAG, isSendSuccess ? "发送成功 ":"发送失败" );
                if(isSendSuccess){
                    //设置成功 跳转到接收消息页面
                    Intent intent = new Intent();
                    intent.setClass(SetActivity.this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }
    };
}
