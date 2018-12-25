package cn.wenhaha.serialport.auto;

import com.example.x6.serial.SerialPort;

import java.util.Map;
import java.util.Set;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.config.AutoConfigInf;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;

public class AutoNewFunctionMsg implements AutoConfigInf{

    private FunctionConetxt functionConetxt;

    private AutoNewFunctionMsg(FunctionConetxt functionConetxt) {
        this.functionConetxt=functionConetxt;
    }

    public static AutoNewFunctionMsg newInitialize(){
        return new AutoNewFunctionMsg(FunctionConetxt.getSerialPortConfigContext());
    }


    @Override
    public void start() throws Exception {

        Map<String, Function> mapFunction = SerialPortConfigContext.getSerialPortConfigContext().getMapFunction();

        for (String key : mapFunction.keySet()) {
            Function function = mapFunction.get(key);
            String className = function.getClassName();


            Class clazz = Class.forName(className);

            FunctionMsg o =(FunctionMsg) clazz.getDeclaredConstructor(Function.class).newInstance(function);

            functionConetxt.getFunctionMsgMap().put(function.getName(),o);

        }


    }
}
