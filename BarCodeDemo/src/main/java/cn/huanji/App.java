package cn.huanji;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {

        String url = createCode("迷人的桓记");
        readCode(url);

    }

    public static String createCode(String text){
        int width = 150;
        int height = 150;
        String format = "png";

        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN,2);

        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,width,height,hashMap);
            String url = "e:/excel/"+text+".png";
            Path file = new File("e:/excel/"+text+".png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix,format,file);
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void readCode(String url){
        try{
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File(url);
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            HashMap hashMap = new HashMap();
            hashMap.put(EncodeHintType.CHARACTER_SET,"utf-8");
            Result result = formatReader.decode(binaryBitmap,hashMap);

            System.out.println("解析结果:"+result.toString());
            System.out.println("二维码格式类型:"+result.getBarcodeFormat());
            System.out.println("二维码文本内容:"+result.getText());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
