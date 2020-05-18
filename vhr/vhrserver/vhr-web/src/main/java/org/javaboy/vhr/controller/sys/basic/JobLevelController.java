package org.javaboy.vhr.controller.sys.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.serve.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName JobLevelController
 * @Author 周某
 * @Date 2020-04-28 19:07
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
@Api(tags = "权限组接口", description = "权限组接口 Rest API")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;

    /* 查 */
    @GetMapping("/")
    @ApiOperation("查询权限组")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    /* 增 */
    @PostMapping("/")
    @ApiOperation("新增权限组")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.addJobLevel(jobLevel) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /* 改 */
    @PutMapping("/")
    @ApiOperation("通过Id修改权限组数据")
    public RespBean updateJobLevelById(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.updateJobLevelById(jobLevel) == 1) {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    /* 删 */
    @DeleteMapping("/{id}")
    @ApiOperation("通过Id删除权限组中的数据")
    public RespBean deleteJobLevelById(@PathVariable Integer id) {
        if (jobLevelService.deleteJobLevelById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    @ApiOperation("通过一段数组Ids来批量删除权限信息")
    public RespBean deleteJobLevelByIds(Integer [] ids){
        if (jobLevelService.deleteJobLevelByIds(ids) == ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}



























