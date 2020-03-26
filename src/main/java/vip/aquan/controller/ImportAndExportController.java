package vip.aquan.controller;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import vip.aquan.bo.UserBO;
import vip.aquan.pojo.User;
import vip.aquan.util.excel.ExportExcel;
import vip.aquan.util.excel.ImportExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入导出控制器
 *
 * @author wcp
 * @date 2019/7/4
 */
@Controller
public class ImportAndExportController {

    @RequestMapping(value = "exportData")
    @ResponseBody
    public String exportData(HttpServletResponse response) {
        try {
            User user1 = new User(1,"张三","123456");
            User user2 = new User(2,"李四","111111");
            ArrayList<User> list = Lists.newArrayList();
            list.add(user1);
            list.add(user2);
            new ExportExcel("用户列表", UserBO.class).setDataList(list).write(response, "用户列表.xlsx").dispose();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "importData")
    @ResponseBody
    public String importData(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        try {
            //解析Excel文件数据
            ImportExcel ie = new ImportExcel(multipartFile, 0, 0);
            List<UserBO> userBOList = ie.getDataList(UserBO.class);
            //去掉标题栏
            userBOList.remove(0);
            for (UserBO userBO:userBOList){
                System.out.println(userBO);
            }
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
