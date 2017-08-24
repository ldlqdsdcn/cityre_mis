package cn.cityre.mis.sys.service.impl;

import cn.cityre.mis.account.dao.AccountUserMapper;
import cn.cityre.mis.account.model.AccountUser;
import cn.cityre.mis.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/8/23 14:36.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * @param username
     * @return
     * @see UserService#getUserByUsername(String)
     */
    public AccountUser getUserByUsername(String username) {
        Map param = new HashMap();
        param.put("userId", username);
        List<AccountUser> accountUserList = accountUserMapper.selectList(param);
        if (accountUserList.size() > 0) {
            return accountUserList.get(0);
        }
        return null;
    }
}
