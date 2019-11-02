package cnhuanji;

import cnhuanji.domain.MailBean;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class JavaMailUtil {

    /*发件人邮箱*/
    public static String MYEMAILACCOUNT = "1340768350@qq.com";
    /*密码*/
    public static String MYEMAILPASSWORD = "uvgjctgmnfhziehe";
    /*SMTP服务器地址*/
    public static String MYEMAILSMTPHOST = "smtp.qq.com";
    /*收件人邮箱(测试)*/
    public static String RECEIVEMAILACCOUNT = "1340768350@qq.com";

    /*1.创建参数配置，用于连接邮件服务器参数配置*/
    public static Properties properties = new Properties();

    /*2.创建会话*/
    public static Session session;

    /*3.创建邮件传输对象*/
    public static Transport transport;

    /**
     * 配置设置
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public static void setProperties() throws NoSuchProviderException, MessagingException {
        /*使用的协议*/
        properties.setProperty("mail.transport.protocol","smtp");
        /*发件人邮箱SMTP服务器地址*/
        properties.setProperty("mail.smtp.host",MYEMAILSMTPHOST);
        /*需要的请求认证*/
        properties.setProperty("mail.smtp.auth","true");
        session = Session.getInstance(properties);
        session.setDebug(true);
        transport = session.getTransport();
        transport.connect(MYEMAILACCOUNT,MYEMAILPASSWORD);
    }

    /**
     * 创建邮件
     * @param mailBean
     * @return 邮件对象
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static MimeMessage getMimeMessage(MailBean mailBean) throws UnsupportedEncodingException,MessagingException{
        //1.创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //2.设置发送人
        mimeMessage.setFrom(new InternetAddress(MYEMAILACCOUNT,"迷人的猪肉佬","utf-8"));
        //3.设置收件人
        mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(mailBean.getReceiveMail(),mailBean.getReceiverName(),"utf-8"));
        //4.设置邮件主题
        mimeMessage.setSubject(mailBean.getSuject(),"utf-8");

        return mimeMessage;
    }
    /**
     * 发送简单邮件
     * @param mailBean
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void sendSimpleMail(MailBean mailBean) throws MessagingException, UnsupportedEncodingException {
        //1.创建邮件对象
        MimeMessage mimeMessage = getMimeMessage(mailBean);
        //2.设置文本信息
        mimeMessage.setText(mailBean.getText());
        //3.发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
    }


    /**
     * 发送Html邮件
     * @param mailBean
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void sendHtmlMail(MailBean mailBean) throws UnsupportedEncodingException,MessagingException{
        //1.创建邮件对象
        MimeMessage mimeMessage = getMimeMessage(mailBean);
        //2.设置邮件文本信息
        mimeMessage.setText(mailBean.getText(),"utf-8","html");
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
    }

    /**
     * 发送有附件的邮件
     * @param mailBean
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void sendAttchmentMail(MailBean mailBean,String url) throws UnsupportedEncodingException,MessagingException{
        //1.创建邮件对象
        MimeMessage mimeMessage = getMimeMessage(mailBean);
        /*创建附件节点*/
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler handler = new DataHandler(new FileDataSource(url));
        attachment.setDataHandler(handler);
        attachment.setFileName(MimeUtility.encodeText(handler.getName()));

        /*设置文件*/
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(attachment);

        /*设置整个邮件关系*/
        mimeMessage.setContent(multipart);
        /*设置发送时间*/
        mimeMessage.setSentDate(new Date());
        /*保存上面所有设置*/
        mimeMessage.saveChanges();

        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();

    }

    /**
     * 发送附件+图片邮件
     * @param mailBean
     * @param imagePath 图片路径
     * @param filePath 文件路径
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void sendAttchmentImageMail(MailBean mailBean,String imagePath,String filePath) throws MessagingException,UnsupportedEncodingException{
        //1.创建邮件对象
        MimeMessage mimeMessage = getMimeMessage(mailBean);
        mimeMessage.setText(mailBean.getText());

        /*邮件内容创建*/
        MimeBodyPart image = new MimeBodyPart();
        /*读取本地文件*/
        // 创建图片节点
        DataHandler dataHandler = new DataHandler(new FileDataSource(imagePath));
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
        DataHandler handler = new DataHandler(new FileDataSource(filePath));
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

        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
    }

}
