package cn.ep.service.impl;

import cn.ep.bean.EpWatchRecord;
import cn.ep.bean.EpWatchRecordExample;
import cn.ep.courseenum.WatchRecordEnum;
import cn.ep.mapper.EpWatchRecordMapper;
import cn.ep.service.IWatchRecordService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IWatchRecordServiceImpl implements IWatchRecordService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    EpWatchRecordMapper recordMapper;

    @Override
    public boolean insert(EpWatchRecord record) {
        record.setId(idWorker.nextId());
        record.setStatus(WatchRecordEnum.VALID_STATUS.getValue());
        return recordMapper.insertSelective(record) > 0;
    }

    @Override
    public List<EpWatchRecord> selectByUserIdAndCourseIdAndStatus(long userId, long courseId, int status) {
        EpWatchRecordExample recordExample = new EpWatchRecordExample();
        recordExample.setOrderByClause("chapter_id");
        EpWatchRecordExample.Criteria criteria = recordExample.createCriteria();
        criteria.andUserIdEqualTo(userId).andCourseIdEqualTo(courseId).andStatusEqualTo(status);
        return recordMapper.selectByExample(recordExample);
    }

    @Override
    public boolean update(EpWatchRecord record) {
        EpWatchRecordExample recordExample = new EpWatchRecordExample();
        EpWatchRecordExample.Criteria criteria = recordExample.createCriteria();
        criteria.andUserIdEqualTo(record.getUserId())
                .andCourseIdEqualTo(record.getCourseId())
                .andChapterIdEqualTo(record.getChapterId());
        return recordMapper.updateByExampleSelective(record,recordExample) > 0;
    }
}
