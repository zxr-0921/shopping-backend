package seig.qlj.shopping.vo;

import lombok.Data;
import seig.qlj.shopping.pojo.Order;

/**
 * @Auther: wdd
 * @Date: 2020-03-27 16:29
 * @Description:
 */
@Data
public class OrderVo extends Order {

    private String productName;

    private String productPicture;

}
