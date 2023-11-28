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
 * @TableName user
 */
@TableName(value ="user")
@Data
@ApiModel(value = "用户")
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer user_id;

    /**
     * 
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 
     */
    @TableField(value = "user_phone_number")
    @ApiModelProperty(value = "手机号")
    private String user_phone_number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}