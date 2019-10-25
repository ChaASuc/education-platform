package cn.ep.bean;

import cn.ep.serializer.Long2StringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author deschen
 * @Create 2019/10/11
 * @Description
 * @Since 1.0.0
 */
@Data
public class EpUserDetails{

    @JsonSerialize(using = Long2StringSerializer.class)
    private Long userId;

    private String username;

    private String password;

    private Set<EpRole> roles;

    private Set<EpUserDetialsPermission> permissions;


}

