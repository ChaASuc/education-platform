package cn.ep.bean;

import lombok.Data;

import java.util.List;

@Data
public class EpPermissionDto extends EpPermission {

    private List<EpRole> roles;
}
