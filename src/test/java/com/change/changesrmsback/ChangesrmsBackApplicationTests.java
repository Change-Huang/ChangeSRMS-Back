package com.change.changesrmsback;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChangesrmsBackApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectUserList(new Page(), null);
        for (User user : users) {
            System.out.println(user);
        }

        System.out.println(userMapper.selectUserCount(null));
    }

}
