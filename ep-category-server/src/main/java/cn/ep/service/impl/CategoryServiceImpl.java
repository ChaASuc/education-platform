package cn.ep.service.impl;

import cn.ep.bean.Category;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.CategoryMapper;
import cn.ep.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;    // 爆红不管

    /**
     * 保存新类别
     *
     * @param category
     * @return
     */
    @Override
    @Transactional
    public void insert(Category category) {
        boolean success = categoryMapper.insertSelective(category) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "种类操作失败");
        }
    }


    /**
     * 更新和删除类别
     *
     * @param category
     * @return
     */
    public void update(Category category) {
        boolean success = categoryMapper.updateByPrimaryKeySelective(category) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "种类操作失败");
        }
    }

    /**
     * 查找类别
     *
     * @param id
     * @return
     */
    @Override
    public Category select(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 查找所有类别
     *
     * @return
     */
    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectByExample(null);
    }
}
