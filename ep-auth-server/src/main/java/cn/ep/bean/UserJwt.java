package cn.ep.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

@Data
@ToString
public class UserJwt extends User {

    private Long userId;

    private String name;

    private String fileUrl;

    private String userPosition;

    private String userDescription;

    private String userPhone;

    private String userEmail;

    private Set<EpRole> roles;

    private Set<EpUserDetialsPermission> permissions;


    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
