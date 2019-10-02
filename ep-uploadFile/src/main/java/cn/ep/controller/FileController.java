package cn.ep.controller;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpFile;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @ApiOperation(value="新增文件夹", notes = "已测试")
    @ApiImplicitParam(name= "epDir",value = "文件夹实体类", dataType = "EpDir")
    @PostMapping("/dir")
    public ResultVO insert(@RequestBody @Validated({Insert.class}) EpDir epDir){
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
    @ApiOperation(value="根据主键修改文件夹",notes = "已测试")
    @ApiImplicitParam(name="epDir", value = "文件夹实体类", dataType = "EpDir")
    @PutMapping("/dir")
    public ResultVO update(@RequestBody @Validated({Update.class}) EpDir epDir){
        epDirService.update(epDir);
        // 删除缓存
        return ResultVO.success();

    }

    /**
     * 根据主键物理删除文件夹
     * @param dirId
     * @return
     */
    @ApiOperation(value="根据主键物理删除文件夹",notes = "已测试")
    @ApiImplicitParam(name="dirId", value = "文件夹id", paramType = "path")
    @DeleteMapping("/dir/{dirId}")
    public ResultVO deleteByDirId(@NotNull @PathVariable Long dirId){
        epDirService.deleteById(dirId);
        // 删除缓存
        return ResultVO.success();

    }

    /**
     * 获取所有文件夹
     * @return
     */
    @ApiOperation(value="获取所有文件夹",notes = "已测试")
    @GetMapping("/dir/listAll")
    public ResultVO getDirListAll(){

        // 删除缓存
        return ResultVO.success(epDirService.selectAll());

    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @ApiOperation(value="上传文件", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "dirId",value = "文件夹Id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "file",value = "文件", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping(value = "/upload/{dirId}", headers = "content-type=multipart/form-data")
    public ResultVO upload(@NotNull @PathVariable Long dirId, @NotNull MultipartFile file){
        EpFile epFile = uploadService.uploadFile(file, dirId);
        // 删除缓存
        return ResultVO.success(epFile);
    }

    /**
     * 根据文件id物理删除文件
     * @param fileId
     * @return
     */
    @ApiOperation(value="根据文件id物理删除文件", notes = "已测试")
    @ApiImplicitParam(name= "fileId",value = "文件id", required = true, paramType = "path")
    @DeleteMapping(value = "/delete/{fileId}")
    public ResultVO delete(@PathVariable @NotNull Long fileId){
        uploadService.deleteByEpFileId(fileId);
        // 删除缓存
        return ResultVO.success();
    }

    @ApiOperation(value="根据文件路径不包含ip下载文件", notes = "已测试")
    @ApiImplicitParam(name= "fileUrl",value = "文件路径", required = true, paramType = "query")
    @GetMapping(value = "/download")
    public void downloadFile(@RequestParam @NotNull String fileUrl, HttpServletResponse response) throws UnsupportedEncodingException {
        byte[] bytes = uploadService.downloadFile(fileUrl);
        // 文件格式
        response.setHeader(
                "Content-disposition",
                "attachment;filename=" + URLEncoder.encode(
                        fileUrl.substring(fileUrl.lastIndexOf("/") + 1), "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

