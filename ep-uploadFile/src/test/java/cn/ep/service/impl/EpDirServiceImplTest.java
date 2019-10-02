package cn.ep.service.impl;

import cn.ep.bean.EpDir;
import cn.ep.config.UploadProperties;
import cn.ep.mapper.EpDirMapper;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class EpDirServiceImplTest extends TestUtil {

    @Autowired
    private EpDirService epDirService;

    @Autowired
    private UploadProperties uploadProperties;


    @Test
    public void insert() throws JsonProcessingException {
        EpDir epDir = new EpDir();
        epDir.setDirName("图片");
        epDir.setDirType(JsonUtil.obj2String(uploadProperties.getAllowTypes()));
        epDirService.insert(epDir);
    }

    @Test
    public void update() throws JsonProcessingException {
        List<EpDir> epDirs = epDirService.selectAll();
        EpDir epDir = epDirs.get(0);
        epDir.setDirName("视频");
        epDirService.update(epDir);
        List<EpDir> epDirsNew = epDirService.selectAll();
        log.info("【文件夹模块】epDirNew = {}", JsonUtil.obj2String(epDirsNew.get(0)));
    }

    @Test
    public void delete() {
        List<EpDir> epDirs = epDirService.selectAll();
        EpDir epDir = epDirs.get(0);
        epDirService.deleteById(epDir.getDirId());
    }

    @Test
    public void selectAll() throws JsonProcessingException {
        List<EpDir> epDirs = epDirService.selectAll();
        log.info("【文件夹模块】epDirs = {}", JsonUtil.obj2String(epDirs));
    }
}