package com.appg.djTalk.common.component;

import java.io.File;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.appg.djTalk.common.bean.EmailBean;
import com.appg.djTalk.common.constants.Constants;

public class MailSender extends JavaMailSenderImpl implements InitializingBean
{
	private static final Log	logger	= LogFactory.getLog(MailSender.class);
	private Boolean				smtpSSLEnabled;
	private String				senderEmail;

	public void setSenderEmail(String senderEmail)
	{
		this.senderEmail = senderEmail;
	}

	public void afterPropertiesSet() throws Exception
	{
		Properties properties = new Properties();
		this.smtpSSLEnabled.booleanValue();
	}

	public void setSmtpSSLEnabled(Boolean smtpSSLEnabled)
	{
		this.smtpSSLEnabled = smtpSSLEnabled;
	}

	public void testEmailSending()
	{
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		//logger.debug("this.senderEmail : " + this.senderEmail);
		System.out.println(this.senderEmail);
		mailMessage.setFrom(this.senderEmail);
		mailMessage.setSubject("test mail1234 ");
		mailMessage.setTo("khm0121@naver.com");
		mailMessage.setText("TEST");

		send(mailMessage);
	}
	
	public void sendEmail(EmailBean bean){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		System.out.println(bean.getSubject());
		//logger.debug("this.senderEmail : " + this.senderEmail);
		
		mailMessage.setFrom(Constants.EMAIL_ID);
		mailMessage.setSubject(bean.getSubject());
		mailMessage.setTo(bean.getTo());
		mailMessage.setText(bean.getMessage());
		
		send(mailMessage);		
	}
	
	public void sendEmailAttach(EmailBean bean, String fileName){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		MimeMessage message = mailSender.createMimeMessage();
		
		File attachFile = new File("/home/" + fileName); // 첨부파일 경로(C:\첨부파일.jpg)
		try{
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom(Constants.EMAIL_ID);
			messageHelper.setSubject(bean.getSubject());
			messageHelper.setTo(bean.getTo());
			messageHelper.setText(bean.getMessage());
			DataSource dataSource = new FileDataSource(attachFile);
			messageHelper.addAttachment(attachFile.getName(), dataSource);
			
			
			mailSender.send(message);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
