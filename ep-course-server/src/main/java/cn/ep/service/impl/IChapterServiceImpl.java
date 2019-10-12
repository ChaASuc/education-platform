package cn.ep.service.impl;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpChapterExample;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.service.IChapterService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IChapterServiceImpl implements IChapterService {

    @Autowired
    private EpChapterMapper chapterMapper;
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
    public boolean insert(EpChapter epChapter) {
        epChapter.setId(idWorker.nextId());
        System.out.println(epChapter);
        return chapterMapper.insertSelective(epChapter) > 0;
    }

    @Override
    public boolean updateById(EpChapter epChapter) {
        System.out.println(epChapter);
        return chapterMapper.updateByPrimaryKeySelective(epChapter) > 0;
    }
}
