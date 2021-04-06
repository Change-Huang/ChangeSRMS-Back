package com.change.changesrmsback;

import com.change.changesrmsback.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试工具
 */
@SpringBootTest
class ChangesrmsBackApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

    }
}
