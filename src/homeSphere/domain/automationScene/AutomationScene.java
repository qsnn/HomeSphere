package homeSphere.domain.automationScene;

import homeSphere.domain.devices.Device;

import java.util.*;

/**
 * 自动化场景实现类
 * 用于管理一组设备操作的集合，可以批量执行
 */
public class AutomationScene {

    // 场景唯一标识
    private final String sceneId;

    // 场景名称
    private String name;

    // 场景描述
    private String description;

    // 是否激活状态
    private boolean isActive;

    /**
     * 自动化操作存储
     * 键：设备对象
     * 值：该设备要执行的操作参数映射
     */
    private final Map<Device, Map<String, Object>> deviceOperations = new HashMap<>();

    // 执行顺序（可选，按添加顺序执行）
    private final List<Device> executionOrder = new ArrayList<>();

    public AutomationScene(String name) {
        this.sceneId = createSceneId();
        this.name = name;
        this.description = "";
        this.isActive = true;
    }

    public AutomationScene(String name, String description) {
        this.sceneId = createSceneId();
        this.name = name;
        this.description = description;
        this.isActive = true;
    }

    public AutomationScene(String sceneId, String name, String description, boolean isActive) {
        this.sceneId = sceneId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    /**
     * 生成场景ID
     */
    private String createSceneId() {
        return "SCENE_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8);
    }

    // ========== 基本Getter/Setter ==========

    public String getSceneId() {
        return sceneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // ========== 操作管理方法 ==========

    /**
     * 获取所有设备操作（不可修改的视图）
     */
    public Map<Device, Map<String, Object>> getDeviceOperations() {
        Map<Device, Map<String, Object>> copy = new HashMap<>();
        for (Map.Entry<Device, Map<String, Object>> entry : deviceOperations.entrySet()) {
            copy.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return Collections.unmodifiableMap(copy);
    }

    /**
     * 添加设备操作（如果设备已存在则合并操作）
     */
    public void addDeviceOperation(Device device, Map<String, Object> operations) {
        if (device == null || operations == null || operations.isEmpty()) {
            throw new IllegalArgumentException("设备和操作不能为空");
        }

        // 如果设备不存在，添加到执行顺序列表
        if (!deviceOperations.containsKey(device)) {
            executionOrder.add(device);
        }

        // 合并操作参数
        Map<String, Object> existingOperations = deviceOperations.get(device);
        if (existingOperations == null) {
            existingOperations = new HashMap<>();
            deviceOperations.put(device, existingOperations);
        }
        existingOperations.putAll(operations);
    }

    /**
     * 添加单个设备操作
     */
    public void addDeviceOperation(Device device, String attribute, Object value) {
        Map<String, Object> operation = new HashMap<>();
        operation.put(attribute, value);
        addDeviceOperation(device, operation);
    }

    /**
     * 移除设备的特定操作
     */
    public boolean removeDeviceOperation(Device device, String attribute) {
        Map<String, Object> operations = deviceOperations.get(device);
        if (operations != null) {
            return operations.remove(attribute) != null;
        }
        return false;
    }

    /**
     * 移除整个设备的操作
     */
    public boolean removeDevice(Device device) {
        executionOrder.remove(device);
        return deviceOperations.remove(device) != null;
    }

    /**
     * 清空所有设备操作
     */
    public void clearAllOperations() {
        deviceOperations.clear();
        executionOrder.clear();
    }

    /**
     * 获取指定设备的操作参数
     */
    public Map<String, Object> getOperationsForDevice(Device device) {
        Map<String, Object> operations = deviceOperations.get(device);
        return operations != null ? Collections.unmodifiableMap(new HashMap<>(operations)) : Collections.emptyMap();
    }

    /**
     * 检查是否包含指定设备
     */
    public boolean containsDevice(Device device) {
        return deviceOperations.containsKey(device);
    }

    /**
     * 获取场景中涉及的设备数量
     */
    public int getDeviceCount() {
        return deviceOperations.size();
    }

    /**
     * 获取所有涉及的设备（按执行顺序）
     */
    public List<Device> getDevicesInOrder() {
        return Collections.unmodifiableList(new ArrayList<>(executionOrder));
    }

    // ========== 执行相关方法 ==========

    /**
     * 执行自动化场景
     * @return 执行是否成功
     */
    public boolean execute() {
        if (!isActive) {
            System.out.println("场景 '" + name + "' 未激活，跳过执行");
            return false;
        }

        System.out.println("开始执行场景: " + name);
        boolean allSuccess = true;
        int successCount = 0;

        // 按顺序执行每个设备的操作
        for (Device device : executionOrder) {
            try {
                Map<String, Object> operations = deviceOperations.get(device);
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

        System.out.println(String.format("场景执行完成: %d/%d 成功", successCount, executionOrder.size()));
        return allSuccess;
    }

    /**
     * 验证设备操作参数
     */
    private boolean validateDeviceOperations(Device device, Map<String, Object> operations) {
        for (Map.Entry<String, Object> entry : operations.entrySet()) {
            String attribute = entry.getKey();
            Object value = entry.getValue();

            // 检查设备是否支持该属性
            if (!device.hasAttribute(attribute)) {
                return false;
            }

            // 验证参数值（如果设备有验证方法）
            // 这里假设Device类有validateParameters方法
        }
        return true;
    }

    /**
     * 测试执行（不实际执行，只验证）
     */
    public boolean validate() {
        for (Device device : deviceOperations.keySet()) {
            Map<String, Object> operations = deviceOperations.get(device);
            if (!validateDeviceOperations(device, operations)) {
                return false;
            }
        }
        return true;
    }

    // ========== 工具方法 ==========

    @Override
    public String toString() {
        return String.format("AutomationScene{id='%s', name='%s', devices=%d, active=%s}",
                sceneId, name, getDeviceCount(), isActive);
    }

    /**
     * 获取场景摘要信息
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("场景: ").append(name).append("\n");
        sb.append("描述: ").append(description).append("\n");
        sb.append("状态: ").append(isActive ? "激活" : "未激活").append("\n");
        sb.append("设备数量: ").append(getDeviceCount()).append("\n");

        for (Device device : executionOrder) {
            Map<String, Object> operations = deviceOperations.get(device);
            sb.append("  - ").append(device.getName()).append(": ");
            sb.append(operations).append("\n");
        }

        return sb.toString();
    }

}