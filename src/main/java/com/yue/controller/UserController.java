package com.yue.controller;

import com.yue.constant.Code;
import com.yue.constant.UserCode;
import com.yue.entity.User;
import com.yue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yue on 2018/5/16
 */
@Controller
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public String save(User user) {
        User dataUser = userService.findByUsername(user.getUsername());
        if (dataUser == null) {
            userService.save(user);
        }
        return "success";
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET, consumes = "application/json")
    public String getByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return toJson(user, Code.SUCCESS.getCode());
    }

    @RequestMapping(value = "/user/hello", method = RequestMethod.GET)
    public String test(Model model) {
        System.out.println("hhhhhhh");
        model.addAttribute("name", "yue");
        return "index";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(User user) {
        User dataUser = userService.findByUsernameAndPassword(user);
        if (dataUser != null) {
            return "home";
        }
        return "error";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(User user) {

        if (userService.register(user) == UserCode.success.getCode()) {
            return "home";
        }

        return "error";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String registerHtml() {
        return "register";
    }
}
