package com.xuanru.util;

import com.sun.mail.smtp.SMTPAddressFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * 
 * desc:邮件发送工具（可包含附件、图片）
 * <p>
 * 创建人：Liaoxf 创建日期：2015-12-30
 * </p>
 * 
 * @version V1.0
 */
public class SendEmailUtil {

	private static final Logger logger = LoggerFactory.getLogger(SendEmailUtil.class);

	/**
	 * 根据传入的文件路径创建附件并返回
	 */
	private static MimeBodyPart createAttachment(String fileName) throws Exception {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		attachmentPart.setDataHandler(new DataHandler(fds));
		attachmentPart.setFileName(MimeUtility.encodeText(fds.getName()));// 解决附件名含中文显示乱码的情况
		return attachmentPart;
	}

	/**
	 * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分
	 */
	private static MimeBodyPart createContent(String body, Map<String, String> imageMap)
			throws Exception {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=UTF-8");
		contentMulti.addBodyPart(textBody);

		// 正文的图片部分
		if (imageMap != null) {
			Iterator<String> iter = imageMap.keySet().iterator();
			MimeBodyPart jpgBody = null;
			FileDataSource fds = null;
			String key = "";
			while (iter.hasNext()) {
				key = iter.next();
				if (key != null && !"".equals(key)) {
					jpgBody = new MimeBodyPart();
					fds = new FileDataSource(imageMap.get(key));
					jpgBody.setDataHandler(new DataHandler(fds));
					jpgBody.setContentID(key);
					contentMulti.addBodyPart(jpgBody);
				}
			}
		}

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}

