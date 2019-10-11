package cn.ep.service.impl;

import cn.ep.bean.EpChapter;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IChapterServiceImpl implements IChapterService {

    @Autowired
    private EpChapterMapper chapterMapper;

    @Override
    public Map<EpChapter, List<EpChapter>> getListByIdAndStatus(long id, int status) {
        return null;
    }

    @Override
    public boolean insert(EpChapter epChapter) {
        return false;
    }

    @Override
    public boolean update(EpChapter epChapter) {
        return false;
    }
}
