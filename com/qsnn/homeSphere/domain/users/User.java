package qsnn.homeSphere.domain.users;


import java.util.StringJoiner;

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
    private final int userId;

    /** 用户名，用于登录认证 */
    private String loginName;

    /** 用户密码，用于登录认证 */
    private String loginPassword;

    /** 用户真实姓名 */
    private String userName;

    /** 用户联系地址 */
    private String email;

    private boolean isAdmin;


    /**
     * 用户构造函数
     *
     * <p>创建用户时会自动记录注册日志到用户日志集合中。</p>
     *
     * @param userId 用户唯一标识符
     * @param loginName 用户名，用于登录
     * @param loginPassword 用户密码
     * @param userName 用户真实姓名
     * @param email 用户联系地址
     */
    public User(int userId, String loginName, String loginPassword, String userName, String email) {
        this.userId = userId;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.userName = userName;
        this.email = email;
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
     * 获取用户地址
     *
     * @return 用户联系地址
     */
    public String getEmail() {
        return email;
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
     * 设置用户地址
     *
     * @param email 新的用户联系地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
                .add(Integer.toString(getUserId()))
                .add(getUserName())
                .add(getLoginName())
                .add(getEmail())
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
        return userId == user.userId;
    }
}