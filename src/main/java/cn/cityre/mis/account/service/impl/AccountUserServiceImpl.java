package cn.cityre.mis.account.service.impl;

import cn.cityre.mis.account.dao.AccountUserMapper;
import cn.cityre.mis.account.model.AccountUser;
import cn.cityre.mis.account.service.AccountUserService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.pagination.dto.PageMyBatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 刘大磊 on 2017/8/23 16:10.
 */
@Service
public class AccountUserServiceImpl implements AccountUserService {
    @Autowired
    private AccountUserMapper accountUserMapper;


    @Override
    public PageMyBatis<AccountUser> getAccountUser(int firstRow, int rows) {
        return accountUserMapper.selectList(null, new RowBounds(firstRow, rows));
    }
}
