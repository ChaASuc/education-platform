package cn.ep.service;

import cn.ep.bean.EpDept;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description 部门业务层
 * @Since 1.0.0
 */
public interface EpDeptService {

    /**
     * 创建部门
     * @param epDept
     */
    void insert(EpDept epDept);

    /**
     * 更新或逻辑删除部门
     * @param epDept
     */
    void update(EpDept epDept);

    /**
     * 获取所有有效部门
     */
    List<EpDept> getAll();

}
