package vip.aquan.controller;

import com.google.common.collect.Lists;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import vip.aquan.bo.UserBO;
import vip.aquan.pojo.User;
import vip.aquan.util.Encodes;
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


    /**
     * 导出多工作表excel
     * @param response
     */
    @RequestMapping(value = "exportMultipleData")
    @ResponseBody
    public void exportMultipleData(HttpServletResponse response) {
        String fileName = "多工作表excel.xlsx";

        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));

        WritableWorkbook book = null;
        ArrayList<User> list1 = Lists.newArrayList();
        User user1 = new User(1,"甲一","111");
        User user2 = new User(2,"乙二","222");
        list1.add(user1);
        list1.add(user2);
        ArrayList<User> list2 = Lists.newArrayList();
        User user3 = new User(3,"丙三","333");
        User user4 = new User(4,"丁四","444");
        list2.add(user3);
        list2.add(user4);
        ArrayList<User> list3 = Lists.newArrayList();
        User user5 = new User(5,"王五","555");
        User user6 = new User(6,"赵柳","666");
        list3.add(user5);
        list3.add(user6);
        try {
            book = Workbook.createWorkbook(response.getOutputStream());
            WritableSheet sheet1 = book.createSheet("工作表1", 0);
            addCell(sheet1, list1);

            WritableSheet sheet2 = book.createSheet("工作表2", 1);
            addCell(sheet2, list2);

            WritableSheet sheet3 = book.createSheet("工作表3", 2);
            addCell(sheet3, list3);

            book.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addCell(WritableSheet sheet, List<User> list) throws WriteException {
        sheet.addCell(new Label(0, 0, "编号"));
        sheet.addCell(new Label(1, 0, "用户名"));
        sheet.addCell(new Label(2, 0, "密码"));

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                int j = 0;
                User user = list.get(i);

                sheet.addCell(new Label(j++, i + 1, user.getId()+""));
                sheet.addCell(new Label(j++, i + 1, user.getUsername()));
                sheet.addCell(new Label(j++, i + 1, user.getPassword()));
            }
        }
    }
}
