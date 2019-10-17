package cn.ep.service;

import cn.ep.bean.EpCourseKind;

import java.util.List;
import java.util.Map;

/**
* @author chenshaoxin
* @description 关于课程模块种类业务逻辑的接口定义
* @date 14:51 2019-10-10
* @modified 14:51 2019-10-10
*/
public interface IKindService {

/**  表字段及解释
 * id
 * kind_name
 * status
 * root
 * update_time
 * create_time
 *
 * 编号
 * 名称
 * 状态
 * 此表记录的是树状结构，此字段记录所属父节点id，如果是根，id为0
 * 例如 大种类：名称：前端 ； id ：1 ；root：0
 *  他的子种类1 名称：js； id ：2；root：1
 * 子种类2  名称：html； id：3；root：1
 * 更新时间
 * 创建时间
 */

    /**
     * 获取种类列表中status字段不为零的map
     * 该map有一定的规律性，key为一级种类，value为其二级种类list集合
     * @param status 状态值 整型
     * @return Map<EpCourseKind,List<EpCourseKind>>
     */
    Map<EpCourseKind,List<EpCourseKind>> getListByStatus(int status);

    /**
     * 获取种类类表中其父节点字段为root、status字段不为零list集合
     * @param status 状态值
     * @param root 父节点
     * @return List<EpCourseKind>
     */
    List<EpCourseKind> getListByRootAndStatus( long root,int status);

    /**
     * 增加一个种类
     * @param epCourseKind 课程种类实体类实例
     * @return 插入成功为true，否则为false
     */
    boolean insert(EpCourseKind epCourseKind);

    /**
     *  根据id 更新一个种类，逻辑删除也属于更新
     * @param epCourseKind 课程种类实体类实例
     * @return 更新成功为true，否则为false
     */
    boolean updateById(EpCourseKind epCourseKind);

    /**
     * 获取所有子种类
     * @return
     */
    List<EpCourseKind> getSubKindList();

    /**
     * 添加一个并提交一条审核
     * @param epCourseKind
     * @return 事务， 成功返回true，否则回滚
     */
    boolean insertAndSendCheck(EpCourseKind epCourseKind);
}
