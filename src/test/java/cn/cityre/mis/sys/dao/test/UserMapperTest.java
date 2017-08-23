package cn.cityre.mis.sys.dao.test;

import cn.cityre.mis.RootConfig;
import cn.cityre.mis.sys.dao.UserMapper;
import cn.cityre.mis.sys.model.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/8/22 15:45.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@TestPropertySource(locations= "classpath:application-test.properties")
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test

    public void testPaging() {

        System.out.println("hello");
        List<User> list=userMapper.selectList(null);
        System.out.println(list.size());
        List<User> list2=userMapper.selectList(null,new RowBounds(0,10));
        System.out.println(list2.getClass().getName());
        System.out.println(list2.size());
        Assert.assertTrue(list2.size()>0&&list2.size()<=10);
    }
}
