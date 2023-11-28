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
 * @TableName carousel
 */
@TableName(value ="carousel")
@Data
@ApiModel(value = "轮播图")
public class Carousel implements Serializable {
    /**
     * 
     */
    @TableId(value = "carousel_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer carousel_id;

    /**
     * 
     */
    @TableField(value = "img_path")
    @ApiModelProperty(value = "图片路径")
    private String img_path;

    /**
     * 
     */
    @TableField(value = "describes")
    @ApiModelProperty(value = "描述")
    private String describes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}