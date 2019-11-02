package cn.huanji;

public class Qrest {
    public static void main(String[] args) {
        String content = "你好我叫Richard,好高兴认识你";
        String logUri = "E:/私人保存/huan/DSC_0135.jpg";
        String format = "jpg";
        int [] size = new int[]{430,430};
        try{
            new QRCodeFactory().createQRImage(content,format,logUri,size);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
