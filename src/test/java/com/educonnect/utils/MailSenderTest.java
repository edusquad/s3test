package com.educonnect.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.educonnect.MailSource;
import com.educonnect.domain.user.Token;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MailSenderTest {

private static final Logger log = LoggerFactory.getLogger(MailSenderTest.class);

        @Value("${local.server.port}")
        private int serverPort;

        @Value("${property.host}")
        String RECEIVER_MAIL_ADDRESS;

//        @Value("${property.sender}")
//        public String MAIL_SENDER;

//        @Value("${property.sender_password}")
//        public String MAIL_SENDER_PASSWORD;

//        @Value("${property.sender_url}")
//        public String DOMAIN;

        @Before
        public void setup() {
                RestAssured.port = serverPort;
        }

        @Bean
        public MailSource mailSources() {
            return new MailSource();
        }
        @Test
        public void mailSendTest() {
        		MailSource ms = this.mailSources();
//        		log.debug(ms.getSender());
//                MailSource ms = new MailSource(MAIL_SENDER, MAIL_SENDER_PASSWORD, DOMAIN);
//        	log.debug(RECEIVER_MAIL_ADDRESS);
//                Token token = new Token(RECEIVER_MAIL_ADDRESS);
//                MailSender joinAuthMail = new MailSender(RECEIVER_MAIL_ADDRESS, ms);
//                joinAuthMail.sendMailForToken(token);
        }

}