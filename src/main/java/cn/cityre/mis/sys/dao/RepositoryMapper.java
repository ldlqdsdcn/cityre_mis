package cn.cityre.mis.sys.dao;

import cn.cityre.mis.sys.model.Repository;

public interface RepositoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Repository record);

    int insertSelective(Repository record);

    Repository selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Repository record);

    int updateByPrimaryKey(Repository record);
}