package cn.ep.bean;

import cn.ep.serializer.Long2StringSerializer;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Product {

    @NotNull(groups = {Update.class})
//    @JsonSerialize(using = Long2StringSerializer.class)
    private Integer id;

    @NotNull(groups = {Insert.class})
    private String name;

    @NotNull(groups = {Insert.class})
    private String description;

    private Integer cid;

    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}