package cn.ep.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author deschen
 * @Create 2019/9/23
 * @Description
 * @Since 1.0.0
 */
@ApiModel(value = "结果集", description = "返回json对象")
@Data
public class ResultVO implements Serializable {

    /**
     * 错误码.
     */
    @ApiModelProperty(value = "状态码", name = "code", required = true)
    private Integer code;

    /**
     * 提示信息.
     */
    @ApiModelProperty(value = "信息", name = "msg", required = true)
    private String msg;

    /**
     * 返回成功的数据
     */
    @ApiModelProperty(value = "返回成功的数据", name = "data")
    private Object data;

    /**
     * 返回失败的数据
     */
    @ApiModelProperty(value = "返回失败的数据", name = "extra")
    private Map<String, Object> extra;

    public Integer getCode() {
        return code;
    }

    public ResultVO setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    public Object getData() {
        return data;
    }

    public ResultVO setData(Object data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public ResultVO setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public ResultVO addExtra(String key, Object value) {
        extra = extra == null ? new HashMap<>(16) : extra;
        extra.put(key, value);
        return this;
    }



    public static ResultVO failure(Integer code, String message) {
        return new ResultVO()
                .setCode(code)
                .setMsg(message);
    }

}
