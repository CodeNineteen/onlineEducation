package com.df;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SpringbootJavaApplicationTests {

    @Test
    public void contextLoads() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encode = pe.encode("111111");
        System.out.println(encode);
        boolean matches = pe.matches("111111",encode);
        System.out.println("============");
        System.out.println(matches);

    }

}
