package cn.ep.service;

import cn.ep.bean.EpCheck;

import java.util.List;

/**
* @author chenshaoxin
* @description 课程模块审核业务逻辑接口定义
* @date 17:20 2019-10-10
* @modified 17:20 2019-10-10
*/
public interface ICheckService {

    /** 审核表 字段
     *  id
     * reason
     * belong
     * status
     * create_time
     * update_time
     * belong_id
     * who
     *
     * 理由
     * 0为审核视频内容，1为审核课程内容
     * 状态
     * 创建时间
     * 审核时间
     * 对应表id，通过belong和belongid确定审核内容
     * 审核人id
     */

    /**
     * 根据审核人id查询所属记录
     * @param userId  审核人id
     * @param belong    0为审核视频内容，1为审核课程内容
     * @param belongId  belong为0时，belongId为视频（章节）id，belong为1时，belongId为课程id，
     * @return List<EpCheck>
     */
    List<EpCheck> getListByWhoAndBelongAndBelongId(long userId, int belong, long belongId);


    /**
     *  审核记录
     * @param useId  审核人id
     * @param id 记录id
     * @param status 0 未审核 1未通过 2 通过
     * @return 成功为true，否则为false
     */
    boolean check(long useId, long id, int status);
    /**
     * 增加一条记录
     * @param epCheck 记录实体
     * @return 插入成功为true，否则为false
     */
    boolean insert(EpCheck epCheck);

    /**
     *  更新一个章节，逻辑删除也属于更新
     * @param  epCheck 记录实体
     * @return 更新成功为true，否则为false
     */
    boolean update(EpCheck epCheck);
    /**
     *  获取一条记录
     * @param  id 记录id
     * @return 更新成功为true，否则为false
     */
    EpCheck getById(long id);

}