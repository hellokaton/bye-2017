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
@Table(value = "record", pk = "rid")
public class Record extends ActiveRecord {

    private String  rid;
    private String  nickname;
    private Byte    sex;
    private String  location;
    private String  website;
    private String  ip;
    private Integer picid;
    private Integer stars;
    private Date    created;
}