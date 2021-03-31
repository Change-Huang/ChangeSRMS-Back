package com.change.changesrmsback;

import com.change.changesrmsback.mapper.UserMapper;
import com.change.changesrmsback.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChangesrmsBackApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        CommonUtils.formateDate("2020-02-02 07:00:00");
    }

}
