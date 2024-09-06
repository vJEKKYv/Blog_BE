package BlogTest.Blog_Test.member.controller;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<Map<String, Object>> login(MemberForm form) {
        Member member = memberService.login(form.getName(), form.getPassword());

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", member != null);
        requestMap.put("message", member != null ? "로그인 성공" : "로그인 실패");
        requestMap.put("userInfo", member);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }

    //회원가입
    @PostMapping(value = "/member/new")
    public ResponseEntity<String> create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        long id = memberService.join(member);
        if(id == -1){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Name already exsist");
        }
        else {
            return ResponseEntity.ok("id: "+ id +  "\t Join Success.");
        }
    }
    //맴버 전체 조회
    @GetMapping(value = "/member/all")
    public List<Member> list(Model model) {
        List<Member> members = memberService.findMembers();
        return members;
    }
    @PutMapping(value = "/member/password")
    public boolean changePassword(){
        return true;
    }
    //맴버 이름 조회
    @GetMapping(value = "/member/name")
    public Member findMemberByName(@RequestParam("name") String name) {
        Member member = memberService.findByName(name);
        if (member != null){
            return member;
        }else {
            throw new NoSuchElementException("No member found with name: " + name);
        }
    }
    //맴버 id 조회
    @GetMapping(value = "/member/id")
    public Member findMemberById(@RequestParam("id") Long id) {
        Member member = memberService.findOne(id);
        if(member == null){
            throw new NoSuchElementException("No member found with id: " + id);
        }
        return member;
    }
    //맴버 삭제
    @DeleteMapping(value = "/member")
    public ResponseEntity<String> deleteMemberByName(@RequestParam("name") String name) {
        boolean check = memberService.deleteByName(name);
        if(check){
            return ResponseEntity.ok("Delete Success.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found.");
        }
    }
    //맴버 정보 변경
    @PutMapping(value = "/member")
    public ResponseEntity<String> changeMember(MemberForm form) {
        Member member = memberService.findMember(form.getId());
        if (member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found.");
        }
        else if(memberService.findByName(form.getName())!=null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Name already exsist");
        }
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        boolean check = memberService.changeMemberInfo(member);
        if(check){
            return ResponseEntity.ok("Update Success.");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed.");
        }
    }
}