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
 * @TableName seckill_time
 */
@TableName(value ="seckill_time")
@Data
@ApiModel(value = "秒杀时间")
public class SeckillTime implements Serializable {
    /**
     * 
     */
    @TableId(value = "time_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer time_id;

    /**
     * 
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value = "开始时间")
    private Long start_time;

    /**
     * 
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value = "结束时间")
    private Long end_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}