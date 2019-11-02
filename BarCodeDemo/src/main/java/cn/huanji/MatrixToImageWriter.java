package cn.huanji;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用，当然你也可以自己写个
 *   生产条形码的基类
 */
public class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000; //设置图案颜色
    private static final int WHITE = 0xFFFFFFFF; //设置背景色

    private MatrixToImageWriter(){

    }

    public static BufferedImage toBufferedImage(BitMatrix matrix){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<width;i++){
            for (int j = 0;j<height;j++){
                image.setRGB(i,j,(matrix.get(i,j) ? BLACK : WHITE));
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file,String logUri) throws IOException {
        System.out.println("写入文件");
        BufferedImage image = toBufferedImage(matrix);
        //设置图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image,logUri);

        if (!ImageIO.write(image,format,file)){
            System.out.println("生成图片失败！");
        }else {
            System.out.println("生成图片成功！");
        }
    }

    public static void writeStream(BitMatrix matrix, String format, OutputStream stream,String logUri) throws IOException, WriterException {
        BufferedImage image = toBufferedImage(matrix);
        /*设置logo图标*/
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image,logUri);
        if (!ImageIO.write(image,format,stream)){
            System.out.println("生成图片失败！");
        }
    }
}
