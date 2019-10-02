package cn.ep.bean;

import cn.ep.serializer.Long2StringSerializer;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class EpDir {
    @NotNull(groups = {Update.class})
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long dirId;

    @NotNull(groups = {Insert.class})
    private String dirName;

    @NotNull(groups = {Insert.class})
    private String dirType;

    private Date createTime;

    private Date updateTime;

    @JsonSerialize(using = Long2StringSerializer.class)
    private Long dirParentId;

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

    public Long getDirParentId() {
        return dirParentId;
    }

    public void setDirParentId(Long dirParentId) {
        this.dirParentId = dirParentId;
    }
}