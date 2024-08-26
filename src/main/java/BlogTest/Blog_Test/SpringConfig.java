package BlogTest.Blog_Test;

import BlogTest.Blog_Test.aop.TimeTraceAop;
import BlogTest.Blog_Test.blog.repository.PostRepository;
import BlogTest.Blog_Test.blog.service.PostService;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    @Autowired
    public SpringConfig(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
    @Bean
    public PostService postService() {
        return new PostService(postRepository);
    }

    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

}
