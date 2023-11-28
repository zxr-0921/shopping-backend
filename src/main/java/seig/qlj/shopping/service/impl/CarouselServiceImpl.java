package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.CarouselMapper;
import seig.qlj.shopping.pojo.Carousel;
import seig.qlj.shopping.service.CarouselService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【carousel】的数据库操作Service实现
* @createDate 2023-11-24 23:33:43
*/
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel>
    implements CarouselService{
    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> getCarouselList() {
        List<Carousel> list = null;
        try {
//            list = carouselMapper.selectAll();
            list = carouselMapper.selectList(null);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_CAROUSEL_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_CAROUSEL_ERROR);
        }
        return list;
    }
}




