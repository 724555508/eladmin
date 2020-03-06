package me.zhengjie.modules.task.repository;

import me.zhengjie.modules.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Aroli
* @date 2020-03-04
*/
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}