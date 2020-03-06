package me.zhengjie.modules.task.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.task.domain.Task;
import me.zhengjie.modules.task.service.TaskService;
import me.zhengjie.modules.task.service.dto.TaskQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.*;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
* @author Aroli
* @date 2020-03-04
*/
@Api(tags = "task管理")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService ) {
        this.taskService = taskService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('task:list')")
    public void download(HttpServletResponse response, TaskQueryCriteria criteria) throws IOException {
        taskService.download(taskService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询任务")
    @ApiOperation("查询任务")
    @PreAuthorize("@el.check('task:list')")
    public ResponseEntity<Object> getTasks(TaskQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(taskService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增任务")
    @ApiOperation("新增任务")
    @PreAuthorize("@el.check('task:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Task resources){
        return new ResponseEntity<>(taskService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改任务")
    @ApiOperation("修改任务")
    @PreAuthorize("@el.check('task:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Task resources){
        taskService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除任务")
    @ApiOperation("删除任务")
    @PreAuthorize("@el.check('task:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        taskService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @Log("下架任务")
    @ApiOperation("下架任务")
    @PreAuthorize("@el.check('task:close')")
    @PutMapping
    public ResponseEntity<Object> close(@RequestBody String id) {
        taskService.close(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}