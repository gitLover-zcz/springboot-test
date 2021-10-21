package com.example.project.controller;

import com.example.project.dto.LoginDTO;
import com.example.project.service.AccountService;
import com.example.project.service.ResourceService;
import com.example.project.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("login")
    public String login(String username, String password, HttpSession session,
                        RedirectAttributes redirectAttributes, Model model) {
        LoginDTO login = accountService.login(username, password);
        String error = login.getError();
        if (error == null) {
            session.setAttribute("account", login.getAccount());
            List<ResourceVO> resourceVOS = resourceService.listResourceByRoleId(login.getAccount().getRoleId());
            model.addAttribute("resources", resourceVOS);

            // 将资源转化为代码模块名称的集合
            HashSet<String> hashSet = resourceService.convert(resourceVOS);
            session.setAttribute("model", hashSet);
        } else {
            redirectAttributes.addFlashAttribute("error", login.getError());
        }
        return login.getPath();
    }

    /**
     * 登出方法
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
