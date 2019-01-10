package cn.wenhaha.serialport.bean;

import java.util.List;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.core.DataStitching;
import cn.wenhaha.serialport.util.DataUtil;
import cn.wenhaha.serialport.util.crc.Crc16;

public abstract class FunctionMsg{

    private Function function;

    public  FunctionMsg(Function function){
        this.function=function;
    }


    public Function getFunction() {
        return function;
    }


    public  void send(String  data){

        //获取完整的数据链
        String s = DataStitching.getDataStitching().dataResult(function.getName(), data);

        //去掉空格
        data =s.replaceAll(" ", "");

        //转字节
        byte[] bytes = DataUtil.toBytes(data);

        //加crc
        byte[] data_bytes=addCrc(bytes);

        DataUtil.sendMesg(data_bytes);

    }



    private byte[] addCrc(byte[] data){
        switch (SeriaPortConetxt.getCrc()){
            case 16:
                return Crc16.addCrc(data);
        }
        throw new RuntimeException("暂时还不支持"+SeriaPortConetxt.getCrc()+"类型的CRC校验");
    }


    public abstract  void  read(String name,String[] data,List<String> allData);

}
