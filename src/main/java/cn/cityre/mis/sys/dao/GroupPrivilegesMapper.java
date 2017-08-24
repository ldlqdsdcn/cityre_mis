package cn.cityre.mis.sys.dao;

import cn.cityre.mis.sys.model.GroupPrivileges;

public interface GroupPrivilegesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupPrivileges record);

    int insertSelective(GroupPrivileges record);

    GroupPrivileges selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupPrivileges record);

    int updateByPrimaryKey(GroupPrivileges record);
}