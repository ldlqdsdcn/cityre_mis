package cn.cityre.mis.account.service;

import cn.cityre.mis.account.model.AccountUser;
import org.mybatis.pagination.dto.PageMyBatis;

/**
 * Created by 刘大磊 on 2017/8/23 16:10.
 */
public interface AccountUserService {
    /**
     * 获取 账户信息分页查询
     * @param firstRow
     * @param rows
     * @return
     */
    PageMyBatis<AccountUser> getAccountUser(int firstRow,int rows);
}
