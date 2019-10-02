package cn.ep.service.impl;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpFile;
import cn.ep.bean.EpFileExample;
import cn.ep.config.UploadProperties;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpFileMapper;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.IdWorker;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadProperties prop;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private EpDirService epDirService;

    @Autowired
    private EpFileMapper epFileMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public EpFile uploadFile(MultipartFile file, Long dirId) {
        //对文件进行校验
        //对文件格式进行校验
        String contentType = file.getContentType();
        EpDir epDir = epDirService.selectById(dirId);
        if (null == epDir) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "该文件夹不存在");
        }

        if (!epDir.getDirType().contains(contentType)) {
            throw new GlobalException(GlobalEnum.INVALID_FILE_FORMAT);
        }


        //保存图片
        try {
            String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extensionName, null);

            // 保存数据库
            EpFile epFile = new EpFile();
            epFile.setFileId(idWorker.nextId());
            epFile.setDirId(dirId);
            epFile.setFileIp(prop.getBaseUrl());
            epFile.setFileUrl(storePath.getFullPath());
            boolean success = epFileMapper.insertSelective(epFile) > 0 ? true : false;
            if (!success) {
                deleteFile(storePath.getFullPath());
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "保存文件失败");
            }
            //返回保存图片的完整url
            return epFile;
        } catch (IOException e) {
            throw new GlobalException(GlobalEnum.UPLOAD_FILE_EXCEPTION);
        }

    }

    @Override
    public void deleteFile(String fullPath) {
        log.info("【文件模块】删除文件 url = {}", fullPath);
        storageClient.deleteFile(fullPath);
    }

    @Override
    @Transactional
    public void deleteByDirId(Long dirId) {
        EpFileExample epFileExample = new EpFileExample();
        epFileExample.createCriteria().andDirIdEqualTo(dirId);
        // 获取相关文件
        List<EpFile> epFiles = epFileMapper.selectByExample(epFileExample);
        // 删除数据库信息
        boolean success = epFileMapper.deleteByExample(epFileExample) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件删除失败");
        }
        // 删除文件
        epFiles.stream().forEach(
                epFile -> deleteFile(epFile.getFileUrl())
        );
    }


    @Override
    @Transactional
    public void deleteByEpFileId(Long fileId) {
        // 删除数据库信息
        EpFile epFile = epFileMapper.selectByPrimaryKey(fileId);
        boolean success = epFileMapper.deleteByPrimaryKey(fileId) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件删除失败");
        }
        // 删除文件
        deleteFile(epFile.getFileUrl());
    }

    @Override
    public byte[] downloadFile(String fileUrl) {
        byte[] bytes = storageClient.downloadFile(
                fileUrl.substring(0, fileUrl.indexOf("/")),
                fileUrl.substring(fileUrl.indexOf("/") + 1),
                new DownloadByteArray()
        );
        return bytes;
    }
}