	/**
	 * 根据传入的 Seesion 对象创建混合型的 MIME消息
	 */
	private static MimeMessage createMessage(Session session, String fromEmail,
			List<String> recipients, String subject, String body, Map<String, String> imageMap,
			List<String> attachmentList) throws Exception {
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromEmail));
		// 设置收件人们
		InternetAddress[] addresses = null;
		if (recipients != null) {
			addresses = new InternetAddress[recipients.size()];
			for (int i = 0; i < addresses.length; i++) {
				addresses[i] = new InternetAddress(recipients.get(i));
			}
		} else {// 如果没有收件人，则默认发送给发件人邮箱
			addresses = new InternetAddress[1];
			addresses[0] = new InternetAddress(fromEmail);
		}
		msg.setRecipients(Message.RecipientType.TO, addresses);
		msg.setSubject(subject);

		// 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象
		MimeMultipart allPart = new MimeMultipart("mixed");

		// 创建邮件的各个 MimeBodyPart(附件) 部分
		if (attachmentList != null && attachmentList.size() > 0) {
			for (int i = 0; i < attachmentList.size(); i++) {
				allPart.addBodyPart(createAttachment(attachmentList.get(i)));
			}
		}

		MimeBodyPart content = createContent(body, imageMap);
		allPart.addBodyPart(content);

		// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
		msg.setContent(allPart);
		msg.saveChanges();
		return msg;
	}

	/**
	 * 创建Session对象，此时需要配置传输的协议，是否身份认证
	 */
	private static Session createSession(String protocol) {
		Properties property = new Properties();
		property.setProperty("mail.transport.protocol", protocol);
		property.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(property);
		// 启动JavaMail调试功能，可以返回与SMTP服务器交互的命令信息
		// 可以从控制台中看一下服务器的响应信息
		session.setDebug(true);
		return session;
	}

	/**
	 * 
	 * desc:
	 * <p>
	 * 创建人：Liaoxf , 2015-12-30 下午4:20:13
	 * </p>
	 * 
	 * @param smtpServer
	 *            SMTP服务器
	 * @param fromEmail
	 *            发送邮箱用户名
	 * @param fromPwd
	 *            发送邮箱密码
	 * @param recipients
	 *            收件人邮箱集合
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容（文本或HTML文本）
	 * @param imageMap
	 *            邮件内容中图片信息。key:HTML文本中引用的图片名。value：HTML文本中源图片路径
	 * @param attachmentList
	 *            邮件附件路径集合
	 * @throws Exception
	 */
	public static void sendMail(String smtpServer, String fromEmail, String fromPwd,
			List<String> recipients, String subject, String body, Map<String, String> imageMap,
			List<String> attachmentList) {
		try {
			// 指定使用SMTP协议来创建Session对象
			Session session = createSession("smtp");
			// 使用前面文章所完成的邮件创建类获得 Message 对象
			MimeMessage msg = createMessage(session, fromEmail, recipients, subject, body,
					imageMap, attachmentList);
			// 设置发件人使用的SMTP服务器、用户名、密码

			// 由 Session 对象获得 Transport 对象
			Transport transport = session.getTransport();
			// 发送用户名、密码连接到指定的 smtp 服务器
			transport.connect(smtpServer, fromEmail, fromPwd);

			transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (Exception e) {
			// 判断收件人地址中是否存在无效地址，若存在，则剔除后重新发送
			if (e instanceof SendFailedException) {
				SendFailedException se = (SendFailedException) e;
				Address[] invalidAdd = se.getInvalidAddresses();
				if (invalidAdd != null) {
					for (int i = 0; i < invalidAdd.length; i++) {
						logger.info("无效收件人邮箱地址：[{}]", invalidAdd[i].toString());
						recipients.remove(invalidAdd[i].toString());
					}
				}
			}
			if (e instanceof SMTPAddressFailedException) {
				SMTPAddressFailedException se = (SMTPAddressFailedException) e;
				logger.info("无效收件人邮箱地址：[{}]", se.getAddress().getAddress());
				recipients.remove(se.getAddress().getAddress());
			}

			sendMail(smtpServer, fromEmail, fromPwd, recipients, subject, body, imageMap,
					attachmentList);
		}

	}

	/**
	 * 
	 * desc:解析邮件内容中的图片信息
	 * <p>
	 * 创建人：Liaoxf , 2016-1-28 下午5:59:36
	 * </p>
	 * 
	 * @param body
	 * @return
	 */
	public static List<String> parseHTMLImage(String body) {
		List<String> imageNames = new ArrayList<String>();
		parseImageName(imageNames, body);
		// for (int i = 0; i < imageNames.size(); i++) {
		// System.out.println("======="+ imageNames.get(i));
		// }
		return imageNames;
	}

	private static void parseImageName(List<String> imageNames, String body) {
		if (body != null) {
			int imgIndex = body.indexOf("<img");
			if (imgIndex != -1) {
				body = body.substring(imgIndex + 4);
				body = body.replace("src=\"/cdrf-meeting/image/", "src=\"");
				int srcIndex = body.indexOf("src=");
				int pointIndex = body.indexOf(".");
				if (srcIndex != -1 && pointIndex != -1 && srcIndex + 5 < pointIndex) {
					// System.out.println("srcIndex=" + srcIndex +
					// " pointIndex=" +
					// pointIndex);http://img3.3lian.com/2013/v11/33/d/1.jpg
					String imageName = body.substring(srcIndex + 5, pointIndex);
					body = body.substring(pointIndex + 1);
					if (imageName.indexOf("http:") == -1 && imageName.indexOf("https:") == -1) { // 剔除网络链接图片
						int qmarkIndex = body.indexOf("\"");
						if (qmarkIndex != -1) {
							imageName += "." + body.substring(0, qmarkIndex);
							imageNames.add(imageName);
						}
					}
					parseImageName(imageNames, body);
				}
			}
		}
	}

	// 测试：发送邮件
	public static void main(String[] args) {
		// String path = System.getProperty("catalina.home")
		// + File.separator + "webapps" + File.separator + "cdrf-meeting" +
		// File.separator + "image" + File.separator;
		String subject = "Javamail邮件发送测试！test";
		String body = "<h4>内含附件、图文并茂的邮件测试！！！</h4> <br/>"
				+ "<a href = http://haolloyin.blog.51cto.com/> 蚂蚁</a><br/>"
				+ "<img src = \"cid:logo_jpg\"></a><br/><br/><img src = \"cid:icon\"><h3>xxdd！</h3>";

		body = "<!DOCTYPE html><html><head></head><body><p dir=\"ltr\"><img src=\"/cdrf-meeting/image/app_logo.png\" alt=\"多少啊\" width=\"96\" height=\"96\" />测试<img src=\"/cdrf-meeting/image/icon.jpg\" alt=\"\" width=\"100\" height=\"100\" />的撒范德萨的</p><p dir=\"ltr\">打上单分散</p><p dir=\"ltr\">的萨达</p><p dir=\"ltr\"><strong>大地方撒<img src=\"/cdrf-meeting/image/ktv.jpg\" alt=\"测试\" width=\"720\" height=\"1600\" /></strong></p></body></html>";
		List<String> imageNames = parseHTMLImage(body);
		body = body.replace("src=\"/cdrf-meeting/image/", "src=\"cid:");
		// 收件人集合
		List<String> recipients = new ArrayList<String>();
		recipients.add("boa52048@163.com");
		// recipients.add("lxf@crdtimes.com");
		// recipients.add("352002653@qq.com");
		// 邮件中包含图片的情况
		Map<String, String> imageMap = new Hashtable<String, String>();
		if (imageNames != null && imageNames.size() > 0) {
			for (int i = 0; i < imageNames.size(); i++) {
				imageMap.put(imageNames.get(i),
						"E:\\tomcat\\apache-tomcat-7.0.62\\webapps\\cdrf-meeting\\image\\"
								+ imageNames.get(i));
			}
		}
		// imageMap.put("logo_jpg",
		// "D:\\crd\\work_records\\e-hotel\\design\\app_logo.png");
		// imageMap.put("icon",
		// "D:\\crd\\work_records\\e-hotel\\design\\icon.jpg");
		// 邮件附件文件路径集合
		List<String> attachmentList = new ArrayList<String>();
		// attachmentList.add("D:\\crd\\work_records\\mysql关键字.txt");
		sendMail("smtp.crdtimes.com", "liaoxf@crdtimes.com", "password", recipients, subject, body,
				imageMap, attachmentList);
	}
}