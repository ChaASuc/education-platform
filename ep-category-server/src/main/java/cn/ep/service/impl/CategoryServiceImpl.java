package cn.ep.service.impl;

import cn.ep.bean.Category;
import cn.ep.bean.CategoryExample;
import cn.ep.client.ProductClient;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.CategoryMapper;
import cn.ep.service.CategoryService;
import cn.ep.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;    // 爆红不管

    @Autowired
    private ProductClient productClient;

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
     * 更新和逻辑删除类别，
     *
     * @param category
     * @return
     */
    @Transactional
    public void update(Category category) {
        boolean success = categoryMapper.updateByPrimaryKeySelective(category) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "种类更新失败");
        }

        // 判断是否逻辑删除
        if (category.getDeleted()) {
        ResultVO resultVO = productClient.updateListByCid(category.getId());
        if (!resultVO.getCode().equals(GlobalEnum.SUCCESS.getCode())) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "产品删除失败");
        }
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

        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category.getDeleted()) {
            return null;
        }
        return category;
    }

    /**
     * 查找所有类别
     *
     * @return
     */
    @Override
    public List<Category> selectAll(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andDeletedEqualTo(false);
        return categoryMapper.selectByExample(categoryExample);
    }
}
