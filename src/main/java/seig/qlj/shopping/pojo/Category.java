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
 * @TableName category
 */
@TableName(value ="category")
@Data
@ApiModel(value = "商品分类")
public class Category implements Serializable {
    /**
     * 
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer category_id;

    @TableField(value = "category_name")
    @ApiModelProperty(value = "商品分类名称")
    private String category_name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}