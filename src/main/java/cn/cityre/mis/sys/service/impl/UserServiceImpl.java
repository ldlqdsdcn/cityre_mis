package cn.cityre.mis.sys.service.impl;

import cn.cityre.mis.sys.dao.UserMapper;
import cn.cityre.mis.sys.model.User;
import cn.cityre.mis.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/8/23 14:36.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @see UserService#getUserByUsername(String)
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        Map param = new HashMap();
        param.put("username", username);
        User user = userMapper.selectByExample(param);
        return user;
    }
}
