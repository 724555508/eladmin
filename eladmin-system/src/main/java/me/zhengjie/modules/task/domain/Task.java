package me.zhengjie.modules.task.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Aroli
* @date 2020-03-04
*/
@Entity
@Data
@Table(name="v_task")
public class Task implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /** 任务类型 */
    @Column(name = "type",nullable = false)
    @NotNull
    private Integer type;

    /** 支持设备（1.全部   2.安卓   3.ios） */
    @Column(name = "device")
    private Integer device;

    /** 项目名称 */
    @Column(name = "item_name")
    private String itemName;

    /** 项目标题 */
    @Column(name = "task_title")
    private String taskTitle;

    /** 项目备注 */
    @Column(name = "task_remark")
    private String taskRemark;

    /** 做单限时 */
    @Column(name = "limit_time",nullable = false)
    @NotNull
    private Integer limitTime;

    /** 审核时间 */
    @Column(name = "check_time",nullable = false)
    @NotNull
    private Integer checkTime;

    /** 悬赏主发布单价 */
    @Column(name = "reward_price")
    private Double rewardPrice;

    /** 实际用户看到的单价 */
    @Column(name = "reality_price")
    private Double realityPrice;

    /** 悬赏总额 */
    @Column(name = "total_money")
    private Double totalMoney;

    /** 任务总数量 */
    @Column(name = "reward_count")
    private Integer rewardCount;

    /** 已完成数量 */
    @Column(name = "completed_count",nullable = false)
    @NotNull
    private Integer completedCount;

    /** 步骤 */
    @Column(name = "steps")
    private String steps;

    /** 创建时间 */
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

    /** 创建人 */
    @Column(name = "create_id")
    private Long createId;

    /** 状态（1.待审核   2.已上架  3.已下架 4.已完成 ） */
    @Column(name = "status")
    private Integer status;

    /** 完成任务时是否需要上传图片（0:不需要  n:需要n张） */
    @Column(name = "need_img")
    private Integer needImg;

    /** 完成任务时是否需要输入文本（0:不需要  n:需要n条） */
    @Column(name = "need_text")
    private Integer needText;

    /** 完成时间 */
    @Column(name = "gmt_finish")
    private Timestamp gmtFinish;

    public void copy(Task source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}