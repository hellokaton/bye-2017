package io.github.biezhi.bye2017.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

/**
 * @author biezhi
 * @date 2017/12/6
 */
@Data
@Table("topics")
public class Topic extends ActiveRecord {

    private Long   id;
    private String topic;
    private String content;
    private String rid;
    private Boolean mock;
    private Date   created;

}
