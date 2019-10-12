package cn.ep.bean;

import cn.ep.serializer.Date2LongSerializer;
import cn.ep.serializer.Long2StringSerializer;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EpCourseKind {

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class, Update.class})
    private Long id;

    @NotNull(groups = {Insert.class})
    private String kindName;

    @NotNull(groups = {Insert.class})
    private Integer status;

    @JsonSerialize(using =Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long root;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName == null ? null : kindName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRoot() {
        return root;
    }

    public void setRoot(Long root) {
        this.root = root;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}