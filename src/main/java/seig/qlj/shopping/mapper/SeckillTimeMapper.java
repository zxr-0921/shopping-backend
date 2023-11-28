package seig.qlj.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import seig.qlj.shopping.pojo.SeckillTime;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【seckill_time】的数据库操作Mapper
* @createDate 2023-11-24 23:37:09
* @Entity seig.qlj.shopping.pojo.SeckillTime
*/
public interface SeckillTimeMapper extends BaseMapper<SeckillTime> {
    @Select("select * from seckill_time where end_time > #{time} limit 6")
    List<SeckillTime> getTime(long time);

    @Delete("delete from seckill_time")
    void deleteAll();

    @Select("select endTime from seckill_time where time_id = #{timeId}")
    Long getEndTime(Integer timeId);

}




