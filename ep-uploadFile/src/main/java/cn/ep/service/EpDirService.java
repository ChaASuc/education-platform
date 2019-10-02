package cn.ep.service;

import cn.ep.bean.EpDir;
import cn.ep.dto.EpDirDto;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
public interface EpDirService {

    /**
     * 创建文件夹
     * @param dir
     */
    void insert(EpDir dir);


    /**
     * 更新文件夹
     * @param dir
     */
    void update(EpDir dir);

    /**
     * 根据文件夹id物理删除
     * @param dirId
     */
    void deleteById(Long dirId);

    /**
     * 根据文件夹id集合物理删除
     * @param dirIds
     */
    void deleteByIds(List<Long> dirIds);
    /**
     * 查看所有文件夹
     * @return
     */
    List<EpDirDto> selectAll();

    /**
     * 查看文件夹
     * @param dirId
     * @return
     */
    EpDir selectById(Long dirId);
}
