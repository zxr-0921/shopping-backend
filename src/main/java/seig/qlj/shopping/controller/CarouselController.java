package seig.qlj.shopping.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import seig.qlj.shopping.pojo.Carousel;
import seig.qlj.shopping.service.CarouselService;
import seig.qlj.shopping.util.ResultMessage;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:24
 * @Description:
 */
// TODO: 2023/11/26  测试通过
@RestController
@Api(tags = "轮播图模块")
public class CarouselController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CarouselService carouselService;

    @GetMapping("/resources/carousel")
    @ApiOperation(value = "获取轮播图")
    public ResultMessage carousels() {
        List<Carousel> carousels = carouselService.getCarouselList();
        resultMessage.success("001", carousels);
        return resultMessage;
    }

}
