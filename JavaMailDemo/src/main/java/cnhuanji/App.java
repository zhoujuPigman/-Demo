package cnhuanji;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class App {

    public static String MYEMAILACCOUNT = "1340768350@qq.com";
    /*密码*/
    public static String MYEMAILPASSWORD = "uvgjctgmnfhziehe";
    /*SMTP服务器地址*/
    public static String MYEMAILSMTPHOST = "smtp.qq.com";
    /*收件人邮箱*/
    public static String RECEIVEMAILACCOUNT = "1340768350@qq.com";

    public static void main(String[] args) throws UnsupportedEncodingException,MessagingException{
        /*1.创建参数配置，用于连接邮件服务器参数配置*/
        Properties properties = new Properties();
        /*使用的协议*/
        properties.setProperty("mail.transport.protocol","smtp");
        /*发件人邮箱SMTP服务器地址*/
        properties.setProperty("mail.smtp.host",MYEMAILSMTPHOST);
        /*需要的请求认证*/
        properties.setProperty("mail.smtp.auth","true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(session,MYEMAILACCOUNT,RECEIVEMAILACCOUNT);
        Transport transport = session.getTransport();
        transport.connect(MYEMAILACCOUNT,MYEMAILPASSWORD);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    /**
     * 创建一封复杂邮件(文本+图片+附件)
     * @param session 会话对象
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static MimeMessage createMimeMessage(Session session,String sendMail,String receiveMail) throws UnsupportedEncodingException, MessagingException {
        //1.创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //2.发件人
        mimeMessage.setFrom(new InternetAddress(sendMail,"迷人的猪肉佬","utf-8"));
        //3.收件人
        mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(receiveMail,"迷人的桓记","utf-8"));
        //4.邮件主题
        mimeMessage.setSubject("Test邮件（文本+图片+附件）","utf-8");
        /*邮件内容创建*/
        MimeBodyPart image = new MimeBodyPart();
        /*读取本地文件*/
        // 创建图片节点
        DataHandler dataHandler = new DataHandler(new FileDataSource("E:\\私人保存\\huan\\DSC_0099.jpg"));
        /*将图片数据添加到节点*/
        image.setDataHandler(dataHandler);
        /*为该节点设置唯一ID*/
        image.setContentID("image_test");

        //创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一个图片<br/><img src='cid:image_test'/>","text/html;charset=UTF-8");

        MimeMultipart text_image = new MimeMultipart();
        text_image.addBodyPart(text);
        text_image.addBodyPart(image);
        text_image.setSubType("related");//关联关系
        MimeBodyPart textImage = new MimeBodyPart();
        textImage.setContent(text_image);

        /*创建附件节点*/
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler handler = new DataHandler(new FileDataSource("E:\\私人保存\\huan\\DSC_0099.jpg"));
        attachment.setDataHandler(handler);
        attachment.setFileName(MimeUtility.encodeText(handler.getName()));

        /*设置文件+图片+附件关系*/
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(textImage);
        multipart.addBodyPart(attachment);
        multipart.setSubType("mixed");

        /*设置整个邮件关系*/
        mimeMessage.setContent(multipart);
        /*设置发送时间*/
        mimeMessage.setSentDate(new Date());
        /*保存上面所有设置*/
        mimeMessage.saveChanges();

        return mimeMessage;

    }

}
