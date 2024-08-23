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
        return memberService.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No member found with name: " + name));
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
/**
 @Controller
 public class MemberController {
 private final MemberService memberService;
 protected MemberService getMemberService(){
 return memberService;
 }
 @Autowired
 public MemberController(MemberService memberService) {
 this.memberService = memberService;
 }
 @GetMapping(value = "/members/new")
 public String createForm() {
 return "members/createMemberForm";
 }

 @PostMapping(value = "/members/new")
 public String create(MemberForm form) {
 Member member = new Member();
 member.setName(form.getName());
 System.out.println("member.getName() = " + member.getName());
 memberService.join(member);
 return "redirect:/";
 }
 @GetMapping(value = "/members")
 public String list(Model model) {
 List<Member> members = memberService.findMembers();
 model.addAttribute("members", members);
 return "members/memberList";
 }
 }
 */

