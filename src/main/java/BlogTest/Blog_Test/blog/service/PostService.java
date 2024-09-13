package BlogTest.Blog_Test.blog.service;

import BlogTest.Blog_Test.blog.domain.Post;
import BlogTest.Blog_Test.blog.repository.PostRepository;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public PostService(PostRepository postRepository, MemberRepository memberRepository){
        this.postRepository =  postRepository;
        this.memberRepository = memberRepository;
    }
    //포스트 작성
    public Post create (Post post){
        boolean check = validateDuplicatePost(post);
        if(check){
            try {
                postRepository.save(post);
            } catch (DataIntegrityViolationException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "데이터 무결성 오류", e);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", e);
            }
            return post;
        }else   return null;
    }
    //중복 확인
    boolean validateDuplicatePost(Post post) {
        Post postCompare = findPostByTitleMemberId(post.getMemberId(), post.getTitle());
        if (postCompare==null)    return true;
        else if(postCompare.getMemberId().equals(post.getMemberId())) return false;
        else return false;
    }
    public List<Post> findAllPost(){
        return postRepository.findAll();
    }
    public Post findPost(long id){
        return postRepository.findById(id).get();
    }
    //내 포스트 목록 조회
    public List<Post> findPostsByMember(long memberId){
        return postRepository.findByMemberId(memberId);
    }
    //내 포스트 타이틀 조회
    public Post findPostByTitleMemberId(Long memberId, String title){
        // 동일한 타이틀을 가진 모든 포스트를 조회
        List<Post> postsWithSameTitle = postRepository.findByTitle(title);

        for (Post post : postsWithSameTitle) {
            if (post.getMemberId().equals(memberId))  return post;
        }
        return null;
    }
    //전체 포스트 타이틀 조회
    public List<Post> findPostByTitle(String title){
        return postRepository.findByTitle(title);
    }
    //포스트 수정
    public boolean changePost(Post post){
        Post postInfo = postRepository.findById(post.getId()).get();
        if (postInfo!=null){
            postInfo.setTitle(post.getTitle());
            postInfo.setContent(post.getContent());
            postRepository.save(postInfo);
            return true;
        }else return false;
    }
    public boolean deletePost(Long id){
        Post post = postRepository.findById(id).get();
        if (post==null){
            return false;
        }else{
            postRepository.delete(post);
            return true;
        }
    }
}
