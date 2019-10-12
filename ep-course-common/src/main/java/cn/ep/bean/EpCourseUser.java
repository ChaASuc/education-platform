package cn.ep.bean;

import cn.ep.serializer.Date2LongSerializer;
import cn.ep.serializer.Long2StringSerializer;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EpCourseUser {

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class, Update.class})
    private Long id;

    @JsonSerialize(using =Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long courseId;

    @JsonSerialize(using =Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long userId;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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