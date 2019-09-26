package cn.ep.enums;

import cn.ep.utils.ResultVO;

/**
 * @Author deschen
 * @Create 2019/7/14
 * @Description  全局状态码模板
 * @Since 1.0.0
 */
public enum GlobalEnum implements IEnum{

    /*
     * https://blog.csdn.net/q1056843325/article/details/53147180
     * Http状态码
     * */
    SUCCESS(200, "成功"),

    PARAMS_ERROR(1000, "参数错误: %s"),

    OPERATION_ERROR(10001, "操作失败：%s"),

    SERVICE_ERROR(500, "系统异常"),



    ;

    private Integer code;

    private String message;

    GlobalEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
