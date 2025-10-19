package qsnn.homeSphere.domain.automationScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自动化场景实现类
 *
 * <p>该类用于管理一组设备操作的集合，可以批量执行多个设备的自动化操作。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理自动化场景的基本信息（ID、名称、描述）</li>
 *   <li>维护设备操作列表</li>
 *   <li>批量执行设备自动化操作</li>
 *   <li>支持手动触发场景</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>场景ID为final，确保唯一性和不变性</li>
 *   <li>使用List结构存储设备操作</li>
 *   <li>提供操作的批量添加、移除和执行</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class AutomationScene {

    /** 场景唯一标识 */
    private final Integer sceneId;

    /** 场景名称 */
    private String name;

    /** 场景描述 */
    private String description;

    /**
     * 设备操作列表
     * 存储该场景下所有要执行的设备操作
     */
    private final List<DeviceAction> deviceActions = new ArrayList<>();

    /**
     * 自动化场景构造函数（仅名称）
     *
     * @param sceneId 场景ID
     * @param name 场景名称
     */
    public AutomationScene(int sceneId, String name) {
        this.sceneId = sceneId;
        this.name = name;
        this.description = "";
    }

    /**
     * 自动化场景构造函数（名称和描述）
     *
     * @param sceneId 场景ID
     * @param name 场景名称
     * @param description 场景描述
     */
    public AutomationScene(int sceneId, String name, String description) {
        this.sceneId = sceneId;
        this.name = name;
        this.description = description;
    }

    // ========== 基本Getter/Setter ==========

    /**
     * 获取场景ID
     *
     * @return 场景唯一标识
     */
    public int getSceneId() {
        return sceneId;
    }

    /**
     * 获取场景名称
     *
     * @return 场景名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置场景名称
     *
     * @param name 新的场景名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取场景描述
     *
     * @return 场景描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置场景描述
     *
     * @param description 新的场景描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取设备操作列表
     *
     * @return 设备操作列表
     */
    public List<DeviceAction> getActions() {
        return new ArrayList<>(deviceActions);
    }

    // ========== 操作管理方法 ==========

    /**
     * 添加设备操作
     *
     * @param action 要添加的设备操作
     */
    public void addAction(DeviceAction action) {
        if (action != null) {
            deviceActions.add(action);
        }
    }

    /**
     * 移除设备操作
     *
     * @param action 要移除的设备操作
     */
    public void removeAction(DeviceAction action) {
        deviceActions.remove(action);
    }

    /**
     * 手动触发场景
     * 执行场景中所有的设备操作
     */
    public void manualTrig() {
        for (DeviceAction action : deviceActions) {
            action.execute();
        }
    }

    // ========== Object类方法重写 ==========

    /**
     * 比较两个自动化场景是否相等（基于场景ID）
     *
     * @param o 要比较的对象
     * @return 如果场景ID相同则返回true，否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomationScene that = (AutomationScene) o;
        return Objects.equals(sceneId, that.sceneId);
    }


    /**
     * 返回对象的字符串表示形式
     * 格式：类名{属性1=属性1值, 属性2='属性2值',...}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "AutomationScene{" +
                "sceneId=" + sceneId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}