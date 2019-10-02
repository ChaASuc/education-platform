package cn.ep.bean;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EpDir {
    private Long dirId;

    @NotNull
    private String dirName;

    private String dirType;

    private Date createTime;

    private Date updateTime;

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName == null ? null : dirName.trim();
    }

    public String getDirType() {
        return dirType;
    }

    public void setDirType(String dirType) {
        this.dirType = dirType == null ? null : dirType.trim();
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