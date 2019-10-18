package cn.ep.vo;

import cn.ep.bean.EpCheck;
import cn.ep.courseenum.CheckEnum;
import lombok.Data;

@Data
public class CheckVO {
    EpCheck record;
    Object  checked;
    CheckEnum type;
}

