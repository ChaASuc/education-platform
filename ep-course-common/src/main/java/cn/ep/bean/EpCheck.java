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
public class EpCheck {

    @JsonSerialize(using = Long2StringSerializer.class)
    @NotNull(groups = {Insert.class, Update.class})
    private Long id;

    private String reason;

    @NotNull(groups = {Insert.class})
    private Integer belong;

    @NotNull(groups = {Insert.class})
    private Integer status;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @JsonSerialize(using =Long2StringSerializer.class)
    @NotNull(groups = {Insert.class})
    private Long belongId;

    @NotNull(groups = {Insert.class})
    @JsonSerialize(using =Long2StringSerializer.class)
    private Long who;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getBelong() {
        return belong;
    }

    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public Long getWho() {
        return who;
    }

    public void setWho(Long who) {
        this.who = who;
    }
}