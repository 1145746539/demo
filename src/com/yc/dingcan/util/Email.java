package com.yc.dingcan.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;



public class Email {
	public static String ver;
	private static Logger logger=Logger.getLogger(Email.class);
	private String myEmailAccount="";//发件人
	private String myEmailpassword="";//第三方密码
	private String myEmailSMTPHost="";//smtp协议
	private String reciveMailAccount="";//接收人
	
	public Email(String myEmailAccount, String myEmailpassword, String myEmailSMTPHost, String reciveMailAccount) {
		super();
		this.myEmailAccount = myEmailAccount;
		this.myEmailpassword = myEmailpassword;
		this.myEmailSMTPHost = myEmailSMTPHost;
		this.reciveMailAccount = reciveMailAccount;
	}

	public Email() {
		super();
	}

	public void email(){
		//配置参数
		Properties props=new Properties();						//参数配置
		props.setProperty("mail.transport.protocol", "smtp");	//使用的协议
		props.setProperty("mail.smtp.host", myEmailSMTPHost);	//发件人的邮箱地址
		props.setProperty("mail.smtp.auth","true");				//需要请求认证
		//根据配置创建会话对象，用于邮件服务器交互
		Session session =Session.getInstance(props);
		session.setDebug(false);
		//创建一封邮件
		try {
			MimeMessage message=createMimeMessage(session,myEmailAccount,reciveMailAccount);
			//根据Session获取邮件传输对象
			Transport transport=session.getTransport();
			transport.connect(myEmailAccount,myEmailpassword);
			transport.sendMessage(message, message.getAllRecipients());
			//关闭连接
			transport.close();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private MimeMessage createMimeMessage(Session session, String sendMail, String reciveMail) throws Exception{
		 // 根据session创建一个邮件消息
		MimeMessage message = new MimeMessage(session);
        // 设置邮件消息的发送者
        message.setFrom(new InternetAddress(sendMail,"源程订餐有限公司","UTF-8"));
        // 创建邮件的接收者地址，并设置到邮件消息中
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(reciveMail,"xx用户","UTF-8"));
        // 设置邮件消息的主题
        message.setSubject("验证码","UTF-8");
        // 设置邮件消息的主要内容
        Email e=new Email();
        ver=e.getString();
        message.setContent("您的验证码为："+ver,"text/html;charset=UTF-8");
        // 设置邮件消息发送的时间
        message.setSentDate(new Date());
        // 发送邮件
        message.saveChanges();
        return message;
	}
	
	//随机数
	public String getString() {
		String str="";
		Random r=new Random();
		for(int i=0;i<6;i++){
			str+=r.nextInt(10);
		}
		return str;
	}
	
//	public static void main(String[] args) {
//		Email e=new Email("18397716367@163.com","zs111111", "smtp.163.com", "1145746539@qq.com");
//		e.email();
//		logger.debug(e.getString());
//	}

}
