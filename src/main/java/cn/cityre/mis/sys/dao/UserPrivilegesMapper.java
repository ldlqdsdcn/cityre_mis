package cn.cityre.mis.sys.dao;

import cn.cityre.mis.sys.model.UserPrivileges;

public interface UserPrivilegesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPrivileges record);

    int insertSelective(UserPrivileges record);

    UserPrivileges selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPrivileges record);

    int updateByPrimaryKey(UserPrivileges record);
}