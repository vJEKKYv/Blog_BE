package BlogTest.Blog_Test.member.controller;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { // MemberService
        this.memberService = memberService;
    }
    /**
    // controller 참고 예제
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("name") String userName) {
        UserEntity user = userService.login(userName);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("success", user != null);
        requestMap.put("message", user != null ? "로그인 성공" : "로그인 실패");
        requestMap.put("userInfo", user);

        return ResponseEntity.status(HttpStatus.OK).body(requestMap);
    }
     **/
    @PostMapping(value = "/member/new")
    public Member create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        System.out.println("member.getName() = " + member.getName());
        memberService.join(member);
        return member;
    }
    @GetMapping(value = "/member/all")
    public List<Member> list(Model model) {
        List<Member> members = memberService.findMembers();
        return members;
    }
    @PutMapping(value = "/member/change")
    public Member changeName(@RequestParam("name") String name, @RequestParam("newName") String newName) {
        return memberService.changeName(name, newName);
    }

    @GetMapping(value = "/member/name")
    public Member findMemberByName(@RequestParam("name") String name) {
        Member member = memberService.findByName(name);
        if (member != null){
            return member;
        }else {
            throw new NoSuchElementException("No member found with name: " + name);
        }
    }
    @GetMapping(value = "/member/id")
    public Member findMemberById(@RequestParam("id") Long id) {
        return memberService.findOne(id)
                .orElseThrow(() -> new NoSuchElementException("No member found with id: " + id));
    }

    @DeleteMapping(value = "/member/delete")
    public String deleteMemberByName(@RequestParam("name") String name) {
        memberService.deleteByName(name);
        return "Member with name " + name + " deleted successfully";
    }
}

