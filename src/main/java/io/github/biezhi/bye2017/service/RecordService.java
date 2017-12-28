package io.github.biezhi.bye2017.service;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.page.Page;
import com.blade.kit.Hashids;
import com.blade.kit.StringKit;
import com.blade.security.web.filter.HTMLFilter;
import io.github.biezhi.bye2017.model.dto.RecordDto;
import io.github.biezhi.bye2017.model.entity.Record;
import io.github.biezhi.bye2017.model.entity.Topic;
import io.github.biezhi.bye2017.model.param.My2017;
import io.github.biezhi.bye2017.utils.RelativeDateFormat;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author biezhi
 * @date 2017/12/7
 */
@Bean
@Slf4j
public class RecordService {

    private Hashids           hashids           = new Hashids("bye-my-2017");
    private HTMLFilter htmlFilter = new HTMLFilter();

    @Inject
    private GenerateWordCloud generateWordCloud;

    public void saveRecord(My2017 my2017) {
        Record record = new Record();
        record.setCreated(new Date());
        record.setLocation(my2017.getLocation());
        record.setNickname(my2017.getNickname());
        record.setIp(my2017.getIp());
        record.setPicid(StringKit.rand(1, 30));

        String rid = hashids.encode(2017, System.currentTimeMillis());
        record.setRid(rid);
        record.setSex(Byte.valueOf(my2017.getSex()));
        record.setStars(0);
        if (StringKit.isNotBlank(my2017.getHomeUrl())) {
            record.setWebsite(my2017.getHomeUrl());
        } else {
            record.setWebsite("");
        }
        record.save();

        if (StringKit.isNotBlank(my2017.getOutlook())) {
            this.saveTopic(rid, "展望", my2017.getOutlook());
        }

        if (StringKit.isNotBlank(my2017.getFeeling())) {
            this.saveTopic(rid, "感情", my2017.getFeeling());
        }

        if (StringKit.isNotBlank(my2017.getJob())) {
            this.saveTopic(rid, "工作", my2017.getJob());
        }

        if (StringKit.isNotBlank(my2017.getBook())) {
            this.saveTopic(rid, "读书", my2017.getBook());
        }
        if (StringKit.isNotBlank(my2017.getHealth())) {
            this.saveTopic(rid, "身体健康", my2017.getHealth());
        }
        if (StringKit.isNotBlank(my2017.getMusic())) {
            this.saveTopic(rid, "音乐", my2017.getMusic());
        }
        if (StringKit.isNotBlank(my2017.getTechnology())) {
            this.saveTopic(rid, "技术", my2017.getTechnology());
        }
        if (StringKit.isNotBlank(my2017.getOther())) {
            this.saveTopic(rid, "其他", my2017.getOther());
        }
        // 后台重新生成词云图
        //generateWordCloud.generator();
    }

    private void saveTopic(String rid, String topicName, String content) {
        Topic topic = new Topic();
        topic.setCreated(new Date());
        topic.setMock(false);
        topic.setRid(rid);
        topic.setTopic(topicName);
        topic.setContent(htmlFilter.filter(content));
        topic.save();
    }

    public void addStar(String rid) {
        Record record = new Record();
        record.execute("update `record` set stars=stars+1 where rid = ?", rid);
    }

    public List<RecordDto> queryRecords(String orderBy, int page, int size) {
        if (page < 1) {
            page = 1;
        }
        if (size > 12) {
            size = 12;
        }

        Page<Record> recordPage = new Record().page(page, size, orderBy);
        return recordPage.getRows().stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    private RecordDto parse(Record record) {
        RecordDto recordDto = new RecordDto();
        recordDto.setId(record.getRid());
        recordDto.setPicid(record.getPicid());
        recordDto.setNickname(record.getNickname());
        recordDto.setStars(record.getStars());
        recordDto.setCreated(RelativeDateFormat.format(record.getCreated()));
        recordDto.setWebsite(record.getWebsite());
        List<Topic> topics = new Topic().where("rid", record.getRid()).findAll();
        String content = topics.stream().map(topic -> "<font color=red>" + topic.getTopic() + "</font>: " + topic.getContent().replace("\n", "<br/>"))
                .collect(Collectors.joining("<br/><br/>"));

        String intro = topics.get(0).getContent();
        if (intro.length() > 40) {
            intro = intro.substring(0, 40);
        }
        recordDto.setIntro(intro.replace("\n", "&nbsp;"));
        recordDto.setContent(content);
        return recordDto;
    }

}
