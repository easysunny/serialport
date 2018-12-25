package cn.wenhaha.serialport.core;

import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.util.ByteUtil;
import cn.wenhaha.serialport.util.DataUtil;
import cn.wenhaha.serialport.util.crc.Crc16;

public class DataStitching {

    private  static  DataStitching dataStitching;
    private  SerialPortConfigContext serialPortConfigContext;
    private  List<String> structure ;
    private  FunctionConetxt functionConetxt ;

    public DataStitching() {
        this.serialPortConfigContext =serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
        this.structure= serialPortConfigContext.getStructure();
        this.functionConetxt=FunctionConetxt.getSerialPortConfigContext();
    }

    public  static  DataStitching getDataStitching(){
        if (dataStitching==null){
            dataStitching = new DataStitching();
        }
        return dataStitching;
    }


    public  String dataResult(String name,String dataMsg){

        StringBuffer data=new StringBuffer();

        FunctionMsg functionMsg = functionConetxt.getFunctionMsgMap().get(name);

        Function function = functionMsg.getFunction();


        Map<String, Object> mapRootData = serialPortConfigContext.getMapRootData();

        for (String key :structure) {
            //地址
            if (key.equals(LabelFunctionEnum.ADDRESS.getMarking())){
                data.append(function.getAddress());
            }

            //数据
            else  if (key.equals("function")){
                data.append(dataMsg);
            }

            //crc
            else  if (key.equals(LabelRootEnum.CRC.getMarking())) continue;

            //长度
            else if (key.equals(LabelRootEnum.LENGTH.getMarking())){
                data.append(getLength(dataMsg));
            }

            else {
                String o =(String) mapRootData.get(key);
                data.append(o);
            }

        }
        //添加crc


        return data.toString();

    }






    public String getLength(String dataMsg){
        Integer length = serialPortConfigContext.getLength();
        if (length!=null&&length!=0){
            return Integer.toHexString(length);
        }

        int i=dataMsg.replaceAll(" ","").length()/2;
        String length_str = Integer.toHexString(i);
        if (length_str.length()==1){
            length_str="0"+length_str;
        }

        return length_str;
    }







}
