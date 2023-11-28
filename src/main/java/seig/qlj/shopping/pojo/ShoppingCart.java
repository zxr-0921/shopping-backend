package seig.qlj.shopping.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName shopping_cart
 */
@TableName(value ="shopping_cart")
@Data
@ApiModel(value = "购物车")
public class ShoppingCart implements Serializable {
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
    @TableField(value = "num")
    @ApiModelProperty(value = "数量")
    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}