
package cn.ep.service.impl;

import cn.ep.bean.*;
import cn.ep.courseenum.ChapterEnum;
import cn.ep.courseenum.CheckEnum;
import cn.ep.courseenum.CourseEnum;
import cn.ep.courseenum.CourseKindEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpCheckMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.ICheckService;
import cn.ep.service.ICourseService;
import cn.ep.service.IKindService;
import cn.ep.utils.IdWorker;
import cn.ep.utils.Oauth2Util;
import cn.ep.vo.CheckVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class ICheckServiceImpl implements ICheckService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private EpCheckMapper checkMapper;

    @Autowired private IChapterService chapterService;
    @Autowired private ICourseService courseService;
    @Autowired private IKindService kindService;


    @Override
    public List<EpCheck> getListByEpCheck(EpCheck epCheck) {
        EpCheckExample checkExample = new EpCheckExample();
        EpCheckExample.Criteria criteria = checkExample.createCriteria();
        if (epCheck.getBelong() != null)
            criteria.andBelongEqualTo(epCheck.getBelong());
        if (epCheck.getBelongId() != null)
            criteria.andBelongIdEqualTo(epCheck.getBelongId());
        if (epCheck.getCreateTime() != null)
            criteria.andCreateTimeEqualTo(epCheck.getCreateTime());
        if (epCheck.getId() != null)
            criteria.andIdEqualTo(epCheck.getId());
        if (epCheck.getReason() != null)
            criteria.andReasonEqualTo(epCheck.getReason());
        if (epCheck.getStatus() != null)
            criteria.andStatusEqualTo(epCheck.getStatus());
        if (epCheck.getUpdateTime() != null)
            criteria.andUpdateTimeEqualTo(epCheck.getUpdateTime());
        if (epCheck.getWho() != null)
            criteria.andWhoEqualTo(epCheck.getWho());
        return checkMapper.selectByExample(checkExample);
    }

    @Override
    public List<EpCheck> getListByWhoAndBelongAndBelongId(long userId, int belong, long belongId) {
        EpCheck check = new EpCheck();
        check.setWho(userId);
        check.setBelong(belong);
        check.setBelongId(belongId);
        return getListByEpCheck(check);
    }


    @Override
    public List<EpCheck> getListByWhoAndBelongAndBelongIdAndStatus(long userId, int belong, long belongId, int status) {
        EpCheck check = new EpCheck();
        check.setWho(userId);
        check.setBelong(belong);
        check.setBelongId(belongId);
        check.setStatus(status);
        return getListByEpCheck(check);
    }

    @Override
    public boolean insert(EpCheck epCheck) {
        epCheck.setId(idWorker.nextId());
        return checkMapper.insertSelective(epCheck) > 0;
    }

    @Override
    public boolean update(EpCheck epCheck) {
        return checkMapper.updateByPrimaryKeySelective(epCheck) > 0;
    }

    @Override
    public EpCheck getById(long id) {
        return checkMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<CheckVO> getListByUserIdAndTypeAndPageAndChecked(Long userId, int type, int page,Integer checked) {
        PageHelper.startPage(page,20);
        EpCheckExample checkExample = new EpCheckExample();
        EpCheckExample.Criteria criteria = checkExample.createCriteria();
        if (userId != null)
            criteria.andWhoEqualTo(userId);
        if (type == 1){  //未审核
            criteria.andStatusEqualTo(CheckEnum.UNCHECKED_STATUS.getValue());
        } else if(type == 2) {  //已审核
            criteria.andStatusNotEqualTo(CheckEnum.UNCHECKED_STATUS.getValue());
        } else{    //所有
            ;
        }
        if (checked!=null)
            criteria.andBelongEqualTo(checked);
        checkExample.setOrderByClause("create_time desc");
        List<EpCheck> checkList = checkMapper.selectByExample(checkExample);
        PageInfo<EpCheck> checkInfo = new PageInfo<>(checkList);
        PageInfo<CheckVO> checkVOPageInfo = new PageInfo<>();
        checkVOPageInfo.setTotal(checkInfo.getTotal());
        List<CheckVO> checkVOList = new ArrayList<>();
        for (EpCheck check :
                checkList) {
            CheckVO checkVO = new CheckVO();
            checkVO.setRecord(check);
            if (check.getBelong() == CheckEnum.CHECK_COURSE.getValue()){
                checkVO.setChecked(courseService.getByCourseId(check.getBelongId()));
                checkVO.setType(CheckEnum.CHECK_COURSE);
            } else if(check.getBelong() == CheckEnum.CHECK_VIDEO.getValue()){
                checkVO.setChecked(chapterService.getByChapterId(check.getBelongId()));
                checkVO.setType(CheckEnum.CHECK_VIDEO);
            } else{
                checkVO.setChecked(kindService.getByKindId(check.getBelongId()));
                checkVO.setType(CheckEnum.CHECK_COURSE_KIND);
            }
            checkVOList.add(checkVO);
        }
        checkVOPageInfo.setList(checkVOList);
        return checkVOPageInfo;
    }

    @Override
    public PageInfo<CheckVO> getAllCheckListByPageAndChecked(int page,int checked) {
        return getListByUserIdAndTypeAndPageAndChecked(null,0,page,checked);
    }

    @Override
    @Transactional
    public CheckEnum checkAndSetStatus(long checkId, int status,long userId) {
        //todo 从汉槟获取用户id,好了；
        //long userId = 1L;
        EpCheck epCheck = getById(checkId);
        if (userId != epCheck.getWho())
            throw  new GlobalException(GlobalEnum.OPERATION_ERROR,"你没有审批这条记录的权限");
        check(epCheck,status);
        if (epCheck.getBelong() == CheckEnum.CHECK_VIDEO.getValue()){
            EpChapter epChapter = new EpChapter();
            epChapter.setId(epCheck.getBelongId());
            epChapter.setStatus(status == CheckEnum.CHECK_NOT_PASS.getValue()? ChapterEnum.INVALID_STATUS.getValue():ChapterEnum.VALID_STATUS.getValue());
            chapterService.updateById(epChapter);
            return CheckEnum.CHECK_VIDEO;
        } else if (epCheck.getBelong() == CheckEnum.CHECK_COURSE.getValue()){
            EpCourse epCourse = new EpCourse();
            epCourse.setId(epCheck.getBelongId());
            epCourse.setStatus(status == CheckEnum.CHECK_NOT_PASS.getValue()? CourseEnum.CHECK_NOT_PASS.getValue() : CourseEnum.CHECK_PASS.getValue());
            courseService.updateById(epCourse);
            return CheckEnum.CHECK_COURSE;
        } else {
            EpCourseKind epCourseKind = new EpCourseKind();
            epCourseKind.setId(epCheck.getBelongId());
            epCourseKind.setStatus(status == CheckEnum.CHECK_NOT_PASS.getValue()? CourseKindEnum.INVALID_STATUS.getValue() : CourseKindEnum.VALID_STATUS.getValue());
            kindService.updateById(epCourseKind);
            return CheckEnum.CHECK_COURSE_KIND;
        }
    }

    @Override
    public boolean check(EpCheck epCheck, int status) {
        if (epCheck.getStatus() != CheckEnum.UNCHECKED_STATUS.getValue())
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"不得多次审核!");
        if (status != CheckEnum.CHECK_NOT_PASS.getValue()
                && status != CheckEnum.CHECK_PASS.getValue())
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"参数非法!");
        epCheck.setStatus(status);
        epCheck.setUpdateTime(null);
        if (!update(epCheck))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"审核失败");
        return true;
    }
}

