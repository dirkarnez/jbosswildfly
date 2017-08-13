package org.jboss.tools.example.springmvc.mvc;

import java.util.List;

import javax.validation.Valid;

import org.jboss.tools.example.springmvc.domain.Member;
import org.jboss.tools.example.springmvc.repo.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class MemberController
{
    @Autowired
    private MemberDao memberDao;

    @RequestMapping(method=RequestMethod.GET)
    public String displaySortedMembers(Model model)
    {
    	List<Member> members = memberDao.findAllOrderedByName();
        model.addAttribute("newMember", new Member());
        model.addAttribute("members", members);
        model.addAttribute("newMember", memberDao.findNewComeMember());
//        model.addAttribute("membersJson", (new Gson()).toJson(members));
        return "index";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String registerNewMember(@Valid @ModelAttribute("newMember") Member newMember, BindingResult result, Model model)
    {
        if (!result.hasErrors()) {
            memberDao.register(newMember);
            return "redirect:/";
        }
        else {
            model.addAttribute("members", memberDao.findAllOrderedByName());
            return "index";
        }
    }
}
