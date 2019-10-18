package cn.ep.service;


import cn.ep.bean.EpChapter;

import java.util.List;
import java.util.Map;

/**
* @author chenshaoxin
* @description 课程模块章节业务逻辑接口定义
* @date 17:44 2019-10-10
* @modified 17:44 2019-10-10
*/
public interface IChapterService {
    /** 表字段
     * id
     * chapter_name
     * course_id
     * update_time
     * duration
     * status
     * intro
     * url
     * root
     * free
     * create_time
     *
     * 名称
     * 所属课程
     * 更新时间
     * 章节简介
     * 状态
     * 总时长
     * 视频地址
     * 此表记录的是树状结构，此字段记录所属父节点id，如果是根，id为0
     * 此章节是否收费，0为免费，1为收费
     * 创建时间
     *
     */

    //根据课程id查找所属章节

    /**
     * 根据课程id查找所有status字段不为零的章节，
     * 该map有一定的规律性，key为章节简介，value为该章节的子视频的list集合
     * @param id  课程id
     * @param status  0为无效，1为有效
     * @return Map<EpChapter,List<EpChapter>>
     */
    Map<EpChapter,List<EpChapter>> getListByCourseIdAndStatus(long id, int status );
    /**
     * 增加一个章节
     * @param epChapter 课程章节实体类实例
     * @return 插入成功为true，否则为false
     */
    boolean insert(EpChapter epChapter);

    /**
     *  更新一个章节，逻辑删除也属于更新
     * @param  epChapter 课程章节实体类实例
     * @return 更新成功为true，否则为false
     */
    boolean updateById(EpChapter epChapter);

    boolean multiplyInsertAndSendCheck(List<EpChapter> chapters);

    EpChapter getByChapterId(Long Id);
}
