package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.Carousel;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【carousel】的数据库操作Service
* @createDate 2023-11-24 23:33:43
*/
public interface CarouselService extends IService<Carousel> {
    public List<Carousel> getCarouselList();

}
