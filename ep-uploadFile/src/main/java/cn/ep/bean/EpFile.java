package cn.ep.bean;

import java.util.Date;

public class EpFile {
    private Long fileId;

    private Long dirId;

    private String fileIp;

    private String fileUrl;

    private Date createTime;

    private Date updateTime;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
    }

    public String getFileIp() {
        return fileIp;
    }

    public void setFileIp(String fileIp) {
        this.fileIp = fileIp == null ? null : fileIp.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}