package cn.wenhaha.serialport.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.FunctionMsg;

public class FunctionConetxt {

    private  static  FunctionConetxt functionConetxt;

    private  Map<String,FunctionMsg>  functionMsgMap;

    public FunctionConetxt() {
        this.functionMsgMap=new HashMap<>();
    }

    public  static  FunctionConetxt getSerialPortConfigContext(){
        if (functionConetxt==null){
            functionConetxt = new FunctionConetxt();
        }
        return functionConetxt;
    }

    public  Map<String, FunctionMsg> getFunctionMsgMap() {
        return functionMsgMap;
    }
}
