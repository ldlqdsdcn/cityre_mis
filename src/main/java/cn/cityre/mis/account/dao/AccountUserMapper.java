package cn.cityre.mis.account.dao;

import cn.cityre.mis.account.model.AccountUser;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.pagination.dto.PageMyBatis;

import java.util.List;
import java.util.Map;

public interface AccountUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountUser record);

    int insertSelective(AccountUser record);

    AccountUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountUser record);

    int updateByPrimaryKey(AccountUser record);


    List<AccountUser> selectList(Map<String,Object> param);

    PageMyBatis<AccountUser> selectList(Map<String, Object> param, RowBounds rowBounds);


}