package cn.cityre.mis.sys.service;

import cn.cityre.mis.sys.model.User;

/**
 * Created by 刘大磊 on 2017/8/23 14:35.
 */
public interface UserService {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
