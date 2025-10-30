package com.qsnn.homeSphere.ui;

import java.util.List;
import java.util.Map;

/**
 * 页面类型枚举
 *
 * <p>定义智能家居系统命令行界面的所有页面类型，包含页面标题、选项和导航映射。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>定义系统所有界面页面的枚举常量</li>
 *   <li>管理页面间的导航关系</li>
 *   <li>提供页面标题和选项内容</li>
 *   <li>支持用户选择到目标页面的映射</li>
 * </ul>
 *
 * <p><b>页面层次结构：</b></p>
 * <ol>
 *   <li>起始页面 (START_PAGE) - 系统入口</li>
 *   <li>菜单页面 (MENU_PAGE) - 主功能菜单</li>
 *   <li>功能子页面 - 成员管理、设备管理、场景管理、日志管理</li>
 *   <li>操作页面 - 具体的添加、删除、列表显示等操作页面</li>
 * </ol>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 */
public enum PageType {
    /** 起始页面 - 系统登录界面 */
    START_PAGE,

    /** 用户登录页面 */
    LOGIN,

    /** 主菜单页面 - 系统功能导航 */
    MENU_PAGE,

    // 用户相关页面
    /** 成员管理主页面 */
    MEMBER_PAGE,
    /** 添加成员页面 */
    ADD_MEMBER,
    /** 删除成员页面 */
    REMOVE_MEMBER,
    /** 成员列表页面 */
    LIST_MEMBERS,

    // 设备相关页面
    /** 设备管理主页面 */
    DEVICE_PAGE,
    /** 添加设备页面 */
    ADD_DEVICE,
    /** 删除设备页面 */
    REMOVE_DEVICE,
    /** 设备列表页面 */
    LIST_DEVICES,

    // 场景相关页面
    /** 场景管理主页面 */
    SCENE_PAGE,
    /** 创建场景页面 */
    ADD_SCENE,
    /** 触发场景页面 */
    RUN_SCENE,
    /** 场景列表页面 */
    LIST_SCENES,

    // 日志相关页面
    /** 日志管理主页面 */
    LOG_PAGE,
    /** 设备运行日志页面 */
    DEVICE_LOGS,
    /** 能耗报告页面 */
    ENERGY_REPORT,

    /** 退出系统 */
    EXIT_SYSTEM;

    /** 页面标题 */
    private String title;

    /** 页面选项列表 */
    private List<String> options;

    /** 用户选择到目标页面的映射 */
    private Map<Integer, PageType> choiceMap;

    /**
     * 静态初始化块 - 初始化所有页面的标题、选项和导航映射
     *
     * <p>在类加载时执行，为每个页面枚举常量设置相应的界面配置。</p>
     */
    static {
        // 起始页面配置
        START_PAGE.title = "===智能家居生态系统“HomeSphereG” v2.0===";
        START_PAGE.options = List.of(
                "1. 用户登录",
                "2. 退出系统"
        );
        START_PAGE.choiceMap = Map.of(
                1, LOGIN,
                2, EXIT_SYSTEM
        );

        // 主菜单页面配置
        MENU_PAGE.title = "===家庭管理菜单===";
        MENU_PAGE.options = List.of(
                "1. 管理家庭成员",
                "2. 管理房间设备",
                "3. 智能场景管理",
                "4. 日常能耗管理",
                "5. 退出登录"
        );
        MENU_PAGE.choiceMap = Map.of(
                1, MEMBER_PAGE,
                2, DEVICE_PAGE,
                3, SCENE_PAGE,
                4, LOG_PAGE,
                5, START_PAGE
        );

        // 成员管理页面配置
        MEMBER_PAGE.title = "===成员管理===";
        MEMBER_PAGE.options = List.of(
                "1. 添加成员",
                "2. 删除成员",
                "3. 列出所有成员",
                "4. 返回上级"
        );
        MEMBER_PAGE.choiceMap = Map.of(
                1, ADD_MEMBER,
                2, REMOVE_MEMBER,
                3, LIST_MEMBERS,
                4, MENU_PAGE
        );

        // 设备管理页面配置
        DEVICE_PAGE.title = "===设备管理===";
        DEVICE_PAGE.options = List.of(
                "1. 添加设备",
                "2. 删除设备",
                "3. 列出所有设备",
                "4. 返回上级"
        );
        DEVICE_PAGE.choiceMap = Map.of(
                1, ADD_DEVICE,
                2, REMOVE_DEVICE,
                3, LIST_DEVICES,
                4, MENU_PAGE
        );

        // 场景管理页面配置
        SCENE_PAGE.title = "===场景管理===";
        SCENE_PAGE.options = List.of(
                "1. 创建新场景",
                "2. 触发场景",
                "3. 列出所有场景",
                "4. 返回上级"
        );
        SCENE_PAGE.choiceMap = Map.of(
                1, ADD_SCENE,
                2, RUN_SCENE,
                3, LIST_SCENES,
                4, MENU_PAGE
        );

        // 日志管理页面配置
        LOG_PAGE.title = "===日志能耗管理===";
        LOG_PAGE.options = List.of(
                "1. 查看设备运行日志",
                "2. 查看能耗报告",
                "3. 返回上级"
        );
        LOG_PAGE.choiceMap = Map.of(
                1, DEVICE_LOGS,
                2, ENERGY_REPORT,
                3, MENU_PAGE
        );
    }

    /**
     * 获取页面标题
     *
     * @return 页面标题字符串
     */
    public String getTitle() { return title; }

    /**
     * 获取页面选项列表
     *
     * @return 页面选项字符串列表
     */
    public List<String> getOptions() { return options; }

    /**
     * 获取用户选择到目标页面的映射
     *
     * @return 选择编号到目标页面的映射表
     */
    public Map<Integer, PageType> getChoiceMap() { return choiceMap; }

    /**
     * 检查页面是否具有菜单（标题和选项）
     *
     * <p>用于判断页面是否是可交互的菜单页面，而不是简单的操作页面。</p>
     *
     * @return 如果页面有标题（即是一个菜单页面）返回true，否则返回false
     */
    public boolean hasMenu() {
        return title != null;
    }
}