package cn.ep.bean;
import cn.ep.serializer.Long2StringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
public class EpUserDetialsPermission{
    @JsonSerialize(using = Long2StringSerializer.class)
    private Long permId;

    private Integer reqMethod;

    private String uri;
}
