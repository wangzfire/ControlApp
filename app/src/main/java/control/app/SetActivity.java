package control.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by ac0b on 2019/5/23.
 */
public class SetActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SetActivity";
    private static final String[] m = {"A", "B", "C", "D"};
    private Spinner spinner_one, spinner_two, spinner_three;
    private ImageView mIvToggle;
    //记录开关状态
    private boolean lean = false;
    private ArrayAdapter<String> adapter;
    String[] postArr = new String[5];// 创建一个大小为3的数组

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        spinner_one = findViewById(R.id.spinner_one);
        spinner_two = findViewById(R.id.spinner_two);
        spinner_three = findViewById(R.id.spinner_three);
        mIvToggle = findViewById(R.id.image);

        findViewById(R.id.setting).setOnClickListener(this);
        mIvToggle.setOnClickListener(this);

        initSpinner();
    }

    private void initSpinner() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_one.setAdapter(adapter);
        spinner_two.setAdapter(adapter);
        spinner_three.setAdapter(adapter);

        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i);
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
                String name = adapter.getItem(i);
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
                String name = adapter.getItem(i);
                postArr[2] = name;
                Log.i("spinner_three", name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.image == id) {
            mIvToggle.setImageResource(lean ? R.mipmap.kaiguan1 : R.mipmap.kaiguan2);
            lean = !lean;
        } else if (R.id.setting == id) {
            setting();
        }
    }

    private void setting() {
        if (SocketClickManager.getInstance().isConnected()) {
            SocketClickManager.getInstance().sendMessage(postArr[2]);
            //设置成功 跳转到接收消息页面
            Intent intent = new Intent();
            intent.setClass(SetActivity.this, ResultActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
