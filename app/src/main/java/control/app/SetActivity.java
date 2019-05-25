package control.app;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by ac0b on 2019/5/23.
 */

public class SetActivity extends AppCompatActivity {
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
            Log.v("MainActivity", "start");
            Log.i("spinner_arr0", postArr[0]);
            Log.i("spinner_arr1", postArr[1]);
            Log.i("spinner_arr2", postArr[2]);
            try {
                PrintWriter mBufferedWriter = SocketService.Output();
                Log.v("MainActivity", "try");
                mBufferedWriter.write(postArr[2], 0, postArr[2].length());
                mBufferedWriter.flush();
                Log.v("MainActivity", "发送成功");
            } catch (IOException e) {
                Log.v("MainActivity", "发送失败");
                e.printStackTrace();
            }
        }
    };
}