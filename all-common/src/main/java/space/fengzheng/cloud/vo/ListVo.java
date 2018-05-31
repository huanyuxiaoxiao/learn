package space.fengzheng.cloud.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ListVo implements Serializable {
    private List<?> list;

    public ListVo(List<?> list) {
        this.list = list;
    }

    public static ListVo buildList(ArrayList<UserVo> userVos) {
        return new ListVo(userVos);
    }
}
