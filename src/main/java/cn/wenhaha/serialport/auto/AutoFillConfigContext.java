package cn.wenhaha.serialport.auto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.config.AutoConfigInf;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.util.XmlJsonUtils;

/**
 * 填充配置
 */
public class AutoFillConfigContext implements AutoConfigInf{

    private static final String TAG = "AutoFillConfigContext";
    private static SerialPortConfigContext serialPortConfigContext;

    public static AutoFillConfigContext newInitialize(){
        serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
        return new AutoFillConfigContext();
    }


    /**
     * 填充基本类型
     * @throws JSONException
     */
    private void fillingBasis() throws JSONException {

        //2个必须的
        String head = XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.HEAD.getMarking()).getString("value");
        serialPortConfigContext.setHead(head);

        String structure = XmlJsonUtils.getJsonObject().getString(LabelRootEnum.STRUCTURE.getMarking()).trim();
        serialPortConfigContext.setStructure(Arrays.asList(structure.split(",")));

        //下面可能为空
        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.CRC.getMarking()))
            serialPortConfigContext.setCrc(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.CRC.getMarking()).getInt("value"));


        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.LENGTH.getMarking()))
            serialPortConfigContext.setLength(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.LENGTH.getMarking()).getInt("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.DEBUG.getMarking()))
            serialPortConfigContext.setDebug(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.DEBUG.getMarking()).getBoolean("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.MILLISECOND.getMarking()))
            serialPortConfigContext.setMillisecond(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.MILLISECOND.getMarking()).getLong("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.READSPEED.getMarking()))
            serialPortConfigContext.setReadSpeed(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.READSPEED.getMarking()).getLong("value"));


        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.SERIALPORT.getMarking()))
            serialPortConfigContext.setSerialPort(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.SERIALPORT.getMarking()).getString("value"));

    }



    /**
     * 填充MapRootData字段
     */
    private void fillingMapRootData() throws JSONException {
        List<String> rootDocumentElement = XmlJsonUtils.getRootDocumentElement();
        for (String key :
                rootDocumentElement) {
            if (key.equals(LabelRootEnum.STRUCTURE.getMarking())) continue;
            Object o = XmlJsonUtils.getJsonObject().getJSONObject(key).get("value");
            serialPortConfigContext.getMapRootData().put(key,o);
        }
    }

    /**
     * 填充function
     * @throws JSONException
     */
    private void fillingFunction() throws JSONException {
        
        JSONArray jsonArray = XmlJsonUtils.getJsonObject().getJSONArray(LabelRootEnum.FUNCTION.getMarking());

        for (int i = 0; i < jsonArray.length(); i++) {
            Function f = new Function();
            JSONObject function = jsonArray.getJSONObject(i);

            f.setAddress(function.getJSONObject(LabelFunctionEnum.ADDRESS.getMarking()).getString("value"));
            f.setName(function.getString(LabelFunctionEnum.NAME.getMarking()));

            String className = null;
            try {
                className = function.getJSONArray(LabelFunctionEnum.CLASS.getMarking()).getJSONObject(0).getString("content");
            } catch (JSONException e) {
                className = function.getJSONArray(LabelFunctionEnum.CLASS.getMarking()).getJSONObject(0).getString("value");
            }
            f.setClassName(className);


            Function function1 = serialPortConfigContext.getMapFunction().get(f.getName());
            if (function1!=null) throw new RuntimeException("name属性不能重复："+f.getName());
            serialPortConfigContext.getMapFunction().put(f.getName(),f);

        }
        
        
        
    }


    /**
     * 填充全部
     * @throws JSONException
     */
    private void filling() throws JSONException {
        fillingBasis();
        fillingMapRootData();
        fillingFunction();
    }




    @Override
    public void start() throws Exception {
        filling();
    }
}
