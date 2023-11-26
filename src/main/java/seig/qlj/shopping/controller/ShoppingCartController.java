package seig.qlj.shopping.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seig.qlj.shopping.service.ShoppingCartService;
import seig.qlj.shopping.util.ResultMessage;
import seig.qlj.shopping.vo.CartVo;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:27
 * @Description:
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "购物车模块")
// TODO: 2023/11/26 测试通过
public class ShoppingCartController{

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ShoppingCartService cartService;

    /**
     * 获取购物车信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    @ApiOperation(value = "获取购物车信息")
    public ResultMessage cart(@PathVariable String userId) {
        List<CartVo> carts = cartService.getCartByUserId(userId);
        resultMessage.success("001", carts);
        return resultMessage;
    }

    /**
     * 添加购物车
     * @param productId
     * @param userId
     * @return
     */
    @PostMapping("/product/user/{productId}/{userId}")
    @ApiOperation(value = "添加购物车")
    public ResultMessage cart(@PathVariable String productId, @PathVariable String userId) {
        CartVo cartVo = cartService.addCart(productId, userId);
        if (cartVo != null) {
            resultMessage.success("001", "添加购物车成功", cartVo);
        }else {
            resultMessage.success("002", "该商品已经在购物车，数量+1");
        }
        return resultMessage;
    }

    @PutMapping("/user/num/{cartId}/{userId}/{num}")
    @ApiOperation(value = "更新购物车数量")
    public ResultMessage cart(@PathVariable String cartId, @PathVariable String userId, @PathVariable String num) {
        cartService.updateCartNum(cartId, userId, num);
        resultMessage.success("001", "更新成功");
        return resultMessage;
    }

    @DeleteMapping("/user/{cartId}/{userId}")
    @ApiOperation(value = "删除购物车")
    public ResultMessage deleteCart(@PathVariable String cartId, @PathVariable String userId) {
        cartService.deleteCart(cartId, userId);
        resultMessage.success("001", "删除成功");
        return resultMessage;
    }
}
