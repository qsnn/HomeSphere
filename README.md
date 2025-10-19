# HomeSphere v1.0

## 📖 项目简介

HomeSphere 是一个基于 Java 开发的智能家居系统，采用面向对象的设计理念，提供完整的设备管理、家庭管理、自动化场景和能耗监控功能。系统采用模块化架构，支持多种智能设备类型和连接协议。

## 🏗️ 系统架构

### 核心模块结构
```
HomeSphere System
├── 👤 用户管理模块 (User)
├── 🏠 家庭管理模块 (Household, Room)
├── 🔌 设备管理模块 (Device, Manufacturer)
├── ⚙️ 属性管理模块 (DeviceAttribute)
├── 🤖 自动化场景模块 (AutomationScene)
├── 📝 日志管理模块 (Log)
└── 🛠️ 工具类模块 (Util)
```

### 核心类关系
- **HomeSphereSystem** - 系统核心管理类，统一管理所有实体和关系
- **Device** - 设备抽象基类，所有具体设备类型的父类
- **DeviceAttribute** - 设备属性接口，支持泛型属性管理
- **AutomationScene** - 自动化场景管理，支持批量设备操作
- **Household/Room** - 家庭和房间的空间管理
- **User** - 用户管理和权限控制

## ✨ 主要特性

### 🏠 完整的家庭管理
- 多用户家庭共享
- 房间分区管理
- 家庭成员权限控制

### 🔌 丰富的设备支持
- **设备类型**：空调、灯泡、智能锁、体重秤等
- **连接方式**：WiFi、蓝牙、ZigBee、Z-Wave、Thread、Matter
- **供电模式**：电池供电、主电源供电
- **属性管理**：布尔属性、范围属性、字符串选择属性

### 🤖 智能自动化
- 可配置的自动化场景
- 批量设备操作执行
- 场景验证和执行监控
- 设备操作参数验证

### 📊 能耗监控
- 实时功率计算
- 时间段能耗统计
- 设备使用记录分析
- 自动生成能耗报告

### 🔒 完整的日志系统
- 操作日志记录
- 设备使用历史
- 系统事件追踪
- 多级别日志类型（INFO、WARNING、ERROR）

## 💻 技术栈

### 后端技术
- **Java** - 核心开发语言
- **面向对象设计** - 继承、接口、抽象类
- **泛型编程** - 类型安全的属性管理
- **集合框架** - Map、Set 等数据结构的广泛应用

### 设计模式
- **工厂模式** - 设备创建
- **策略模式** - 属性验证
- **观察者模式** - 日志记录
- **模板方法模式** - 设备操作

## 🔧 核心类说明

### 设备管理系统

#### Device (抽象基类)
```java
// 核心功能
- 设备状态管理（在线状态、电源状态）
- 属性动态管理
- 能耗计算
- 操作日志记录
```

#### 具体设备类型
- `AirConditioner` - 空调设备
- `LightBulb` - 智能灯泡
- `SmartLock` - 智能门锁
- `BathroomScale` - 体重秤
- `UndefinedDevice` - 未定义设备（支持自定义属性）

### 属性管理系统

#### DeviceAttribute 接口
```java
public interface DeviceAttribute<T> {
    String getName();
    T getValue();
    boolean setValue(T value);
    boolean validate(Object value);
}
```

#### 具体属性类型
- `BooleanAttribute` - 布尔类型属性
- `RangeAttribute` - 范围数值属性（带单位）
- `StringChoiceAttribute` - 字符串选择属性（预定义选项）

### 自动化场景系统

#### AutomationScene 类
```java
// 主要功能
- 多设备批量操作
- 操作参数验证
- 场景执行管理
- 设备操作合并
```

## 🚀 快速开始

### 环境要求
- Java 8+
- JUnit 4.13.1 (用于测试)

### 基础使用示例

#### 1. 系统初始化和用户注册
```java
// 用户注册
Integer userId = system.registerUser("alice", "password123", "Alice", "Beijing");

// 创建家庭
Integer householdId = system.createHousehold("Alice's Home", "Beijing", userId);
```

#### 2. 创建设备
```java
// 创建空调设备
Manufacturer manufacturer = new Manufacturer("Xiaomi", EnumSet.of(ConnectMode.WIFI));
Integer acId = system.createDevice(
    DeviceType.AIR_CONDITIONER, 
    deviceId, "Living Room AC", "Android", 
    manufacturer, 1500.0, ConnectMode.WIFI, PowerMode.MAINSPOWER
);
```

#### 3. 设备属性操作
```java
// 设置温度属性
Device ac = system.getDeviceByID(acId);
boolean success = ac.setAttribute("temperature", 25);

// 获取当前模式
String mode = ac.getAttribute("mode");
```

#### 4. 创建自动化场景
```java
// 创建回家场景
Integer sceneId = system.createAutomationScene(householdId, "回家模式", "自动开启灯光和空调");

// 添加设备操作
AutomationScene scene = system.getAutomationSceneByID(sceneId);
scene.addDeviceOperation(light, "brightness", 80);
scene.addDeviceOperation(ac, "temperature", 24);

// 执行场景
scene.execute();
```

## 🗃️ 数据存储

系统使用内存数据存储，主要数据结构包括：

### 核心映射表
- `households` - 家庭数据映射
- `users` - 用户数据映射
- `rooms` - 房间数据映射
- `devices` - 设备数据映射
- `automationScenes` - 自动化场景映射

### 关系映射表
- `userToHouseholds` - 用户-家庭关系
- `householdToUsers` - 家庭-用户关系
- `deviceToRoom` - 设备-房间关系
- `householdToScenes` - 家庭-场景关系

## ✅ 测试覆盖

系统包含完整的测试套件 `HomeSphereSystemTest`，覆盖以下功能：

- ✅ 用户注册和登录
- ✅ 家庭和房间管理
- ✅ 设备创建和操作
- ✅ 自动化场景测试
- ✅ 能耗计算验证
- ✅ 系统查询功能
- ✅ 日志系统测试

## 🔨 扩展开发

### 添加新设备类型
1. 继承 `Device` 抽象类
2. 实现 `initializeAttributes()` 方法
3. 在 `DeviceType` 枚举中添加新类型
4. 在 `HomeSphereSystem.createDeviceByType()` 中注册

### 添加新属性类型
1. 实现 `DeviceAttribute` 接口或继承 `AbstractDeviceAttribute`
2. 实现属性特定的验证逻辑
3. 在设备初始化时添加属性实例

## 📋 版本信息

**当前版本**：v1.0  
**作者**：qsnn  
**发布日期**：2025

## 📄 许可证

本项目采用开源许可证，具体信息请查看 LICENSE 文件。

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request 来帮助改进 HomeSphere。

---

*HomeSphere - 让智能家居更简单、更智能*
```
