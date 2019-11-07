package cn.ep.service;

import cn.ep.bean.EpRole;
import cn.ep.bean.EpUserDetails;
import cn.ep.bean.UserJwt;
import cn.ep.client.EpUserClient;
import cn.ep.constant.UserConstant;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    EpUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        //远程调用用户中心根据账号查询用户信息

        ResultVO resultVO = userClient.getUserDetailsByUserNickNameAndType(username, UserConstant.TYPE_USER);
        if (!resultVO.getCode().equals(resultVO.success().getCode())) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "获取用户信息失败");
        }
        if(resultVO.getData() == null){
            //返回空给spring security表示用户不存在
            return null;
        }
        EpUserDetails userDetails = JsonUtil.parseObject(resultVO.getData(), EpUserDetails.class);
        //取出正确密码（hash值）
        String password = userDetails.getUserPwd();
        //用户权限，这里暂时使用静态数据，最终会从数据库读取
        Set<EpRole> roles = userDetails.getRoles();

        String roleString = Optional.ofNullable(roles)
                .map(epRoles -> {
                    ArrayList<String> role_string = new ArrayList<>();
                    epRoles.forEach(
                            role -> {
                                role_string.add(role.getRoleName());
                            }
                    );
                    return StringUtils.join(role_string, ",");
                }).orElseThrow(() -> new GlobalException(GlobalEnum.OPERATION_ERROR, "无角色"));
        System.out.println(AuthorityUtils.commaSeparatedStringToAuthorityList(roleString));
        UserJwt userJwt = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleString));
        BeanUtils.copyProperties(userDetails, userJwt);
        userJwt.setName(userDetails.getUserName());
        return userJwt;
    }


}
