package BlogTest.Blog_Test.member.controller;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.domain.dto.SaveMemberRequestDTO;
import BlogTest.Blog_Test.member.domain.dto.SaveMemberResponseDTO;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { // MemberService
        this.memberService = memberService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody SaveMemberRequestDTO memberReqDTO) {
        Member member = memberService.login(memberReqDTO.getName(), memberReqDTO.getPassword());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", member != null);
        requestMap.put("message", member != null ? "로그인 성공" : "로그인 실패");
        requestMap.put("userInfo", member);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //회원가입
    @PostMapping(value = "/member/new")
    public ResponseEntity<Map<String, Object>> create(@RequestBody SaveMemberRequestDTO memberRequestDTO) {
        Member member = new Member();
        member.setName(memberRequestDTO.getName());
        member.setPassword(memberRequestDTO.getPassword());
        member = memberService.join(member);

        SaveMemberResponseDTO memberResponseDTO = new SaveMemberResponseDTO();
        memberResponseDTO.setName(member.getName());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", member != null);
        requestMap.put("message", member != null ? "회원가입 성공" : "회원가입 실패");
        requestMap.put("userInfo", memberResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //맴버 전체 조회
    @GetMapping(value = "/member/all")
    public ResponseEntity<Map<String, Object>> list() {
        List<Member> members = memberService.findMembers();

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", members != null);
        requestMap.put("message", members != null ? "조회 성공" : "조회 실패");
        requestMap.put("userInfo", members);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //맴버 id 조회
    @GetMapping(value = "/member/id")
    public ResponseEntity<Map<String, Object>> findMemberById(@RequestParam("id") Long id) {
        Member member = memberService.findOne(id);

        SaveMemberResponseDTO memberResponseDTO = new SaveMemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setName(member.getName());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", member != null);
        requestMap.put("message", member != null ? "조회 성공" : "조회 실패");
        requestMap.put("userInfo", memberResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //맴버 삭제
    @DeleteMapping(value = "/member")
    public ResponseEntity<Map<String, Object>> deleteMemberByName(@RequestParam("name") String name) {
        boolean check = memberService.deleteByName(name);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", check);
        requestMap.put("message", check ? "삭제 성공" : "삭제 실패");

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //맴버 정보 변경
    @PutMapping(value = "/member")
    public ResponseEntity<Map<String, Object>> changeMember(@RequestBody SaveMemberRequestDTO memberRequestDTO) {
        Member member = memberService.findMember(memberRequestDTO.getId());

        member.setName(memberRequestDTO.getName());
        member.setPassword(memberRequestDTO.getPassword());
        if (member== null){
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("success", false);
            requestMap.put("message", "member 조회 실패");
            return ResponseEntity.status(HttpStatus.OK).body(requestMap);
        }
        boolean check = memberService.changeMemberInfo(member);

        SaveMemberResponseDTO memberResponseDTO = new SaveMemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setName(member.getName());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", check);
        requestMap.put("message", check ? "변경 성공" : "변경 실패");
        requestMap.put("userInfo", memberResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }
}