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
 * @TableName seckill_product
 */
@TableName(value ="seckill_product")
@Data
@ApiModel(value = "秒杀商品")
public class SeckillProduct implements Serializable {
    /**
     * 
     */
    @TableId(value = "seckill_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer seckill_id;

    /**
     * 
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "商品id")
    private Integer product_id;

    /**
     * 
     */
    @TableField(value = "seckill_price")
    @ApiModelProperty(value = "秒杀价格")
    private Double seckill_price;

    /**
     * 
     */
    @TableField(value = "seckill_stock")
    @ApiModelProperty(value = "秒杀库存")
    private Integer seckill_stock;

    /**
     * 
     */
    @TableField(value = "time_id")
    @ApiModelProperty(value = "秒杀时间id")
    private Integer time_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}