package cn.ep.bean;

import cn.ep.serializer.Date2LongSerializer;
import cn.ep.serializer.Long2StringSerializer;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class EpPermission {
    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Update.class})
    private Long permId;

    @NotEmpty(groups = {Insert.class})
    private String permDescription;

    @Range(min=1, max=4, groups = {Insert.class})
    private Integer reqMethod;

    private String zuulPrefix;

    private String serverPrefix;

    private Boolean deleted;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long deptId;

    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }

    public String getPermDescription() {
        return permDescription;
    }

    public void setPermDescription(String permDescription) {
        this.permDescription = permDescription == null ? null : permDescription.trim();
    }

    public Integer getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(Integer reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getZuulPrefix() {
        return zuulPrefix;
    }

    public void setZuulPrefix(String zuulPrefix) {
        this.zuulPrefix = zuulPrefix == null ? null : zuulPrefix.trim();
    }

    public String getServerPrefix() {
        return serverPrefix;
    }

    public void setServerPrefix(String serverPrefix) {
        this.serverPrefix = serverPrefix == null ? null : serverPrefix.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}