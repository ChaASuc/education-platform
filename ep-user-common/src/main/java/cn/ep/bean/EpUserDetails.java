package cn.ep.bean;

import cn.ep.serializer.Long2StringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

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

    private String userNickname;

    private String userName;

    private String userPwd;

    private String fileUrl;

    private String userPosition;

    private String userDescription;

    private String userPhone;

    private String userEmail;

    private Set<EpRole> roles;

    private Set<EpUserDetialsPermission> permissions;

}

