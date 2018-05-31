package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.PlayerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yue on 2018/5/31
 */
@Controller
public class PlayerGameController extends BaseController {
    private final PlayerGameService playerGameService;

    @Autowired
    public PlayerGameController(PlayerGameService playerGameService) {
        this.playerGameService = playerGameService;
    }

    @ResponseBody
    @RequestMapping(name = "playerGame/init", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String init() {
        playerGameService.init();
        return toJson("index", Code.SUCCESS.getCode());
    }


    @ResponseBody
    @RequestMapping(name = "playerGame/refactor", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String refactor() {
        playerGameService.refactor();
        return toJson("index", Code.SUCCESS.getCode());
    }
}
