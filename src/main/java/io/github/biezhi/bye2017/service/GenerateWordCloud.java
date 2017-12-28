package io.github.biezhi.bye2017.service;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.Const;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import io.github.biezhi.bye2017.model.entity.Topic;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author biezhi
 * @date 2017/12/11
 */
@Bean
@Slf4j
public class GenerateWordCloud {

    private static final Random RANDOM = new Random();

    public void generator() {
        // 后台重新生成词云图
        Executors.newCachedThreadPool().submit(() -> {
            log.info("开始生成词云图");
            List<Topic> topics = new Topic().findAll();
            List<String> topicString = fenci(
                    topics.parallelStream()
                            .map(Topic::getContent)
                            .collect(Collectors.joining(" "))
            );
            this.generatorTopic(topicString);

            List<String> feelingString = fenci(topics.parallelStream()
                    .filter(topic -> "感情".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));

            this.generatorFeeling(feelingString);

            List<String> jobString = fenci(topics.parallelStream()
                    .filter(topic -> "工作".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));

            this.generatorJob(jobString);

            List<String> bookString = fenci(topics.parallelStream()
                    .filter(topic -> "读书".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));

            this.generatorBook(bookString);

            List<String> musicString = fenci(topics.parallelStream()
                    .filter(topic -> "音乐".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));

            this.generatorMusic(musicString);

            List<String> healthString = fenci(topics.parallelStream()
                    .filter(topic -> "身体健康".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));

            this.generatorHealth(healthString);

            List<String> technologyString = fenci(topics.parallelStream()
                    .filter(topic -> "技术".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));
            this.generatorTechnology(technologyString);

            List<String> outlookString = fenci(topics.parallelStream()
                    .filter(topic -> "展望".equals(topic.getTopic()))
                    .map(Topic::getContent)
                    .collect(Collectors.joining(" ")));
            this.generatorOutlook(outlookString);

            log.info("=================== 生成完毕 ===================");
        });
    }

    /**
     * 生成所有统计
     */
    private void generatorTopic(List<String> keyList) {
        log.info("开始生成所有主题词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
//        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(20));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
//        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/topics.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorFeeling(List<String> keyList) {
        log.info("开始生成所有感情词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/feeling.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);

    }

    private void generatorJob(List<String> keyList) {
        log.info("开始生成所有工作词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/job.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorBook(List<String> keyList) {
        log.info("开始生成所有读书词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/book.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorMusic(List<String> keyList) {
        log.info("开始生成所有音乐词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/music.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorHealth(List<String> keyList) {
        log.info("开始生成所有健康词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/health.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorTechnology(List<String> keyList) {
        log.info("开始生成所有技术词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(512);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(512, 512);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackgroundColor(Color.BLACK);
        try {
            wordCloud.setBackground(new PixelBoundryBackground(Const.CLASSPATH + "/static/img/github-512.png"));
        } catch (IOException e) {
            log.error("生成词云图失败", e);
        }
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/technology.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private void generatorOutlook(List<String> keyList) {
        log.info("开始生成所有展望词云图");
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(keyList);
        final Dimension           dimension       = new Dimension(600, 600);
        final WordCloud           wordCloud       = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(10));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new SqrtFontScalar(16, 55));
        wordCloud.build(wordFrequencies);
        String path = Const.CLASSPATH + "/static/img/wordcloud/outlook.png";
        wordCloud.writeToFile(path);
        log.info("生成成功到: {}", path);
    }

    private List<String> fenci(String value) {
        List<Term> termList = HanLP.segment(value);
        return termList.parallelStream()
                .map(t -> t.word)
                .map(s -> s.replaceAll("[\\pP‘’“”]", ""))
                .collect(Collectors.toList());
    }

    private static ColorPalette buildRandomColorPalette(final int n) {
        final Color[] colors = new Color[n];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25);
        }
        return new ColorPalette(colors);
    }


}
