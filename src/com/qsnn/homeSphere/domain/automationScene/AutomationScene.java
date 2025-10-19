package com.qsnn.homeSphere.domain.automationScene;

import com.qsnn.homeSphere.domain.deviceModule.Device;

import java.util.*;

/**
 * 自动化场景实现类
 *
 * <p>该类用于管理一组设备操作的集合，可以批量执行多个设备的自动化操作。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理自动化场景的基本信息（ID、名称、描述）</li>
 *   <li>维护设备与操作参数的映射关系</li>
 *   <li>批量执行设备自动化操作</li>
 *   <li>验证设备操作的合法性</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>场景ID为final，确保唯一性和不变性</li>
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
    private final String sceneId;

    /** 场景名称 */
    private String name;

    /** 场景描述 */
    private String description;

    /**
     * 自动化操作存储
     * 键：设备对象
     * 值：该设备要执行的操作参数映射
     */
    private final Map<Device, Map<String, Object>> deviceActions = new HashMap<>();

    /**
     * 自动化场景构造函数（仅名称）
     *
     * @param name 场景名称
     */
    public AutomationScene(String name) {
        this.sceneId = createSceneId();
        this.name = name;
        this.description = "";
    }

    /**
     * 自动化场景构造函数（名称和描述）
     *
     * @param name 场景名称
     * @param description 场景描述
     */
    public AutomationScene(String name, String description) {
        this.sceneId = createSceneId();
        this.name = name;
        this.description = description;
    }

    /**
     * 自动化场景构造函数（完整参数）
     *
     * @param sceneId 场景ID
     * @param name 场景名称
     * @param description 场景描述
     */
    public AutomationScene(String sceneId, String name, String description) {
        this.sceneId = sceneId;
        this.name = name;
        this.description = description;
    }

    /**
     * 生成场景ID
     *
     * <p>基于当前时间戳生成唯一的场景标识符</p>
     *
     * @return 生成的场景ID
     */
    private String createSceneId() {
        return "SCENE_" + System.currentTimeMillis();
    }

    // ========== 基本Getter/Setter ==========

    /**
     * 获取场景ID
     *
     * @return 场景唯一标识
     */
    public String getSceneId() {
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

    // ========== 操作管理方法 ==========

    /**
     * 获取所有设备操作（不可修改的视图）
     *
     * <p>返回设备操作的深度拷贝，确保原始数据的安全性</p>
     *
     * @return 不可修改的设备操作映射
     */
    public Map<Device, Map<String, Object>> getDeviceActions() {
        Map<Device, Map<String, Object>> copy = new HashMap<>();
        for (Map.Entry<Device, Map<String, Object>> entry : deviceActions.entrySet()) {
            copy.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return Collections.unmodifiableMap(copy);
    }

    /**
     * 添加设备操作（如果设备已存在则合并操作）
     *
     * @param device 目标设备
     * @param operations 操作参数映射
     * @throws IllegalArgumentException 如果设备或操作为空
     */
    public void addDeviceOperation(Device device, Map<String, Object> operations) {
        if (device == null || operations == null || operations.isEmpty()) {
            throw new IllegalArgumentException("设备和操作不能为空");
        }

        // 合并操作参数
        Map<String, Object> existingOperations = deviceActions.get(device);
        if (existingOperations == null) {
            existingOperations = new HashMap<>();
            deviceActions.put(device, existingOperations);
        }
        existingOperations.putAll(operations);
    }

    /**
     * 添加单个设备操作
     *
     * @param device 目标设备
     * @param attribute 操作属性
     * @param value 属性值
     */
    public void addDeviceOperation(Device device, String attribute, Object value) {
        Map<String, Object> operation = new HashMap<>();
        operation.put(attribute, value);
        addDeviceOperation(device, operation);
    }

    /**
     * 移除设备的特定操作
     *
     * @param device 目标设备
     * @param attribute 要移除的操作属性
     * @return 如果成功移除返回true，否则返回false
     */
    public boolean removeDeviceOperation(Device device, String attribute) {
        Map<String, Object> operations = deviceActions.get(device);
        if (operations != null) {
            return operations.remove(attribute) != null;
        }
        return false;
    }

    /**
     * 移除整个设备的操作
     *
     * @param device 要移除的设备
     * @return 如果成功移除返回true，否则返回false
     */
    public boolean removeDevice(Device device) {
        return deviceActions.remove(device) != null;
    }

    /**
     * 清空所有设备操作
     */
    public void clearAllOperations() {
        deviceActions.clear();
    }

    /**
     * 获取指定设备的操作参数
     *
     * @param device 目标设备
     * @return 设备的操作参数映射（不可修改）
     */
    public Map<String, Object> getOperationsForDevice(Device device) {
        Map<String, Object> operations = deviceActions.get(device);
        return operations != null ? Collections.unmodifiableMap(new HashMap<>(operations)) : Collections.emptyMap();
    }

    /**
     * 检查是否包含指定设备
     *
     * @param device 要检查的设备
     * @return 如果包含该设备返回true，否则返回false
     */
    public boolean containsDevice(Device device) {
        return deviceActions.containsKey(device);
    }

    /**
     * 获取场景中涉及的设备数量
     *
     * @return 设备数量
     */
    public int getDeviceCount() {
        return deviceActions.size();
    }

    /**
     * 获取所有涉及的设备（按执行顺序）
     *
     * @return 设备列表
     */
    public List<Device> getDevicesInOrder() {
        return List.copyOf(deviceActions.keySet());
    }

    // ========== 执行相关方法 ==========

    /**
     * 执行自动化场景
     *
     * @return 执行是否完全成功
     */
    public boolean execute() {
        System.out.println("开始执行场景: " + name);
        boolean allSuccess = true;
        int successCount = 0;

        // 按顺序执行每个设备的操作
        for (Device device : deviceActions.keySet()) {
            try {
                Map<String, Object> operations = deviceActions.get(device);
                if (operations != null && !operations.isEmpty()) {
                    // 验证设备是否支持这些操作
                    if (validateDeviceOperations(device, operations)) {
                        device.execute(device, operations);
                        successCount++;
                        System.out.println("✓ 设备 '" + device.getName() + "' 操作执行成功");
                    } else {
                        System.out.println("✗ 设备 '" + device.getName() + "' 操作验证失败");
                        allSuccess = false;
                    }
                }
            } catch (Exception e) {
                System.err.println("✗ 设备 '" + device.getName() + "' 执行失败: " + e.getMessage());
                allSuccess = false;
            }
        }

        System.out.println(String.format("场景执行完成: %d/%d 成功", successCount, deviceActions.size()));
        return allSuccess;
    }

    /**
     * 验证设备操作参数
     *
     * @param device 目标设备
     * @param operations 操作参数
     * @return 验证是否通过
     */
    private boolean validateDeviceOperations(Device device, Map<String, Object> operations) {
        for (Map.Entry<String, Object> entry : operations.entrySet()) {
            String attribute = entry.getKey();
            Object value = entry.getValue();

            // 检查设备是否支持该属性
            if (!device.hasAttribute(attribute)) {
                return false;
            }

        }
        return true;
    }

    /**
     * 测试执行（不实际执行，只验证）
     *
     * @return 验证是否通过
     */
    public boolean validate() {
        for (Device device : deviceActions.keySet()) {
            Map<String, Object> operations = deviceActions.get(device);
            if (!validateDeviceOperations(device, operations)) {
                return false;
            }
        }
        return true;
    }

    // ========== 工具方法 ==========

    /**
     * 返回场景的字符串表示
     *
     * @return 格式化的场景信息
     */
    @Override
    public String toString() {
        return String.format("AutomationScene{id='%s', name='%s', devices=%d}",
                sceneId, name, getDeviceCount());
    }

    /**
     * 获取场景摘要信息
     *
     * @return 详细的场景摘要
     */
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("场景: ").append(name).append("\n");
        sb.append("描述: ").append(description).append("\n");
        sb.append("设备数量: ").append(getDeviceCount()).append("\n");

        for (Device device : deviceActions.keySet()) {
            Map<String, Object> operations = deviceActions.get(device);
            sb.append("  - ").append(device.getName()).append(": ");
            sb.append(operations).append("\n");
        }

        return sb.toString();
    }

    /**
     * 比较两个自动化场景对象是否相等
     *
     * <p>基于场景ID进行比较，因为场景ID是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果场景ID相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AutomationScene that = (AutomationScene) obj;
        return sceneId.equals(that.sceneId);
    }
}