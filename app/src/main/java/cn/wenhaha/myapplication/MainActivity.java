package cn.wenhaha.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.wenhaha.serialport.SerialPortResourceContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            SerialPortResourceContext.initialize(this,R.raw.c250);
        } catch (Exception e1) {
            throw  new RuntimeException(e1);
        }
        setContentView(R.layout.activity_main);
    }
}
