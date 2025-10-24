# HomeSphere 智能家居系统

## 📖 项目简介

HomeSphere 是一个基于 Java 开发的智能家居系统，采用面向对象的设计理念，提供完整的设备管理、家庭管理、自动化场景和能耗监控功能。系统采用模块化架构，支持多种智能设备类型。

## 🏗️ 系统架构

### 核心模块结构
```
HomeSphere System
├── 👤 用户管理模块 (User)
├── 🏠 家庭管理模块 (Household, Room)
├── 🔌 设备管理模块 (Device, Manufacturer)
├── 🤖 自动化场景模块 (AutomationScene, DeviceAction)
├── ⚡ 能源管理模块 (EnergyReporting)
└── 📝 运行日志模块 (RunningLog)
```

### 核心类关系
- **HomeSphereSystem** - 系统核心管理类，统一管理所有实体和关系
- **Device** - 设备抽象基类，所有具体设备类型的父类
- **AutomationScene** - 自动化场景管理，支持批量设备操作
- **DeviceAction** - 设备操作封装，执行具体设备命令
- **Household/Room** - 家庭和房间的空间管理
- **User** - 用户管理和权限控制

## ✨ 主要特性

### 🏠 完整的家庭管理
- 多用户家庭共享
- 房间分区管理
- 家庭成员权限控制
- 第一个用户自动设为管理员

### 🔌 丰富的设备支持
- **空调 (AirConditioner)** - 温度控制，功率管理
- **智能灯泡 (LightBulb)** - 亮度、色温调节
- **智能门锁 (SmartLock)** - 锁定状态管理，电池监控
- **体重秤 (BathroomScale)** - 体重数据记录，电池管理

### 🤖 智能自动化
- 可配置的自动化场景
- 批量设备操作执行
- 设备操作参数验证
- 手动触发场景功能

### ⚡ 能耗监控
- 实时功率计算
- 时间段能耗统计
- 基于运行日志的精确能耗计算
- 支持日、月、年度能耗报告

### 📊 完整的日志系统
- 设备运行日志记录
- 状态变化追踪
- 多级别日志类型（INFO、WARNING、ERROR）
- 时间戳记录

## 💻 技术栈

### 后端技术
- **Java** - 核心开发语言
- **面向对象设计** - 继承、接口、抽象类
- **集合框架** - List、Map 等数据结构的广泛应用

### 设计模式
- **策略模式** - 设备操作执行
- **模板方法模式** - 设备基类设计
- **接口隔离原则** - 能源报告接口

## 🔧 核心类说明

### 设备管理系统

#### Device (抽象基类)
```java
// 核心功能
- 设备状态管理（在线状态、电源状态）
- 运行日志记录
- 设备连接管理
- 电源开关控制
```

#### 具体设备类型
- `AirConditioner` - 空调设备，实现 EnergyReporting 接口
- `LightBulb` - 智能灯泡，实现 EnergyReporting 接口  
- `SmartLock` - 智能门锁，电池状态管理
- `BathroomScale` - 体重秤，体重数据管理

### 自动化场景系统

#### AutomationScene 类
```java
// 主要功能
- 多设备批量操作管理
- 场景基本信息管理
- 手动触发执行
- 设备操作列表维护
```

#### DeviceAction 类
```java
// 主要功能
- 设备操作命令封装
- 参数验证和处理
- 具体设备操作执行
- 错误处理和异常捕获
```

### 能源管理系统

#### EnergyReporting 接口
```java
// 核心功能
- 设备功率获取
- 运行日志访问
- 电源状态查询
- 能耗报告生成（日、月、年统计）
```

## 🚀 快速开始

### 环境要求
- Java 8+

### 基础使用示例

#### 1. 系统初始化和用户注册
```java
// 创建家庭
Household household = new Household(1, "北京市海淀区");
HomeSphereSystem system = new HomeSphereSystem(household);

// 用户注册
User user = system.register("alice", "password123", "Alice", "alice@example.com");

// 用户登录
system.login("alice", "password123");
```

#### 2. 创建设备和制造商
```java
// 创建制造商
Manufacturer manufacturer = new Manufacturer(1, "小米", "WIFI,BLUETOOTH");

// 创建空调设备
AirConditioner ac = new AirConditioner(1, "客厅空调", manufacturer);
ac.setCurrTemp(25.0);
ac.setTargetTemp(24.0);

// 创建智能灯泡
LightBulb light = new LightBulb(2, "客厅主灯", manufacturer);
light.setBrightness(80);
light.setColorTemp(4000);
```

#### 3. 创建房间并添加设备
```java
// 创建客厅
Room livingRoom = new Room(1, "客厅", 25.0);
livingRoom.addDevice(ac);
livingRoom.addDevice(light);

// 添加到家庭
household.addRoom(livingRoom);
```

#### 4. 创建自动化场景
```java
// 创建回家场景
AutomationScene homeScene = new AutomationScene(1, "回家模式", "自动开启灯光和空调");
homeScene.addAction(new DeviceAction("poweron", "", light));
homeScene.addAction(new DeviceAction("settemperature", "24", ac));

// 添加到家庭并执行
household.addAutoScene(homeScene);
system.manualTrigSceneById(1);
```

#### 5. 能源消耗报告
```java
// 生成今日能耗报告
Date today = new Date();
system.displayEnergyReportings(today, today);
```

## 🗃️ 数据模型

### 核心实体关系
- **Household** (1) ↔ (N) **Room**
- **Room** (1) ↔ (N) **Device**  
- **Household** (1) ↔ (N) **User**
- **Household** (1) ↔ (N) **AutomationScene**
- **AutomationScene** (1) ↔ (N) **DeviceAction**
- **DeviceAction** (1) ↔ (1) **Device**

### 制造商管理
- **Manufacturer** (1) ↔ (N) **Device**

## 📊 支持的设备操作

### 智能灯泡 (LightBulb)
- `poweron` - 开启灯泡（默认100%亮度）
- `poweroff` - 关闭灯泡
- `setbrightness` - 设置亮度 (0-100)
- `setcolortemp` - 设置色温

### 空调 (AirConditioner)
- `poweron` - 开启空调（默认24°C）
- `poweroff` - 关闭空调
- `settemperature` - 设置目标温度
- `setcurrtemp` - 设置当前温度

### 智能门锁 (SmartLock)
- `setlocked` - 切换锁定状态
- `setbatterylevel` - 设置电池电量 (0-100)

### 体重秤 (BathroomScale)
- `setbodymass` - 设置体重数据
- `setbatterylevel` - 设置电池电量 (0-100)

## ✅ 系统功能验证

系统包含完整的功能演示，支持：

- ✅ 用户注册、登录和注销
- ✅ 家庭和房间管理
- ✅ 设备创建和状态管理
- ✅ 自动化场景配置和执行
- ✅ 能源消耗监控和报告
- ✅ 运行日志记录和查询

## 🔨 扩展开发

### 添加新设备类型
1. 继承 `Device` 抽象类
2. 如需能耗监控，实现 `EnergyReporting` 接口
3. 在 `DeviceAction.execute()` 中添加对应的执行逻辑

### 添加新设备操作
1. 在 `DeviceAction` 中添加对应的执行方法
2. 实现命令解析和参数验证
3. 调用设备的具体方法

## 📋 版本信息

**当前版本**：1.0  
**作者**：qsnn  
**发布日期**：2025

---

*HomeSphere - 专业的智能家居管理系统*
