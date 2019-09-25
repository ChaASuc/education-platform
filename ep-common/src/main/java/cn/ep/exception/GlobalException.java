package cn.ep.exception;


import cn.ep.enums.IEnum;

/**
 * @Author deschen
 * @Create 2019/5/14
 * @Description
 * @Since 1.0.0
 */
public class GlobalException extends RuntimeException{

    private Integer code;

    public GlobalException(IEnum iEnum) {
        super(iEnum.getMessage());
        this.code = code;
    }

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
