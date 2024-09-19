package org.qingcha.security.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo {
    private String username;
    private String avatar;
    private String email;
    private String remark;
    private List<String> roles;
}
