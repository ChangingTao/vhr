package org.javaboy.vhr.controller.sys.basic;

import org.javaboy.vhr.model.Position;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.serve.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PositionController
 * @Author 周某
 * @Date 2020-04-27 10:48
 **/
// 注意因为我们的权限url是从数据库获取的，所以url必须要适配数据库中的url
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

    /* 查 */
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    /* 增 */
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        if (positionService.addPosition(position) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /* 改 */
    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position) {
        if (positionService.updatePositions(position) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /* 删 */
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id) {
        if (positionService.deletePositionById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /* 批量删除 */
    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids){
        if (positionService.deletePositionsByIds(ids) == ids.length){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }
}
