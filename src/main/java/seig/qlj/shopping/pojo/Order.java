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
 * @TableName order
 */
@TableName(value ="order")
@Data
@ApiModel(value = "订单")
public class Order implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单id")
    private String order_id;

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
    @TableField(value = "product_num")
    @ApiModelProperty(value = "商品数量")
    private Integer product_num;

    /**
     * 
     */
    @TableField(value = "product_price")
    @ApiModelProperty(value = "商品价格")
    private Double product_price;

    /**
     * 
     */
    @TableField(value = "order_time")
    @ApiModelProperty(value = "订单时间")
    private Long order_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}