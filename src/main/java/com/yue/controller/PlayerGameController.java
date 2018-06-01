package com.yue.controller;

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
    @RequestMapping(value = "/gamePlayer/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String init() {
        playerGameService.init();
        playerGameService.refactor();
        return toJson("index", 200);
    }


    @ResponseBody
    @RequestMapping(value = "/gamePlayer/updateO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateO() {
        playerGameService.updateO();
        return toJson("index", 200);
    }

}
