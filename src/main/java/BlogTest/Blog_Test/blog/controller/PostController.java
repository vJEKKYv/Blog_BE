package BlogTest.Blog_Test.blog.controller;

import BlogTest.Blog_Test.blog.domain.Post;
import BlogTest.Blog_Test.blog.service.PostService;
import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
}
