package cn.ep.bean;

import lombok.Data;

import java.util.List;

@Data
public class EpUserDto extends EpUser{

    private List<EpRole> roles;
}
