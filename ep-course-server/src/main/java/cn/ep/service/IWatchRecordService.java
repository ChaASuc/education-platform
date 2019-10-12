package cn.ep.service;

import cn.ep.bean.EpWatchRecord;

import java.util.List;

/**
* @author chenshaoxin
* @description 课程模块观看记录业务逻辑接口定义
* @date 17:50 2019-10-10
* @modified 17:50 2019-10-10
*/
public interface IWatchRecordService {
    /** 表字段
     * id
     * watch_time
     * update_time
     * status
     * user_id
     * chapter_id
     * create_time
     * course_id
     * 单位毫秒，最长1000小时
     * 更新时间
     * 状态
     * 用户id
     * 章节id
     * 创建时间
     * 课程id
     */

    /**
     * 增加一条观看记录
     * @param record 课程id 章节id 状态 单位毫秒 不为null
     * @return 成功返回true
     */
    boolean insert(EpWatchRecord record);


    /**
     * 通过用户id课程id查询该用户该课程的观看记录
     * @param userId 用户id
     * @param courseId 课程id
     * @param status 0无效，1有效
     * @return list
     */
    List<EpWatchRecord> selectByUserIdAndCourseIdAndStatus(long userId, long courseId,int status);

    /**
     * 更新一条记录
     * @param record id不为null
     * @return 成功返回true
     */
    boolean update(EpWatchRecord record);
}
