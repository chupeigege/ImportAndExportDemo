package vip.aquan.bo;

import vip.aquan.util.excel.annotation.ExcelField;

/**
 * 在这里填描述
 *
 * @author wcp
 * @date 2019/7/4
 */
public class UserBO {
    private Integer id;
    private String username;
    private String password;

    @ExcelField(title = "编号", sort = 1, align = 1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ExcelField(title = "用户名", sort = 2)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ExcelField(title = "密码", sort = 3)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
