package qsnn.homeSphere.domain.automationScene;

import qsnn.homeSphere.domain.deviceModule.Device;

public class DeviceAction {
    private String command;

    private String parameters;
    private Device device;

    public DeviceAction() {
    }

    public DeviceAction(String command, String parameters, Device device) {
        this.command = command;
        this.parameters = parameters;
        this.device = device;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void execute(){

    }
}
