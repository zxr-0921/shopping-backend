package seig.qlj.shopping.vo;

import lombok.Data;
import seig.qlj.shopping.pojo.SeckillProduct;

import java.io.Serializable;

@Data
public class SeckillProductVo extends SeckillProduct implements Serializable {

    private String productName;

    private Double productPrice;

    private String productPicture;

    private Long startTime;

    private Long endTime;


}