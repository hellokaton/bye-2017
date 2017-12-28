package io.github.biezhi.bye2017.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

/**
 * @author biezhi
 * @date 2017/12/7
 */
@Data
@Table("blacklist")
public class Blacklist extends ActiveRecord {

    private Integer id;
    private String  ip;
    private Date    created;
    private Date    expired;

}
