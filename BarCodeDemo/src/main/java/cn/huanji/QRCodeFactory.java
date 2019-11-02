package cn.huanji;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeFactory {

    /**
     * 生成二维码中间的logo
     * @param matrixImage 生成的二维码
     * @param logUri logo地址
     * @return 带有LOGO的二维码图片
     * @throws IOException LOGO地址找不到会有io异常
     */
    public BufferedImage setMatrixLogo(BufferedImage matrixImage,String logUri) throws IOException {

        // 读取二维码图片，构建绘图对象
        Graphics2D g2 =matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /*读取Logo图片*/
        BufferedImage logo = ImageIO.read(new File(logUri));

        /*开始绘制图片*/
        g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2,matrixWidth/5,matrixHeigh/5,null);
        /*绘制边框*/
        BasicStroke basicStroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        /*设置笔画对象*/
        g2.setStroke(basicStroke);
        /*设置弧度的圆角矩形*/
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2,matrixHeigh/5*2,matrixWidth/5,matrixHeigh/5,20,20);
        g2.setColor(Color.white);
        /*绘制圆弧矩形*/
        g2.draw(round);

        /*设置logo ，加上一道灰色边框*/
        BasicStroke stroke = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        /*设置笔画对象*/
        g2.setStroke(stroke);
        RoundRectangle2D.Float  round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2,matrixHeigh/5*2+2,matrixWidth/5-4,matrixHeigh/5-4,20,20);
        g2.setColor(new Color(128,128,128));
        g2.draw(round2);
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    /**
     * 创建二维码图片
     * @param content 二维码内容
     * @param format 二维码格式
     * @param logUri 二维码中间图片的logo地址
     * @param size 用于设定图片的大小（可变参数，宽高
     * @throws IOException
     * @throws WriterException
     */
    public void createQRImage(String content,String format,String logUri,int ...size) throws IOException, WriterException {
        int width = 430; //二维码宽度
        int height = 430; //二维码高度

        //如果存储大小的不为空，那么对图片的大小进行设定
        if (size.length == 2){
            width=size[0];
            height=size[1];
        }else if (size.length==1){
            width=height=size[0];
        }

        Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
        /*指定纠错等级，纠错等级（L 7%、M 15%、Q 25%、H 30%）*/
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hints.put(EncodeHintType.MARGIN,1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        /*设置默认生成地址*/
        String filePath = "e:/excel/"+content+".jpg";
        /*生成二维码图片文件*/
        File outputFile = new File(filePath);
        /*指定输出路径*/
        System.out.println("输出文件路径为:"+filePath);

        MatrixToImageWriter.writeToFile(bitMatrix,format,outputFile,logUri);
    }

}
