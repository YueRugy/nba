package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yue on 2018/5/19
 */
@Controller
public class TeamController extends BaseController {
    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ResponseBody
    @RequestMapping(value = "/team/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String init() throws Exception {
        try {
            teamService.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public String team(Model model) throws Exception {

        try {
            model.addAttribute("page", teamService.eastTeam());
            model.addAttribute("page1", teamService.westTeam());
            model.addAttribute("page2", teamService.oldTeam());
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "team";
    }
}
