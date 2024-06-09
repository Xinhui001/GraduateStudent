package com.example.graduateinfo;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.example.graduateinfo.model.domain.Users;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SampleTest {

    @Resource
    private UsersMapper usersMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Users> userList = usersMapper.selectList(null);
        Assert.isTrue(6 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}