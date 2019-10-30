package cn.ep.controller;

import cn.ep.service.CourseRelateService;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(description = "课程相关模块")
@RestController
@RequestMapping("/ep/course_relate")
public class CourseRelateController {
    @Autowired
    private CourseRelateService courseRelateService;

    /**
     * 获取类别详情
     * @param cid
     * @throws IOException
     */
    @ApiOperation(value="根据课程id获取课程评论", notes="")
    @ApiImplicitParam(name = "cid", value = "课程id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/comment/{cid}")
    public ResultVO getCommentByCourseId(@PathVariable Long cid){
        return new ResultVO().setData("OK");
    }
}
