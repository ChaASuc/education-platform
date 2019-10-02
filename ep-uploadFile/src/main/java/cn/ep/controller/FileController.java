package cn.ep.controller;

import cn.ep.bean.EpDir;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
@RestController
@Validated  // 校验参数，失败被ConstraintViolationException拦截
@Slf4j
@RequestMapping("/file")
@Api(description = "文件模块")
public class FileController {

    @Autowired
    private EpDirService epDirService;

    @Autowired
    private UploadService uploadService;

    /**
     * 新增文件夹
     * @param epDir
     * @return
     */
    @ApiOperation(value="新增文件夹", notes = "未测试")
    @ApiImplicitParam(name= "epDir",value = "文件夹实体类", dataType = "EpDir")
    @PostMapping("/dir")
    public ResultVO insert(@RequestBody @Valid EpDir epDir){
        epDirService.insert(epDir);
        // 删除缓存
        return ResultVO.success();
    }

//    /**
//     * 新增文件夹
//     * @param epDir
//     * @return
//     */
//    @ApiOperation(value="id", notes = "未测试")
//    @ApiImplicitParam(name= "epDir",value = "文件夹实体类", dataType = "EpDir")
//    @PostMapping("/id")
//    public ResultVO insertId(@Validated EpDir epDir){
//
//        return ResultVO.success();
//    }
//
//    /**
//     * 新增文件夹
//     * @param id
//     * @return
//     */
//    @ApiOperation(value="id", notes = "未测试")
//    @ApiImplicitParam(name= "id",value = "id", dataType = "Long")
//    @PostMapping("/ids")
//    public ResultVO insertIds(@NotNull Long id){
//
//        return ResultVO.success();
//    }

    /**
     * 根据主键修改文件夹
     * @param epDir
     * @return
     */
    @ApiOperation(value="根据主键修改文件夹",notes = "未测试")
    @ApiImplicitParam(name="epDir", value = "文件夹实体类", dataType = "EpDir")
    @PutMapping("/dir")
    public ResultVO update(@RequestBody @NotNull EpDir epDir){
        epDirService.update(epDir);
        // 删除缓存
        return ResultVO.success();

    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @ApiOperation(value="上传文件", notes = "未测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "dirId",value = "文件夹Id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "file",value = "文件", required = true, dataType = "MultipartFile")
    })
    @PostMapping(value = "/upload/{dirId}", headers = "content-type=multipart/form-data")
    public ResultVO upload(@PathVariable Long dirId, MultipartFile file){
        String fileUrl = uploadService.uploadFile(file, dirId);
        // 删除缓存
        return ResultVO.success(fileUrl);
    }


}

