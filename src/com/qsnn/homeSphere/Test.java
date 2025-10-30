package com.qsnn.homeSphere;

import com.qsnn.homeSphere.ui.CommandUI;

/**
 * 智能家居系统测试启动类
 *
 * <p>该类是智能家居系统的入口点，用于启动命令行用户界面进行系统测试和演示。</p>
 *
 * <p><b>主要用途：</b></p>
 * <ul>
 *   <li>启动智能家居系统的命令行界面</li>
 *   <li>进行系统功能测试</li>
 *   <li>演示系统各项功能</li>
 * </ul>
 *
 * <p><b>使用说明：</b></p>
 * <ol>
 *   <li>运行该类的主方法启动系统</li>
 *   <li>系统会自动初始化预设数据（用户、房间、设备、场景等）</li>
 *   <li>按照命令行提示进行操作</li>
 * </ol>
 *
 * <p><b>预设数据：</b></p>
 * <ul>
 *   <li>管理员账户：admin/admin</li>
 *   <li>预设房间：客厅、卧室、厨房</li>
 *   <li>预设设备：空调、智能灯泡、智能锁等</li>
 *   <li>预设场景：回家模式、睡眠模式</li>
 * </ul>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see CommandUI
 */
public class Test {

    /**
     * 应用程序主入口方法
     *
     * <p>该方法初始化并启动智能家居系统的命令行用户界面。</p>
     *
     * <p><b>执行流程：</b></p>
     * <ol>
     *   <li>创建 CommandUI 实例</li>
     *   <li>触发系统初始化（setup方法）</li>
     *   <li>启动用户交互控制器（UIController方法）</li>
     *   <li>进入命令行交互循环</li>
     * </ol>
     *
     * @param args 命令行参数（本系统未使用命令行参数）
     */
    public static void main(String[] args) {
        new CommandUI();
    }
}