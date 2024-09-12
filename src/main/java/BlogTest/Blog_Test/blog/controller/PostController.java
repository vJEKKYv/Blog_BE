package BlogTest.Blog_Test.blog.controller;

import BlogTest.Blog_Test.blog.domain.Post;
import BlogTest.Blog_Test.blog.domain.dto.SavePostRequestDTO;
import BlogTest.Blog_Test.blog.service.PostService;
import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    //글 등록
    @PostMapping(value = "/post/new")
    public ResponseEntity<String> Posting(SavePostRequestDTO postReqDTO){
        Post post = new Post();
        post.setTitle(postReqDTO.getTitle());
        post.setContent(postReqDTO.getContent());
        post.setMemberId(postReqDTO.getMemberId());
        long id = postService.create(post);
        if (id==-1){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Title already exsist");
        }
        return ResponseEntity.ok("id: "+ id +  "Posting Success.");
    }
    //전체 post 조회
    @GetMapping(value = "/post/all")
    public List<Post> findPostAll(){
        List<Post> posts = postService.findAllPost();
        return posts;
    }
    //맴버별 포스트 조회
    @GetMapping(value = "/post/member")
    public List<Post> findPostByMember(@RequestParam("memberId")Long memberId){
        List<Post> post = postService.findPostsByMember(memberId);
        return post;
    }
    //포스트 타이틀 조회
    @GetMapping(value = "/post/title")
    public List<Post> findPostByTitle(@RequestParam("title")String title){
        List<Post> post = postService.findPostByTitle(title);
        return post;
    }
    //포스트 수정
    @PutMapping(value = "/post")
    public ResponseEntity<String> changePost(SavePostRequestDTO postReqDTO){
        Post post = postService.findPost(postReqDTO.getId());
        if (post==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
        else if(postService.findPostByTitleMemberId(postReqDTO.getMemberId(), postReqDTO.getTitle())!=null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Title already exsist");
        }
        post.setTitle(postReqDTO.getTitle());
        post.setContent(postReqDTO.getContent());
        boolean check = postService.changePost(post);
        if(check){
            return ResponseEntity.ok("Update Success.");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed.");
        }
    }
    @DeleteMapping(value = "/post")
    public ResponseEntity<String> deletePostById(@RequestParam("id")long id){
        boolean check = postService.deletePost(id);
        if(check){
            return ResponseEntity.ok("Delete Success.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }
}
