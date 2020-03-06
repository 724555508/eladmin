package me.zhengjie.modules.task.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author Aroli
* @date 2020-03-04
*/
@Data
public class TaskQueryCriteria{

	/** 精确 */
    @Query
    private Long id;

    /** 精确 */
    @Query
    private Integer type;

    /** 精确 */
    @Query
    private Integer device;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String itemName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String taskTitle;

    /** 精确 */
    @Query
    private Integer limitTime;

    /** 精确 */
    @Query
    private Integer checkTime;

    /** 精确 */
    @Query
    private Long createId;

    /** 精确 */
    @Query
    private Integer status;
}