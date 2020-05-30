package com.project.bluemarlin2.bluemarlin2.scheduling;

import com.project.bluemarlin2.bluemarlin2.config.ApplicationProperties;
import com.project.bluemarlin2.bluemarlin2.config.ScheduleQueue;
import com.project.bluemarlin2.bluemarlin2.constants.CommonConstants;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.ElasticSearchRepository;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


//https://advenoh.tistory.com/52
@Component
@RequiredArgsConstructor
@Setter
public class EmailJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(EmailJob.class);
    private final JavaMailSender javaMailSender;
    private final UrlSourceRepository urlSourceRepository;
    private final ElasticSearchRepository elasticSearchRepository;
    private final ScheduleQueue scheduleQueue;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        Long urlId = scheduleQueue.blocking().poll();
        List<UrlSource> urlSources= urlSourceRepository.findUrlSourceByUrlId(urlId);

        for (UrlSource urlSource : urlSources) {
            String body = getBody(urlSource);
            sendMail(new EmailRequest(
                    CommonConstants.EMAIL_TITLE
                    ,body
                    ,"admin@bluemarlinsearch.info"
                    ,urlSource.getMember().getEmail()));
        }
        scheduleQueue.blocking().add(urlId);
    }

    private String getBody(UrlSource urlSource) {
        String body;

        List<String> words = urlSource.getKeywords().stream().map(keyword -> keyword.getWord()).collect(Collectors.toList());
        if(urlSource.getKeywordIntersection()){
            List<String> collect = elasticSearchRepository.findByKeywordsInContent(words)
                    .stream()
                    .map(articleData -> articleData.getUrl())
                    .collect(Collectors.toList());
            body = new StringBuilder().append(makeEmailBody(collect)).toString();
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for (String word: words) {
                List<String> collect = elasticSearchRepository.findBySingleKeywordInContent(word)
                        .stream()
                        .map(articleData -> articleData.getUrl())
                        .collect(Collectors.toList());
                stringBuilder.append(makeEmailBody(collect));
            }
            body = stringBuilder.toString();
        };
        logger.info("body=>" + body);
        return body;
    }

    private String makeEmailBody(List<String> urlToSend) {
        String body = null;
        for(String url: urlToSend){
            body += url + "\n";
        }
        return body;
    }

    private void sendMail(EmailRequest emailRequest){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
        try {
            messageHelper.setSubject(emailRequest.getSubject());
            messageHelper.setText(emailRequest.getBody());
            messageHelper.setFrom(emailRequest.getFrom());
            messageHelper.setTo(emailRequest.getTo());
            javaMailSender.send(mimeMessage);
        }catch(MessagingException e){
            logger.error("Mailing error => " + e.getMessage());
        }
    }


    @Data
    static class EmailRequest {
        public EmailRequest(String subject, String body, @Email String from, @Email String to) {
            this.subject = subject;
            this.body = body;
            this.from = from;
            this.to = to;
        }

        private String subject;
        private String body;
        @Email
        private String from;
        @Email
        private String to;
        private LocalDateTime time;
        private ZoneId zoneId;
    }
}
