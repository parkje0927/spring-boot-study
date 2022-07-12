package org.zerock.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String password = "1111";
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("encodedPassword = " + encodedPassword);
//        encodedPassword = $2a$10$X/SMSniEQIMHc83VXwj0s.YYe3C8cMoj45RDJrNp6WF7ozn0mdwbG

        boolean matches = passwordEncoder.matches(password, encodedPassword);
        System.out.println("matches = " + matches);
    }
}
