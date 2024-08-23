package BlogTest.Blog_Test;

import BlogTest.Blog_Test.aop.TimeTraceAop;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

}
