package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by yue on 2018/5/21
 */
@Controller
public class PlayerController extends BaseController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;

    }

    @ResponseBody
    @RequestMapping(value = "/player/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String init() {
        try {
            playerService.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());

    }
}
