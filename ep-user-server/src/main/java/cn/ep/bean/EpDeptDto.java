package cn.ep.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
@Data
public class EpDeptDto extends EpDept{

    public List<EpDeptDto> epDeptDtos;
}
