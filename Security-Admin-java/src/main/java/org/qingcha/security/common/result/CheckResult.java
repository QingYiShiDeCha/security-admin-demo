package org.qingcha.security.common.result;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CheckResult {
    @Getter
    @Setter
    private Integer errCode;
    private Boolean success;
    @Getter
    @Setter
    private Claims claim;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
