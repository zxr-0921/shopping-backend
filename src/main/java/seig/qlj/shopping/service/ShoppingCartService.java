package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.ShoppingCart;
import seig.qlj.shopping.vo.CartVo;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【shopping_cart】的数据库操作Service
* @createDate 2023-11-24 23:37:13
*/
public interface ShoppingCartService extends IService<ShoppingCart> {
    public List<CartVo> getCartByUserId(String userId);
    public CartVo addCart(String productId, String userId);
    public CartVo getCartVo(ShoppingCart cart);
    public void updateCartNum(String cartId, String userId, String num);
    public void deleteCart(String cartId, String userId);

}
