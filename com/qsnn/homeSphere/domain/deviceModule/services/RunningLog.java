package qsnn.homeSphere.domain.deviceModule.services;

import java.util.Date;

/**
 * 设备运行日志类
 *
 * <p>该类表示设备的运行日志记录，用于跟踪设备的状态变化和操作历史。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>记录设备运行事件的时间、类型和描述</li>
 *   <li>支持不同级别的日志分类（信息、警告、错误）</li>
 *   <li>提供日志信息的完整访问接口</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>所有字段均为final，确保日志记录的不可变性</li>
 *   <li>支持多种日志类型的分类管理</li>
 *   <li>简单的值对象设计，专注于日志数据存储</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class RunningLog {

    /** 日志记录时间 */
    private final Date dateTime;

    /** 事件描述 */
    private final String event;

    /** 日志类型 */
    private final Type type;

    /** 附加说明信息 */
    private final String note;

    /**
     * 运行日志构造函数
     *
     * @param dateTime 日志记录时间
     * @param event 事件描述
     * @param type 日志类型
     * @param note 附加说明信息
     */
    public RunningLog(Date dateTime, String event, Type type, String note) {
        this.dateTime = dateTime;
        this.event = event;
        this.type = type;
        this.note = note;
    }

    /**
     * 获取日志记录时间
     *
     * @return 日志记录时间
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * 获取事件描述
     *
     * @return 事件描述信息
     */
    public String getEvent() {
        return event;
    }

    /**
     * 获取日志类型
     *
     * @return 日志类型枚举值
     */
    public Type getType() {
        return type;
    }

    /**
     * 获取附加说明信息
     *
     * @return 附加说明信息
     */
    public String getNote() {
        return note;
    }

    /**
     * 日志类型枚举
     *
     * <p>定义设备运行日志的不同级别类型</p>
     */
    public enum Type {
        /** 信息日志 - 用于记录正常的设备操作和状态变化 */
        INFO,

        /** 警告日志 - 用于记录需要关注的异常情况 */
        WARNING,

        /** 错误日志 - 用于记录设备故障和严重错误 */
        ERROR
    }
}