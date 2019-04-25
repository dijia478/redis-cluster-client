package cn.dijia478.redis5.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请求redis操作的参数对象
 *
 * @author dijia478
 * @version 1.0
 * @date 2019-4-25 11:04
 */
@Data
@ApiModel(value = "redis操作的参数对象")
public class RedisDTO {

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "field")
    private String field;

    @ApiModelProperty(value = "value")
    private String value;

}
