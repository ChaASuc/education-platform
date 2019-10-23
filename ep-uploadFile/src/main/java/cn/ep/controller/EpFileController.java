package cn.ep.controller;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpFile;
import cn.ep.dto.EpDirDto;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
@RestController
@Validated  // 校验参数，失败被ConstraintViolationException拦截
@Slf4j
@RequestMapping("/ep/file")
@Api(description = "文件模块")
public class EpFileController {

    @Autowired
    private EpDirService epDirService;


    @Autowired
    private UploadService uploadService;

    @Autowired
    private RedisUtil redisUtil;
//
    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_uploadFile_prefix_getDirListAll
        public static final String EP_UPLOADFILE_PREFIX_GETDIRLISTALL = "ep_uploadFile_prefix_getDirListAll";

        // ep_uploadFile_prefix_getListByDirIdAndPageNum
        public static final String EP_UPLOADFILE_PREFIX_GETLISTBYDIRIDANDPAGENUM = "ep_uploadFile_prefix_getListByDirIdAndPageNum_%s_%s";

        // ep_uploadFile_prefix_downloadFile_%s
        public static final String EP_UPLOADFILE_PREFIX_DOWNLOADFILE = "ep_uploadFile_prefix_downloadFile_%s";

        //ep_uploadFile_prefix_*  用于全部删除，避免缓存
        public static final String EP_UPLOADFILE_PREFIX = "ep_uploadFile_prefix_*";
    }

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
        redisUtil.delFuz(CacheNameHelper.EP_UPLOADFILE_PREFIX);
        return ResultVO.success();
    }

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
        redisUtil.delFuz(CacheNameHelper.EP_UPLOADFILE_PREFIX);
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
        redisUtil.delFuz(CacheNameHelper.EP_UPLOADFILE_PREFIX);
        return ResultVO.success();

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
        redisUtil.get(CacheNameHelper.EP_UPLOADFILE_PREFIX);
        return ResultVO.success();
    }

    @ApiOperation(value="根据文件路径不包含ip下载文件", notes = "已测试")
    @ApiImplicitParam(name= "fileUrl",value = "文件路径", required = true, paramType = "query")
    @GetMapping(value = "/download")
    public void downloadFile(@RequestParam @NotNull String fileUrl, HttpServletResponse response) throws UnsupportedEncodingException {
        // 文件格式
        response.setHeader(
                "Content-disposition",
                "attachment;filename=" + URLEncoder.encode(
                        fileUrl.substring(fileUrl.lastIndexOf("/") + 1), "UTF-8"));
        response.setCharacterEncoding("UTF-8");

        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_UPLOADFILE_PREFIX_DOWNLOADFILE, fileUrl);
        // 统一返回值
        byte[] bytes = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            bytes = uploadService.downloadFile(fileUrl);
            redisUtil.set(key, bytes);
        }else {
            bytes = (byte[]) obj;
        }

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

    /**
     * 获取所有文件夹
     * @return
     */
    @ApiOperation(value="获取所有文件夹",notes = "已测试")
    @GetMapping("/dir/listAll")
    public ResultVO getDirListAll(){

        // 获取reids的key
        String key = CacheNameHelper.EP_UPLOADFILE_PREFIX_GETDIRLISTALL;
        // 统一返回值
        List<EpDirDto> epDirDtos = new ArrayList<>();
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            epDirDtos = epDirService.selectAll();
            redisUtil.set(key, epDirDtos);
        }else {
            epDirDtos = (List<EpDirDto>) obj;
        }
        // 删除缓存
        return ResultVO.success(epDirDtos);

    }

    /**
     * 获取该文件夹下所有图片
     * @return
     */
    @ApiOperation(value="获取该文件夹下所有图片",notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "dirId",value = "文件夹id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "pageNum",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/list/{dirId}/{pageNum}")
    public ResultVO getListByDirIdAndPageNum(@PathVariable @NotNull @Min(0) Long dirId,
                                             @PathVariable @NotNull @Min(0) Integer pageNum){

        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_UPLOADFILE_PREFIX_GETLISTBYDIRIDANDPAGENUM, dirId, pageNum);
        // 统一返回值
        PageInfo<String> pageInfo = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            pageInfo = uploadService.selectByDirIdAndPageNum(dirId, pageNum);
            redisUtil.set(key, pageInfo);
        }else {
            pageInfo = (PageInfo<String>) obj;
        }
        // 删除缓存
        return ResultVO.success(pageInfo);

    }

}

