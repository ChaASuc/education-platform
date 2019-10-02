package cn.ep.service;

import cn.ep.bean.EpFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
public interface UploadService {

    /**
     * 根据文件夹id上传文件
     * @param file
     * @param dirId
     * @return
     */
    EpFile uploadFile(MultipartFile file, Long dirId);

    /**
     * 根据文件路径删除文件
     * @param fullPath
     */
    void deleteFile(String fullPath);

    /**
     * 根据文件夹id删除文件
     * @param dirId
     */
    void deleteByDirId(Long dirId);

    /**
     * 根据文件id删除文件
     * @param fileId
     */
    void deleteByEpFileId(Long fileId);

    /**
     * 下载文件
     * @param fileUrl
     */
    byte[] downloadFile(String fileUrl);
}
