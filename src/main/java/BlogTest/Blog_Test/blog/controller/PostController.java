package BlogTest.Blog_Test.blog.controller;

import BlogTest.Blog_Test.blog.domain.Post;
import BlogTest.Blog_Test.blog.domain.dto.SavePostRequestDTO;
import BlogTest.Blog_Test.blog.domain.dto.SavePostResponseDTO;
import BlogTest.Blog_Test.blog.service.PostService;
import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> Posting(@RequestBody SavePostRequestDTO postRequestDTO){
        Post post = new Post();
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setMemberId(postRequestDTO.getMemberId());
        post = postService.create(post);
        
        SavePostResponseDTO postResponseDTO = new SavePostResponseDTO();
        postResponseDTO.setTitle(post.getTitle());
        postResponseDTO.setContent(post.getContent());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", post != null);
        requestMap.put("message", post != null ? "조회 성공" : "조회 실패");
        requestMap.put("postInfo", postResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //전체 post 조회
    @GetMapping(value = "/post/all")
    public ResponseEntity<Map<String, Object>> findPostAll(){
        List<Post> posts = postService.findAllPost();

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", posts != null);
        requestMap.put("message", posts != null ? "조회 성공" : "조회 실패");
        requestMap.put("postInfo", posts);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //맴버별 포스트 조회
    @GetMapping(value = "/post/member")
    public ResponseEntity<Map<String, Object>> findPostByMember(@RequestParam("memberId")Long memberId){
        List<Post> post = postService.findPostsByMember(memberId);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", post != null);
        requestMap.put("message", post != null ? "조회 성공" : "조회 실패");
        requestMap.put("postInfo", post);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);

    }

    //포스트 타이틀 조회
    @GetMapping(value = "/post/title")
    public ResponseEntity<Map<String, Object>> findPostByTitle(@RequestParam("title")String title){
        List<Post> post = postService.findPostByTitle(title);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", post != null);
        requestMap.put("message", post != null ? "조회 성공" : "조회 실패");
        requestMap.put("postInfo", post);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //포스트 수정
    @PutMapping(value = "/post")
    public ResponseEntity<Map<String, Object>> changePost(@RequestBody SavePostRequestDTO postRequestDTODTO){
        Post post = postService.findPost(postRequestDTODTO.getId());
        if (post==null){

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("success", false);
            requestMap.put("message", "post 조회 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(requestMap);
        }
        else if(postService.findPostByTitleMemberId(postRequestDTODTO.getMemberId(), postRequestDTODTO.getTitle())!=null){
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("success", false);
            requestMap.put("message", "post title 중복");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(requestMap);
        }

        post.setTitle(postRequestDTODTO.getTitle());
        post.setContent(postRequestDTODTO.getContent());
        boolean check = postService.changePost(post);

        SavePostResponseDTO postResponseDTO = new SavePostResponseDTO();
        postResponseDTO.setTitle(post.getTitle());
        postResponseDTO.setContent(post.getContent());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", check);
        requestMap.put("message", check ? "변경 성공" : "변경 실패");
        requestMap.put("postInfo", postResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    // 포스트 삭제
    @DeleteMapping(value = "/post")
    public ResponseEntity<Map<String, Object>> deletePostById(@RequestParam("id")long id){
        boolean check = postService.deletePost(id);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", check);
        requestMap.put("message", check ? "삭제 성공" : "삭제 실패");

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }
}
