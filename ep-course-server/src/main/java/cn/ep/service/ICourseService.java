package cn.ep.service;

import cn.ep.bean.EpCourse;

import java.util.List;

/**
* @author chenshaoxin
* @description 关于课程模块课程业务逻辑的接口定义
* @date 15:41 2019-10-10
* @modified 15:41 2019-10-10
*/
public interface ICourseService {

    /** 课程表字段及解释
     *id
     * course_name
     * update_time
     * create_time
     * status
     * free
     * goal
     * overview
     * open_time
     * kind_id
     * user_id
     * price
     * watch_count
     * 表示id
     * 名称
     * 更新时间
     * 创建时间
     * 0为无效，1为已通过，2为未通过，3为未审核
     * 0免费1收费
     * 最大1000个中文字符
     * 最大1000个中文字符
     * 开课时间
     * 种类id
     * 上传课程者id
     * 价格最高99999.99
     * 订阅此课程人数
     */



    /**
     * 根据条件查询status有效的课程列表
     * @param epCourse 注意 epCourse中的status、free有固定值 status 0为无效，1为已通过，2为未通过，3为未审核
     *                  * free 0免费1收费
     * @return
     */
    List<EpCourse> getListByEpCourse(EpCourse epCourse);


    /**
     * 查找含有关键字且status的课程列表，全文搜索
     * @param key  关键字
     * @param status  状态 status 0为无效，1为已通过，2为未通过
     * @return
     */
    List<EpCourse> getListByKey(String key,int status);

    /**
     * 增加一个课程
     * @param epCourse 课程实体类
     * @return
     */
    boolean insert(EpCourse epCourse);
    //更新一个课程

    /**
     * 更新一个课程
     * @param epCourse 课程实体类
     * @return
     */
    boolean update(EpCourse epCourse);

}
