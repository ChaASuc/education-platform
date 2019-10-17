package cn.ep.mapper;

import cn.ep.bean.EpDir;
import cn.ep.utils.IdWorker;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.Assert.*;

@Slf4j
public class EpDirMapperTest extends TestUtil {

    @Autowired
    private EpDirMapper dirMapper;

    @Autowired
    private IdWorker idWorker;

    @Test
    public void insertSelectiveTest() {
        EpDir epDir = new EpDir();
        epDir.setDirId(idWorker.nextId());
        epDir.setDirName("irene");
        epDir.setDirType("[\"image/jpeg\",\"image/png\",\"image/bmp\",\"image/gif\",\"video/x-msvideo\",\"video/mpeg\",\"video/mpeg4\",\"video/mp4\",audio/mpeg\"]");
        epDir.setUserId(10L);
        try {
            int success = dirMapper.insertSelective(epDir);
        } catch (DuplicateKeyException e) {
            try {
                log.info("{}", JsonUtil.obj2String(e));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }
}