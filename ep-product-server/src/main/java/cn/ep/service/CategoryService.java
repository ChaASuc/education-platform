package cn.ep.service;


import cn.ep.bean.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 保存新类别
     * @param category
     * @return
     */
    void insert(Category category);


    /**
     * 更新类别
     * @param category
     * @return
     */
    void update(Category category);

    /**
     * 查找类别
     * @param id
     * @return
     */
    Category select(int id);

    /**
     * 查找所有类别
     * @return
     */
    List<Category> selectAll();
}
