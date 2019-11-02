package cnhuanji;

import cnhuanji.domain.MailBean;
import org.springframework.core.io.FileSystemResource;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import java.io.UnsupportedEncodingException;

public class JavaMailApp {
    public static void main(String[] args) throws NoSuchProviderException, MessagingException, UnsupportedEncodingException {

        JavaMailUtil.setProperties();

        MailBean mailBean = new MailBean();
        mailBean.setReceiveMail("1340768350@qq.com");
        mailBean.setReceiverName("唏嘘的猪肉佬");
        mailBean.setText("测试文本");
        mailBean.setSuject("测试邮件");

        System.out.println("-----发送简单邮件开始-----");
        JavaMailUtil.sendSimpleMail(mailBean);
        System.out.println("-----发送简单邮件结束-----");

        System.out.println("-----发送Html邮件开始-----");
        JavaMailUtil.sendHtmlMail(mailBean);
        System.out.println("-----发送Html邮件结束-----");

        System.out.println("-----发送附件邮件开始-----");
        JavaMailUtil.sendAttchmentMail(mailBean,"E:\\私人保存\\huan\\DSC_0099.jpg");
        System.out.println("-----发送附件邮件结束");
    }
}
