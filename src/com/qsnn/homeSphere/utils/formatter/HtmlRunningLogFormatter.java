package com.qsnn.homeSphere.utils.formatter;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.utils.DateUtil;

import java.io.FileOutputStream;

/**
 * HTML格式运行日志格式化器
 *
 * <p>将设备运行日志格式化为HTML表格格式输出</p>
 */
public class HtmlRunningLogFormatter implements RunningLogFormatter {

    @Override
    public String format(Household household) {
        if (household == null) {
            return "<html><body><p>无数据</p></body></html>";
        }

        StringBuilder htmlBuilder = new StringBuilder();

        // HTML文档开始
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html>");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<title>HomeSphere 运行日志</title>");
        htmlBuilder.append("<style>table,th,td{border:1px solid black;border-collapse:collapse;}th,td{padding:8px;text-align:left;}ul{margin:0;padding-left:20px;}</style>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");

        // 标题和基本信息
        htmlBuilder.append("<p>智能家居生态系统\"HomeSphere\" v3.0</p>");
        htmlBuilder.append("<p>householdId：").append(household.getHouseholdId()).append("， address：").append(household.getAddress()).append("</p>");

        // 房间和设备列表开始
        htmlBuilder.append("<ul>");

        // 遍历所有房间
        for (Room room : household.getRooms().values()) {
            // 房间信息
            htmlBuilder.append("<li>roomId：").append(room.getRoomId()).append("， roomName：").append(room.getName()).append("</li>");

            // 遍历房间内的所有设备
            for (Device device : room.getDevices().values()) {
                htmlBuilder.append("<table>");

                // 设备基本信息行
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<th>deviceId</th>");
                htmlBuilder.append("<td>").append(device.getDeviceId()).append("</td>");
                htmlBuilder.append("<th>deviceName</th>");
                htmlBuilder.append("<td>").append(device.getName()).append("</td>");
                htmlBuilder.append("</tr>");

                // 运行日志标题行
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<th colspan=\"4\">runningLogs：</th>");
                htmlBuilder.append("</tr>");

                // 运行日志内容行
                htmlBuilder.append("<td colspan=\"4\">");
                htmlBuilder.append("<ul>");

                // 遍历设备的所有运行日志
                for (RunningLog log : device.getRunningLogs()) {
                    htmlBuilder.append("<li>")
                            .append(DateUtil.formatDateToString(log.getDate())).append(", ")
                            .append(log.getEvent()).append(", ")
                            .append(log.getType().name()).append(", ")
                            .append(log.getNote())
                            .append("</li>");
                }

                htmlBuilder.append("</ul>");
                htmlBuilder.append("</td>");
                htmlBuilder.append("</table>");
                htmlBuilder.append("</p>");
            }
            htmlBuilder.append("</p>");
        }

        // 结束标签
        htmlBuilder.append("</ul>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        String result = htmlBuilder.toString();

        saveToFile(result, "HomeSphere_RunningLogs.html");

        return result;
    }

    /**
     * 将HTML内容保存到文件
     *
     * @param htmlContent 文本
     * @param filePath 文件路径
     */
    private void saveToFile(String htmlContent, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(htmlContent.getBytes());
            System.out.println("HTML文件已保存至: " + filePath);
        } catch (java.io.IOException e) {
            System.err.println("保存文件失败: " + e.getMessage());
        }
    }
}