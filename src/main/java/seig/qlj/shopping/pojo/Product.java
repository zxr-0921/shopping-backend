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
 * @TableName product
 */
@TableName(value ="product")
@Data
@ApiModel(value = "商品")
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer product_id;

    /**
     * 
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value = "商品名称")
    private String product_name;

    /**
     * 
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "商品分类id")
    private Integer category_id;

    /**
     * 
     */
    @TableField(value = "product_title")
    @ApiModelProperty(value = "商品标题")
    private String product_title;

    /**
     * 
     */
    @TableField(value = "product_intro")
    @ApiModelProperty(value = "商品介绍")
    private String product_intro;

    /**
     * 
     */
    @TableField(value = "product_picture")
    @ApiModelProperty(value = "商品图片")
    private String product_picture;

    /**
     * 
     */
    @TableField(value = "product_price")
    @ApiModelProperty(value = "商品价格")
    private Double product_price;

    /**
     * 
     */
    @TableField(value = "product_selling_price")
    @ApiModelProperty(value = "商品售价")
    private Double product_selling_price;

    /**
     * 
     */
    @TableField(value = "product_num")
    @ApiModelProperty(value = "商品库存")
    private Integer product_num;

    /**
     * 
     */
    @TableField(value = "product_sales")
    @ApiModelProperty(value = "商品销量")
    private Integer product_sales;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}