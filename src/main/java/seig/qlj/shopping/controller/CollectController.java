package seig.qlj.shopping.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.service.CollectService;
import seig.qlj.shopping.util.ResultMessage;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:25
 * @Description:
 */
// TODO: 2023/11/26   测试通过
@RestController
@RequestMapping("/collect")
@Api(tags = "收藏模块")
public class CollectController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CollectService collectService;

    /**
     * 将商品收藏
     * @param userId
     * @param productId
     * @return
     */
    @PostMapping("/user/{productId}/{userId}")
    @ApiOperation(value = "商品收藏")
    public ResultMessage addCollect(@PathVariable String userId, @PathVariable String productId) {
        collectService.addCollect(userId, productId);
        resultMessage.success("001", "商品收藏成功");
        return resultMessage;
    }

    /**
     * 获取用户收藏
     * @param userId
     * @return 返回商品集合
     */
    @GetMapping("/user/{userId}")
    @ApiOperation(value = "获取用户收藏")
    public ResultMessage getCollect(@PathVariable String userId) {
        List<Product> collects = collectService.getCollect(userId);
        resultMessage.success("001", collects);
        return resultMessage;
    }

    @DeleteMapping("/user/{productId}/{userId}")
    @ApiOperation(value = "删除收藏")
    public ResultMessage deleteCollect(@PathVariable String productId, @PathVariable String userId) {
        collectService.deleteCollect(userId, productId);
        resultMessage.success("001", "删除收藏成功");
        return resultMessage;
    }
}
