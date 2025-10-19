package com.qsnn.homeSphere.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 日志类
 *
 * <p>该类用于记录系统的各种操作日志，包括信息、警告和错误等不同类型的日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>记录操作日志的基本信息（ID、执行者、时间、事件、类型、备注）</li>
 *   <li>自动生成唯一的日志标识符</li>
 *   <li>提供日志的格式化输出和写入功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>日志ID和时间戳为final，确保日志记录的不可变性</li>
 *   <li>自动生成基于时间戳的唯一日志ID</li>
 *   <li>在构造时自动输出日志内容</li>
 *   <li>支持日志类型的分类管理</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Log {
    /** 日志唯一标识符 */
    protected final String logID;

    /** 日志执行者（通常是设备ID或用户ID） */
    protected final String actor;

    /** 日志记录时间 */
    protected final LocalDateTime t;

    /** 日志事件描述 */
    protected String event;

    /** 日志类型 */
    protected LogType eventType;

    /** 日志备注信息 */
    protected String remarks;

    /**
     * 日志构造函数
     *
     * <p>创建日志时会自动生成日志ID、记录当前时间，并输出日志内容。</p>
     *
     * @param actor 日志执行者
     * @param event 日志事件描述
     * @param eventType 日志类型
     * @param remarks 日志备注信息
     */
    public Log(String actor, String event, LogType eventType, String remarks) {
        t = LocalDateTime.now();
        this.logID = generateLogID();
        this.actor = actor;
        this.event = event;
        this.eventType = eventType;
        this.remarks = remarks == null ? "" : remarks;
        System.out.println(this);
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取日志ID
     *
     * @return 日志唯一标识符
     */
    public String getLogID() {
        return logID;
    }

    /**
     * 获取日志记录时间
     *
     * @return 日志时间戳
     */
    public LocalDateTime getT() {
        return t;
    }

    /**
     * 获取日志事件描述
     *
     * @return 事件描述
     */
    public String getEvent() {
        return event;
    }

    /**
     * 获取日志类型
     *
     * @return 日志类型
     */
    public LogType getEventType() {
        return eventType;
    }

    /**
     * 获取日志备注信息
     *
     * @return 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    // ==================== Setter 方法 ====================

    /**
     * 设置日志事件描述
     *
     * @param event 新的事件描述
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * 设置日志类型
     *
     * @param eventType 新的日志类型
     */
    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }

    /**
     * 设置日志备注信息
     *
     * @param remarks 新的备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // ==================== 业务方法 ====================

    /**
     * 生成日志ID
     *
     * <p>基于当前时间戳和随机数生成唯一的日志标识符</p>
     * <p>格式：LOG + 年月日时分秒 + 3位随机数</p>
     *
     * @return 生成的日志ID
     */
    private String generateLogID() {
        return String.format("LOG%d%02d%02d%02d%02d%02d%03d",
                t.getYear(), t.getMonthValue(), t.getDayOfMonth(),
                t.getHour(), t.getMinute(), t.getSecond(),
                new Random().nextInt(1000));
    }

    /**
     * 写入日志
     *
     * <p>将日志内容输出到控制台</p>
     */
    public void writeLog(){
        System.out.println(this);
    }

    // ==================== 枚举类型定义 ====================

    /**
     * 日志类型枚举
     */
    public enum LogType {
        /** 信息日志 */
        INFO,
        /** 警告日志 */
        WARNING,
        /** 错误日志 */
        ERROR
    }

    // ==================== 重写方法 ====================

    /**
     * 返回日志的格式化字符串表示
     *
     * <p>格式：LOG[日志ID](执行者) 时间 类型 事件 (备注)</p>
     * <p>示例：LOG[LOG20250115143045001](DEV001) 2025-01-15T14:30:45 INFO 设备开启 (主卧室空调)</p>
     *
     * @return 格式化的日志信息字符串
     */
    @Override
    public String toString(){
        return String.format("LOG[%s](%s) %s %s %s (%s)",
                logID, actor,
                t.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                eventType, event, remarks);
    }

    /**
     * 比较两个日志对象是否相等
     *
     * <p>基于日志ID进行比较，因为日志ID是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果日志ID相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Log log = (Log) obj;
        return logID.equals(log.logID);
    }
}