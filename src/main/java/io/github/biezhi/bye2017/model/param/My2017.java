package io.github.biezhi.bye2017.model.param;

import com.blade.validator.annotation.NotEmpty;
import lombok.Data;

/**
 * @author biezhi
 * @date 2017/12/7
 */
@Data
public class My2017 {

    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    @NotEmpty(message = "请选择所在城市")
    private String location;
    @NotEmpty(message = "性别不能为空")
    private String sex;
    private String job;
    private String homeUrl;
    private String feeling;
    private String book;
    private String music;
    private String health;
    private String technology;
    private String outlook;
    private String other;
    private String ip;

}
