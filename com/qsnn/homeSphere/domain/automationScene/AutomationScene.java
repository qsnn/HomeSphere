package qsnn.homeSphere.domain.automationScene;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化场景实现类
 *
 * <p>该类用于管理一组设备操作的集合，可以批量执行多个设备的自动化操作。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理自动化场景的基本信息（ID、名称、描述、创建时间）</li>
 *   <li>维护设备与操作参数的映射关系</li>
 *   <li>批量执行设备自动化操作</li>
 *   <li>验证设备操作的合法性</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>场景ID为final，确保唯一性和不变性</li>
 *   <li>自动生成场景ID，不允许外部传入</li>
 *   <li>记录场景创建时间</li>
 *   <li>使用Map结构存储设备操作，支持灵活的参数配置</li>
 *   <li>提供操作验证机制，确保执行安全性</li>
 *   <li>支持操作的批量添加、移除和执行</li>
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

    /** 场景创建时间 */
    private final Date createTime;

    /**
     * 自动化操作存储
     * 键：设备对象
     * 值：该设备要执行的操作参数映射
     */
    private final List<DeviceAction> deviceActions = new ArrayList<>();

    /**
     * 自动化场景构造函数（仅名称）
     *
     * @param name 场景名称
     */
    public AutomationScene(int sceneID, String name) {
        this.sceneId = sceneID;
        this.name = name;
        this.description = "";
        this.createTime = Date.now();
    }

    /**
     * 自动化场景构造函数（名称和描述）
     *
     * @param name 场景名称
     * @param description 场景描述
     */
    public AutomationScene(int sceneID, String name, String description) {
        this.sceneId = sceneID;
        this.name = name;
        this.description = description;
        this.createTime = Date.now();
    }


    // ========== 基本Getter/Setter ==========

    /**
     * 获取场景ID
     *
     * @return 场景唯一标识
     */
    public Integer getSceneId() {
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
     * 获取场景创建时间
     *
     * @return 场景创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    public List<DeviceAction> getActions() {
        return deviceActions;
    }

// ========== 操作管理方法 ==========

    public void addAction(DeviceAction action){
        deviceActions.add(action);
    }

    public void removeAction(DeviceAction action){
        deviceActions.remove(action);
    }

    public void manualTrig(){
        for (DeviceAction action : deviceActions) {
            action.execute();
        }
    }


}