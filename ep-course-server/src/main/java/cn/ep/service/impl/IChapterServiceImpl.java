package cn.ep.service.impl;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpChapterExample;
import cn.ep.bean.EpCheck;
import cn.ep.courseenum.ChapterEnum;
import cn.ep.courseenum.CheckEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.ICheckService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
public class IChapterServiceImpl implements IChapterService {

    @Autowired
    private EpChapterMapper chapterMapper;
    @Autowired
    private ICheckService checkService;
    @Autowired
    private IdWorker idWorker;

    @Override
    public Map<EpChapter, List<EpChapter>> getListByCourseIdAndStatus(long id, int status) {
        EpChapterExample chapterExample = new EpChapterExample();
        EpChapterExample.Criteria criteria = chapterExample.createCriteria();
        criteria.andCourseIdEqualTo(id).andStatusEqualTo(status);
        chapterExample.setOrderByClause("`create_time`");
        List<EpChapter> epChapterList = chapterMapper.selectByExample(chapterExample);
        Map<EpChapter, List<EpChapter>> map = new LinkedHashMap<>();
        Map<Long, EpChapter> root = new HashMap<>();
        for (EpChapter chapter :
                epChapterList) {
            if (chapter.getRoot() == 0){
                root.put(chapter.getId(),chapter);
                map.put(chapter,new ArrayList<>());
            }
        }
        for (EpChapter chapter :
                epChapterList) {
            if (chapter.getRoot() != 0){
                map.get(root.get(chapter.getRoot())).add(chapter);
            }
        }
        return map;
    }

    @Override
    public boolean insert(@Validated EpChapter epChapter) {
        System.out.println(epChapter);
        return chapterMapper.insertSelective(epChapter) > 0;
    }

    @Override
    public boolean updateById(EpChapter epChapter) {
        System.out.println(epChapter);
        return chapterMapper.updateByPrimaryKeySelective(epChapter) > 0;
    }

    @Override
    @Transactional
    public boolean multiplyInsertAndSendCheck(List<EpChapter> chapters) {
        if (chapters == null || chapters.size()==0)
            return true; //不处理
        for (EpChapter c :
                chapters) {
            c.setId(idWorker.nextId());
            c.setStatus(ChapterEnum.INVALID_STATUS.getValue());
        }
        if (chapterMapper.multiplyInsertSelective(chapters)<=0)
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"批量添加失败");
        // todo 审核人id从汉槟随机获取管理员id
        long whoId = 1L;
        for (EpChapter chapter :
                chapters) {
            EpCheck check = new EpCheck();
            check.setWho(whoId);
            check.setStatus(CheckEnum.UNCHECKED_STATUS.getValue());
            check.setBelongId(chapter.getId());
            check.setBelong(CheckEnum.CHECK_VIDEO.getValue());
            if (!checkService.insert(check))
                throw new GlobalException(GlobalEnum.OPERATION_ERROR,"批量添加失败");
            //System.out.println(check);
        }
        return true;
    }

    @Override
    public EpChapter getByChapterId(Long Id) {
        return chapterMapper.selectByPrimaryKey(Id);
    }
}
