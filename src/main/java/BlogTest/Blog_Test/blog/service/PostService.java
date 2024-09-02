package BlogTest.Blog_Test.blog.service;

import BlogTest.Blog_Test.blog.domain.Post;
import BlogTest.Blog_Test.blog.repository.PostRepository;
import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public PostService(PostRepository postRepository, MemberRepository memberRepository){
        this.postRepository =  postRepository;
        this.memberRepository = memberRepository;
    }
    //포스트 작성
    public Long join (Post post){
        postRepository.save(post);
        return post.getId();
    }
    //나의 포스트 목록
    public List<Post> findPostsByMember(Member member){
        return postRepository.findByMemberId(member.getId());
    }
    //타이틀 조회
    public List<Post> findPostByTitle(String title){
        return postRepository.findByTitle(title);
    }
    //포스트 수정
    public Post changePost(Long id, String title, String content){
        Post post = postRepository.findById(id).get();
        if(post == null){
            throw new NoSuchElementException("No post found with id: " + id);
        }
        post.setTitle(title);
        post.setContent(content);
        return post;
    }
}
