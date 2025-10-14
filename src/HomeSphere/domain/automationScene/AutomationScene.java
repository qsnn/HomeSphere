package OopHW.Exp1.domain.automationScene;

import OopHW.Exp1.domain.devices.Device;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是自动化场景的实现类
 */

public class AutomationScene {

    //场景名称
    private String name;

/**
 * 这是自动化执行的内容的存储地
 * <p>
 * 以哈希表为数据结构
 * <p>
 * 键：设备（Device）
 * <p>
 * 值：操作细则，这里也是在哈希表内存储<操作属性，具体数值>
 */
    private Map<Device, Map<String, Object>> operators = new HashMap<>();

    public AutomationScene() {
    }

    public AutomationScene(String name) {
        this.name = name;
    }

    public AutomationScene(String name, Map<Device, Map<String, Object>> operators) {
        this.name = name;
        this.operators = operators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //获取自动化操作内容
    public Map<Device, Map<String, Object>> getOperators() {
        return operators;
    }

    //设置自动化操作
    public void setOperators(Map<Device, Map<String, Object>> operators) {
        this.operators = operators;
    }

    //添加/覆盖自动化操作
    public void addOperator(Device d, Map<String, Object> newOperators){
        if (operators.containsKey(d)){
            for (String s : newOperators.keySet()) {
                operators.get(d).put(s, newOperators.get(s));
            }
        }else {
            operators.put(d, newOperators);
        }
    }

    //对自动化操作逐一进行
    public void execute(){
        for (Device d : operators.keySet()) {
            d.execute(d, operators.get(d));
        }
    }
}
