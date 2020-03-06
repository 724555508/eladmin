package me.zhengjie.modules.task.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
* @author Aroli
* @date 2020-03-04
*/
@Data
public class TaskDto implements Serializable {

    /** 防止精度丢失 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 任务类型 */
    private Integer type;

    /** 支持设备（1.全部   2.安卓   3.ios） */
    private Integer device;

    /** 项目名称 */
    private String itemName;

    /** 项目标题 */
    private String taskTitle;

    /** 项目备注 */
    private String taskRemark;

    /** 做单限时 */
    private Integer limitTime;

    /** 审核时间 */
    private Integer checkTime;

    /** 悬赏主发布单价 */
    private Double rewardPrice;

    /** 实际用户看到的单价 */
    private Double realityPrice;

    /** 悬赏总额 */
    private Double totalMoney;

    /** 任务总数量 */
    private Integer rewardCount;

    /** 已完成数量 */
    private Integer completedCount;

    /** 步骤 */
    private String steps;

    /** 创建时间 */
    private Timestamp gmtCreate;

    /** 创建人 */
    private Long createId;

    /** 状态（1.待审核   2.已上架  3.已下架 4.已完成 ） */
    private Integer status;

    /** 完成任务时是否需要上传图片（0:不需要  n:需要n张） */
    private Integer needImg;

    /** 完成任务时是否需要输入文本（0:不需要  n:需要n条） */
    private Integer needText;

    /** 完成时间 */
    private Timestamp gmtFinish;
}