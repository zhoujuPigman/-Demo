package cnhuanji;

import cnhuanji.domain.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class MailUtil {

    /*读取配置文件中的参数*/
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSenderImpl mailSender;// 自动注入的Bean

    Properties properties = new Properties();
    Session session = Session.getInstance(properties);

    /**
     *发送简单邮件
     * @param receiveMail 收件人邮箱
     * @param suject 邮件主题
     * @param text 邮件正文内容
     */
    public void sendMail(String receiveMail,String suject,String text) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = new MimeMessage(session);
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        if (mimeMessage.equals("null")){
            System.out.println("mimeMessage对象为空");
        }
        if (mailSender == null){
            System.out.println("mailSender对象为空");
        }
        //设置发件人
        mimeMessage.setFrom(sender);
        message.setFrom(sender);
        //设置收件人
        mimeMessage.setTo(receiveMail);
        message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress("1340768350@qq.com","USER_CC","utf-8"));
        //设置邮件主题
        mimeMessage.setSubject(suject);
        message.setSubject(suject);
        //设置邮件正文内容
        mimeMessage.setText(text);
        message.setText(text);
        //发送邮件
//        mailSender.send(mimeMessage);

    }

    /**
     * 发送简单邮件
     * @param mailBean 封装好的邮件对象
     * @throws MessagingException
     */
    public void sendMail(MailBean mailBean) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        if (mimeMessage.equals("null")){
            System.out.println("mimeMessage对象为空");
        }
        MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage);
        helper.setFrom(sender);
        helper.setTo(mailBean.getReceiveMail());
        helper.setSubject(mailBean.getSuject());
        helper.setText(mailBean.getText());
        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件-正文是html
     * @param mailBean
     * @throws MessagingException
     */
    public void sendMailHtml(MailBean mailBean) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(sender);
        helper.setTo(mailBean.getReceiveMail());
        helper.setSubject(mailBean.getSuject());
        helper.setText(mailBean.getText(),true);// 这是一个方法，开启html模式
        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件-附件邮件
     * @param mailBean
     * @throws MessagingException
     */
    public void sendMailAttchment(MailBean mailBean) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(sender);
        helper.setTo(mailBean.getReceiveMail());
        helper.setSubject(mailBean.getSuject());
        helper.setText(mailBean.getText(),true);
        helper.addAttachment(mailBean.getAttachmentname(),mailBean.getFile());
        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件-静态资源邮件
     * @param mailBean
     * @throws MessagingException
     */
    public void sendMailInline(MailBean mailBean) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(sender);
        helper.setTo(mailBean.getReceiveMail());
        helper.setSubject(mailBean.getSuject());
        /*一定要首先设置邮件正文内容*/
        helper.setText(mailBean.getText(),true);
        helper.addInline(mailBean.getContentId(),mailBean.getFile());
        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件-模板邮件发送
     * @param mailBean
     * @throws MessagingException
     */
    public void sendMailTemplate(MailBean mailBean) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(sender);
        helper.setTo(mailBean.getReceiveMail());
        helper.setSubject(mailBean.getSuject());
        helper.setText(mailBean.getText(),true);
        mailSender.send(mimeMessage);
    }
}
