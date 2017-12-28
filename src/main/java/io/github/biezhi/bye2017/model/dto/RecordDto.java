package io.github.biezhi.bye2017.model.dto;

import lombok.Data;

/**
 * @author biezhi
 * @date 2017/12/10
 */
@Data
public class RecordDto {

    private String  id;
    private String  nickname;
    private String  website;
    private String  intro;
    private String  content;
    private Integer  picid;
    private String  created;
    private Integer stars;

}
