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
public class EpCourse {

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class, Update.class})
    private Long id;

    @NotNull(groups = {Insert.class})
    private String courseName;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @NotNull(groups = {Insert.class})
    private Integer status;

    @NotNull(groups = {Insert.class})
    private Integer free;

    private String goal;

    private String overview;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date openTime;

    @NotNull(groups = {Insert.class})
    private Long kindId;

    @NotNull(groups = {Insert.class})
    private Long userId;

    @NotNull(groups = {Insert.class})
    private Double price;

    private Integer watchCount;

    private String pictureUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal == null ? null : goal.trim();
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview == null ? null : overview.trim();
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Long getKindId() {
        return kindId;
    }

    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(Integer watchCount) {
        this.watchCount = watchCount;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }
}