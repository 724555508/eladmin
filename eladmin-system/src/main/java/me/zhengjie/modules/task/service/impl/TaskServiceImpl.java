package me.zhengjie.modules.task.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.client.TaskClient;
import me.zhengjie.modules.task.domain.Task;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.task.repository.TaskRepository;
import me.zhengjie.modules.task.service.TaskService;
import me.zhengjie.modules.task.service.dto.TaskDto;
import me.zhengjie.modules.task.service.dto.TaskQueryCriteria;
import me.zhengjie.modules.task.service.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
* @author Aroli
* @date 2020-03-04
*/
@Service
//@CacheConfig(cacheNames = "task")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;
    
    @Resource private TaskClient taskClient;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(TaskQueryCriteria criteria, Pageable pageable){
        Page<Task> page = taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(taskMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<TaskDto> queryAll(TaskQueryCriteria criteria){
        return taskMapper.toDto(taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseGet(Task::new);
        ValidationUtil.isNull(task.getId(),"Task","id",id);
        return taskMapper.toDto(task);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public TaskDto create(Task resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return taskMapper.toDto(taskRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Task resources) {
        Task task = taskRepository.findById(resources.getId()).orElseGet(Task::new);
        ValidationUtil.isNull( task.getId(),"Task","id",resources.getId());
        task.copy(resources);
        taskRepository.save(task);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            taskRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TaskDto task : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务类型", task.getType());
            map.put("支持设备（1.全部   2.安卓   3.ios）", task.getDevice());
            map.put("项目名称", task.getItemName());
            map.put("项目标题", task.getTaskTitle());
            map.put("项目备注", task.getTaskRemark());
            map.put("做单限时", task.getLimitTime());
            map.put("审核时间", task.getCheckTime());
            map.put("悬赏主发布单价", task.getRewardPrice());
            map.put("实际用户看到的单价", task.getRealityPrice());
            map.put("悬赏总额", task.getTotalMoney());
            map.put("任务总数量", task.getRewardCount());
            map.put("已完成数量", task.getCompletedCount());
            map.put("步骤", task.getSteps());
            map.put("创建时间", task.getGmtCreate());
            map.put("创建人", task.getCreateId());
            map.put("状态（1.待审核   2.已上架  3.已下架 4.已完成 ）", task.getStatus());
            map.put("完成任务时是否需要上传图片（0:不需要  n:需要n张）", task.getNeedImg());
            map.put("完成任务时是否需要输入文本（0:不需要  n:需要n条）", task.getNeedText());
            map.put("完成时间", task.getGmtFinish());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

	@Override
	public void close(String id) {
		if(!taskClient.taskClose(id)) {
			throw new BadRequestException("下架任务失败");
		}
	}

	@Override
	public void open(String id) {
		if(!taskClient.taskOpen(id)) {
			throw new BadRequestException("上架任务失败");
		}
	}

	@Override
	public void checkSuccess(String id) {
		if(!taskClient.checkSuccess(id)) {
			throw new BadRequestException("操作失败");
		}
	}

	@Override
	public void checkFailure(String id) {
		if(!taskClient.checkFailure(id)) {
			throw new BadRequestException("操作失败");
		}
	}
}