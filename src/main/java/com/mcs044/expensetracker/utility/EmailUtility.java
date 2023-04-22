package com.mcs044.expensetracker.utility;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtility {

    @Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

    private final String EMAIL_REGEX = 
    "^[a-zA-Z0-9_+&*-]+(?:\\." 
        + "[a-zA-Z0-9_+&*-]+)*@" 
        + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
        + "A-Z]{2,7}$";

    /**
	 * Send email when an account is created.
	 * 
	 * @param username
	 * @param emailAddress
	 */
	public void sendMail(String username, String emailAddress) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(sender);
		mailMessage.setTo(emailAddress);
		mailMessage.setText("The account for " + username + " has been created.");
		mailMessage.setSubject("MDVerse account created.");
		javaMailSender.send(mailMessage);
		System.out.println("MDVerse account created. Mail Sent Successfully...");
	}

    public boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		if (email == null) return false;
		return pattern.matcher(email).matches();
	}
}
