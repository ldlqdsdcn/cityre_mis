package cn.cityre.mis.sys.service.test;

import cn.cityre.mis.RootConfig;
import cn.cityre.mis.account.model.AccountUser;
import cn.cityre.mis.sys.model.User;
import cn.cityre.mis.sys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by 刘大磊 on 2017/8/23 14:47.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUsername() {
        AccountUser user=userService.getUserByUsername("administrator");
        Assert.notNull(user,"通过用户名找不到对应的记录");
    }

}
