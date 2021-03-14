package com.change.changesrmsback;

import com.change.changesrmsback.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChangesrmsBackApplicationTests {

    @Autowired
    LoginService loginService;

    @Test
    void contextLoads() {
        //loginService.regist("1404525196@qq.com");
    }

}
