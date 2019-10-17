package cn.ep.controller;

import cn.ep.bean.EpChapter;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(description = "课程模块：章节接口")
@RequestMapping(value = "api/ep/course/chapter")
public class EpChapterController {

    ResultVO multInsert(List<EpChapter> chapters){
        return  ResultVO.success();
    }

}
