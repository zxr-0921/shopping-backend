package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.ProductMapper;
import seig.qlj.shopping.mapper.ShoppingCartMapper;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.pojo.ShoppingCart;
import seig.qlj.shopping.service.ShoppingCartService;
import seig.qlj.shopping.vo.CartVo;

import java.util.ArrayList;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【shopping_cart】的数据库操作Service实现
* @createDate 2023-11-24 23:37:13
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<CartVo> getCartByUserId(String userId) {
//        ShoppingCart cart = new ShoppingCart();
//        cart.setUser_id(Integer.parseInt(userId));

        List<ShoppingCart> list = null;
        List<CartVo> cartVoList = new ArrayList<>();
        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<ShoppingCart> list1 = cartMapper.selectList(wrapper);
        System.out.println("购物车"+list1);
        try {
//            list = cartMapper.select(cart);ixu
            for (ShoppingCart c : list1) {
                cartVoList.add(getCartVo(c));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_CART_ERROR);
        }
        return cartVoList;
    }

    @Override
    public CartVo addCart(String productId, String userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser_id(Integer.parseInt(userId));
        cart.setProduct_id(Integer.parseInt(productId));
        // TODO: 2023/11/26  未测试
        QueryWrapper wrapper = new QueryWrapper(cart);
        // 查看数据库是否已存在,存在数量直接加1
        ShoppingCart one = cartMapper.selectOne(wrapper);
        if (one != null) {
            // 还要判断是否达到该商品规定上限
            if (one.getNum() >= 5) { // TODO 这里默认设为5 后期再动态修改
                throw new XmException(ExceptionEnum.ADD_CART_NUM_UPPER);
            }
            one.setNum(one.getNum() + 1);
            cartMapper.updateById(one);
            return null;
        }else {
            // 不存在
            cart.setNum(1);
            cartMapper.insert(cart);
            return getCartVo(cart);
        }
    }

    @Override
    public CartVo getCartVo(ShoppingCart cart) {
        // 获取商品，用于封装下面的类
        Product product = productMapper.selectById(cart.getProduct_id());
        // 返回购物车详情
        CartVo cartVo = new CartVo();
        cartVo.setId(cart.getId());
        cartVo.setProductId(cart.getProduct_id());
        cartVo.setProductName(product.getProduct_name());
        cartVo.setProductImg(product.getProduct_picture());
        cartVo.setPrice(product.getProduct_price());
        cartVo.setNum(cart.getNum());
        cartVo.setMaxNum(5); // TODO 这里默认设为5 后期再动态修改
        cartVo.setCheck(false);
        return cartVo;
    }

    @Override
    public void updateCartNum(String cartId, String userId, String num) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(Integer.parseInt(cartId));
        cart.setUser_id(Integer.parseInt(userId));
        cart.setNum(Integer.parseInt(num));
        try {
//            int count = cartMapper.updateByPrimaryKeySelective(cart);
            int count = cartMapper.updateById(cart);
            if (count != 1) {
                throw new XmException(ExceptionEnum.UPDATE_CART_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.UPDATE_CART_ERROR);
        }

    }

    @Override
    public void deleteCart(String cartId, String userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(Integer.parseInt(cartId));
        cart.setUser_id(Integer.parseInt(userId));
        QueryWrapper wrapper = new QueryWrapper(cart);
        try {
            cartMapper.delete(wrapper);
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.DELETE_CART_ERROR);
        }
    }
}




