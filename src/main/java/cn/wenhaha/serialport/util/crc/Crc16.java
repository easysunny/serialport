package cn.wenhaha.serialport.util.crc;


public class Crc16 {
    public static AlgoParams Crc16Arc = new AlgoParams("CRC-16/ARC", 16, 0x8005, 0x0, true, true, 0x0, 0xBB3D);

    public static byte[]  getCrc(byte[] b){
        CrcCalculator calculator = new CrcCalculator(Crc16.Crc16Arc);
        long calc = calculator.Calc(b, 0, b.length);
        byte[] b1=new byte[2];
        putLong(b1,calc,0);
        return  b1;
    }

    public static byte[]  addCrc(byte[] bytes){
        byte[] crc = Crc16.getCrc(bytes);
        byte[] b1 = new byte[bytes.length + crc.length];
        for (int i=0;i<bytes.length;i++){
            b1[i]=bytes[i];
        }
        b1[b1.length-2]=crc[0];
        b1[b1.length-1]=crc[1];
        return  b1;
    }



    public static void putLong(byte[] bb, long x, int index) {

        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }


}