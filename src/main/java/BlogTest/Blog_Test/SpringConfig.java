package BlogTest.Blog_Test;

import BlogTest.Blog_Test.aop.TimeTraceAop;
import BlogTest.Blog_Test.member.repository.SpringDataJpaMemberRepository;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;
    @Autowired
    public SpringConfig(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.springDataJpaMemberRepository = springDataJpaMemberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(springDataJpaMemberRepository);
    }
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

}
