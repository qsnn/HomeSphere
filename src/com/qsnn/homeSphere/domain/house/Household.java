package com.qsnn.homeSphere.domain.house;

import com.qsnn.homeSphere.log.Log;

import java.util.*;

/**
 * 家庭类
 *
 * <p>该类表示一个智能家庭单位，包含家庭的基本信息、管理员设置和操作日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理家庭的基本信息（ID、名称、地址）</li>
 *   <li>设置和管理家庭管理员</li>
 *   <li>记录家庭相关的操作日志</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>家庭ID为final，确保唯一性和不变性</li>
 *   <li>使用TreeSet存储日志，按时间排序</li>
 *   <li>在构造时自动记录家庭创建日志</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Household {
    /** 家庭唯一标识符 */
    private final int householdID;

    /** 家庭名称 */
    private String name;

    /** 家庭地址 */
    private String address;

    /** 管理员用户ID */
    private Integer administratorID;

    /**
     * 家庭日志记录集合
     * 使用TreeSet并按日志时间排序，确保日志按时间顺序存储
     */
    protected final Set<Log> householdLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //日志记录

    /**
     * 家庭构造函数
     *
     * <p>创建家庭时会自动设置创建者为管理员，并记录创建日志到家庭日志集合中。</p>
     *
     * @param householdID 家庭唯一标识符
     * @param name 家庭名称
     * @param address 家庭地址
     * @param creatorID 创建者用户ID，将自动设置为管理员
     */
    public Household(int householdID, String name, String address, Integer creatorID) {
        this.householdID = householdID;
        this.name = name;
        this.address = address;
        administratorID = creatorID;
        householdLogs.add(new Log(Integer.toString(getHouseholdID()),"创建家庭：" + name, Log.LogType.INFO, this.toString()));
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取家庭ID
     *
     * @return 家庭唯一标识符
     */
    public int getHouseholdID() {
        return householdID;
    }

    /**
     * 获取家庭名称
     *
     * @return 家庭名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取家庭地址
     *
     * @return 家庭地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 获取管理员ID
     *
     * @return 管理员用户ID
     */
    public Integer getAdministratorID() {
        return administratorID;
    }

    /**
     * 获取家庭日志集合
     *
     * <p>返回的日志集合按时间顺序排序</p>
     *
     * @return 家庭的日志记录集合，按时间排序
     */
    public Set<Log> getHouseholdLogs() {
        return householdLogs;
    }

    // ==================== Setter 方法 ====================

    /**
     * 设置家庭名称
     *
     * @param name 新的家庭名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置家庭地址
     *
     * @param address 新的家庭地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回家庭的格式化字符串表示
     *
     * <p>格式：[家庭ID - 家庭名称 - 家庭地址]</p>
     * <p>示例：[1001 - 张氏家庭 - 北京市海淀区xx路xx号]</p>
     *
     * @return 格式化的家庭信息字符串
     */
    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getHouseholdID()))
                .add(getName())
                .add(getAddress())
                .toString();
    }

    /**
     * 比较两个家庭对象是否相等
     *
     * <p>基于家庭ID进行比较，因为家庭ID是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果家庭ID相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Household household = (Household) obj;
        return householdID == household.householdID;
    }
}