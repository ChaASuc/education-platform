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
public class EpChapter {

    @NotNull(groups = {Update.class, Insert.class})
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long id;

    @NotNull(groups = {Insert.class})
    private String chapterName;

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long courseId;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private String duration;

    @NotNull(groups = {Insert.class})
    private Integer status;

    @JsonSerialize(using = Long2StringSerializer.class)
    private Long intro;

    @NotNull(groups = {Insert.class})
    private String url;

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long root;

    @NotNull(groups = {Insert.class})
    private Integer free;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getIntro() {
        return intro;
    }

    public void setIntro(Long intro) {
        this.intro = intro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Long getRoot() {
        return root;
    }

    public void setRoot(Long root) {
        this.root = root;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}