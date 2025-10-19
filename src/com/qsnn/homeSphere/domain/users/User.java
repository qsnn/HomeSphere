package com.qsnn.homeSphere.domain.users;

import com.qsnn.homeSphere.log.Log;

import java.util.Comparator;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * 用户类
 *
 * <p>该类表示系统用户，包含用户的基本信息、认证信息和操作日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理用户的基本信息（ID、用户名、密码、姓名、地址）</li>
 *   <li>提供用户身份认证信息</li>
 *   <li>记录用户相关的操作日志</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>用户ID为final，确保唯一性和不变性</li>
 *   <li>使用TreeSet存储日志，按时间排序</li>
 *   <li>在构造时自动记录用户注册日志</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class User {
    /** 用户唯一标识符 */
    private final int userID;

    /** 用户名，用于登录认证 */
    private String username;

    /** 用户密码，用于登录认证 */
    private String password;

    /** 用户真实姓名 */
    private String name;

    /** 用户联系地址 */
    private String address;

    /**
     * 用户日志记录集合
     * 使用TreeSet并按日志时间排序，确保日志按时间顺序存储
     */
    protected final Set<Log> userLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //日志记录

    /**
     * 用户构造函数
     *
     * <p>创建用户时会自动记录注册日志到用户日志集合中。</p>
     *
     * @param userID 用户唯一标识符
     * @param username 用户名，用于登录
     * @param password 用户密码
     * @param name 用户真实姓名
     * @param address 用户联系地址
     */
    public User(int userID, String username, String password, String name, String address) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        userLogs.add(new Log(Integer.toString(getUserID()),"注册用户：" + name, Log.LogType.INFO, this.toString())) ;
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取用户ID
     *
     * @return 用户唯一标识符
     */
    public int getUserID() {
        return userID;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取用户真实姓名
     *
     * @return 用户真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取用户地址
     *
     * @return 用户联系地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 获取用户日志集合
     *
     * <p>返回的日志集合按时间顺序排序</p>
     *
     * @return 用户的日志记录集合，按时间排序
     */
    public Set<Log> getUserLogs() {
        return userLogs;
    }

    // ==================== Setter 方法 ====================

    /**
     * 设置用户名
     *
     * @param username 新的用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 设置用户密码
     *
     * @param password 新的用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置用户真实姓名
     *
     * @param name 新的用户真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置用户地址
     *
     * @param address 新的用户联系地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回用户的格式化字符串表示
     *
     * <p>格式：[用户ID - 用户姓名 - 用户名 - 用户地址]</p>
     * <p>示例：[1001 - 张三 - zhangsan - 北京市海淀区xx路xx号]</p>
     *
     * @return 格式化的用户信息字符串
     */
    @Override
    public String toString(){
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getUserID()))
                .add(getName())
                .add(getUsername())
                .add(getAddress())
                .toString();
    }

    /**
     * 比较两个用户对象是否相等
     *
     * <p>基于用户ID进行比较，因为用户ID是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果用户ID相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return userID == user.userID;
    }
}