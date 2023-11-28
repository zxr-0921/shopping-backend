package seig.qlj.shopping.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName collect
 */
@TableName(value ="collect")
@Data
@ApiModel(value = "收藏")
public class Collect implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer user_id;

    /**
     * 
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "商品id")
    private Integer product_id;

    /**
     * 
     */
    @TableField(value = "collect_time")
    @ApiModelProperty(value = "收藏时间")
    private Long collect_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}