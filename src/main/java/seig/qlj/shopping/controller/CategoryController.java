package seig.qlj.shopping.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seig.qlj.shopping.pojo.Category;
import seig.qlj.shopping.service.CategoryService;
import seig.qlj.shopping.util.ResultMessage;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:25
 * @Description:
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类模块")
// TODO: 2023/11/26 测试通过
public class CategoryController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    @ApiOperation(value = "获取所有分类")
    public ResultMessage category() {
        List<Category> categories = categoryService.getAll();
        resultMessage.success("001", categories);
        return resultMessage;
    }

}
