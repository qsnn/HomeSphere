package com.qsnn.homeSphere.ui;

import java.util.List;
import java.util.Map;

public enum PageType {
    /**登录界面*/
    START_PAGE,
    LOGIN,

    /**菜单*/
    MENU_PAGE,

    //用户相关
    MEMBER_PAGE,
    ADD_MEMBER,
    REMOVE_MEMBER,
    LIST_MEMBERS,

    //设备相关
    DEVICE_PAGE,
    ADD_DEVICE,
    REMOVE_DEVICE,
    LIST_DEVICES,

    //场景相关
    SCENE_PAGE,
    ADD_SCENE,
    RUN_SCENE,
    LIST_SCENES,

    //日志相关
    LOG_PAGE,
    DEVICE_LOGS,
    ENERGY_REPORT,

    EXIT_SYSTEM;

    private String title;
    private List<String> options;
    private Map<Integer, PageType> choiceMap;

    // 静态初始化块
    static {
        START_PAGE.title = "===智能家居生态系统“HomeSphereG” v2.0===";
        START_PAGE.options = List.of("1. 用户登录", "2. 退出系统");
        START_PAGE.choiceMap = Map.of(1, LOGIN, 2, EXIT_SYSTEM);

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
                5, START_PAGE);

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
                4, MENU_PAGE);

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

    // getters
    public String getTitle() { return title; }
    public List<String> getOptions() { return options; }
    public Map<Integer, PageType> getChoiceMap() { return choiceMap; }

    public boolean hasMenu() {
        return title != null;
    }
}