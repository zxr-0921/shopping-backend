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
 * @TableName product_picture
 */
@TableName(value ="product_picture")
@Data
@ApiModel(value = "商品图片")
public class ProductPicture implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "商品id")
    private Integer product_id;

    /**
     * 
     */
    @TableField(value = "product_picture")
    @ApiModelProperty(value = "商品图片")
    private String product_picture;

    /**
     * 
     */
    @TableField(value = "intro")
    @ApiModelProperty(value = "图片介绍")
    private String intro;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}