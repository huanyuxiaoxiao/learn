package space.fengzheng.cloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserVo implements Serializable {
    private String id;

    public UserVo() {
        this.id = UUID.randomUUID().toString();
    }
}
