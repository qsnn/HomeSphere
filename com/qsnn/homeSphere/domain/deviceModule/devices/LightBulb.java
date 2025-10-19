package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

/**
 * 智能灯泡设备类
 *
 * <p>该类表示智能家居系统中的智能灯泡设备，继承自Device基类并实现能源报告功能。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>控制灯泡的亮度和色温</li>
 *   <li>实现能源消耗报告功能</li>
 *   <li>提供灯光调节和控制功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自Device基类，具备设备通用特性</li>
 *   <li>实现EnergyReporting接口，支持能耗监控</li>
 *   <li>支持亮度调节和色温控制</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class LightBulb extends Device implements EnergyReporting {

    /** 灯泡功率，单位：瓦 */
    private double power = 100;

    /** 亮度级别，范围：0-100 */
    private int brightness;

    /** 色温值，单位：开尔文 */
    private int colorTemp;

    /**
     * 智能灯泡设备构造函数
     *
     * @param deviceID 设备唯一标识符
     * @param name 设备名称
     * @param manufacturer 设备制造商信息
     */
    public LightBulb(Integer deviceID, String name, Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
    }

    /**
     * 获取亮度级别
     *
     * @return 亮度值，范围：0-100
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * 设置亮度级别
     *
     * @param brightness 新的亮度值，范围：0-100
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     * 获取色温值
     *
     * @return 色温值，单位：开尔文
     */
    public int getColorTemp() {
        return colorTemp;
    }

    /**
     * 设置色温值
     *
     * @param colorTemp 新的色温值，单位：开尔文
     */
    public void setColorTemp(int colorTemp) {
        this.colorTemp = colorTemp;
    }

    /**
     * 获取灯泡功率
     *
     * @return 灯泡功率值，单位：瓦
     */
    @Override
    public double getPower() {
        return power;
    }


}