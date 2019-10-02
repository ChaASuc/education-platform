package cn.ep.dto;

import cn.ep.bean.EpDir;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description  文件目录实体类
 * @Since 1.0.0
 */
@Data
public class EpDirDto extends EpDir implements Serializable {

    List<EpDirDto> epDirDtos;
}
