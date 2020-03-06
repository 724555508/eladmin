package me.zhengjie.modules.task.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.task.domain.Task;
import me.zhengjie.modules.task.service.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Aroli
* @date 2020-03-04
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper extends BaseMapper<TaskDto, Task> {

}