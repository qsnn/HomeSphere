package com.qsnn.homeSphere.domain.users;

/**
 * 用户类
 *
 * <p>该类表示系统用户，包含用户的基本信息、认证信息和权限管理。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理用户的基本信息（ID、用户名、密码、姓名、邮箱）</li>
 *   <li>提供用户身份认证信息</li>
 *   <li>管理用户权限（管理员/普通用户）</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>用户ID为final，确保唯一性和不变性</li>
 *   <li>支持管理员权限标识</li>
 *   <li>提供完整的用户信息管理</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class User {
    /** 用户唯一标识符 */
    private final int userId;

    /** 用户名，用于登录认证 */
    private String loginName;

    /** 用户密码，用于登录认证 */
    private String loginPassword;

    /** 用户真实姓名 */
    private String userName;

    /** 用户邮箱地址 */
    private String email;

    /** 管理员权限标识 */
    private boolean isAdmin;

    /**
     * 用户构造函数
     *
     * @param userId 用户唯一标识符
     * @param loginName 用户名，用于登录
     * @param loginPassword 用户密码
     * @param userName 用户真实姓名
     * @param email 用户邮箱地址
     */
    public User(int userId, String loginName, String loginPassword, String userName, String email) {
        this.userId = userId;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.userName = userName;
        this.email = email;
        this.isAdmin = false; // 默认不是管理员
    }


    // ==================== Getter 方法 ====================

    /**
     * 获取用户ID
     *
     * @return 用户唯一标识符
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * 获取用户真实姓名
     *
     * @return 用户真实姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 获取用户邮箱
     *
     * @return 用户邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 检查用户是否为管理员
     *
     * @return 如果是管理员返回true，否则返回false
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    // ==================== Setter 方法 ====================

    /**
     * 设置用户名
     *
     * @param loginName 新的用户名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 设置用户密码
     *
     * @param loginPassword 新的用户密码
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * 设置用户真实姓名
     *
     * @param userName 新的用户真实姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 设置用户邮箱
     *
     * @param email 新的用户邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 设置管理员权限
     *
     * @param admin 是否为管理员
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // ==================== Object类方法重写 ====================

    /**
     * 比较两个用户是否相等（基于用户ID）
     *
     * @param obj 要比较的对象
     * @return 如果用户ID相同则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：User{userId=值, loginName='值', userName='值', email='值', isAdmin=值}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}