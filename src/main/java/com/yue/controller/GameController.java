package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yue on 2018/5/22
 */
@Controller
public class GameController extends BaseController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @ResponseBody
    @RequestMapping(value = "/game/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String init() {
        try {
            gameService.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());

    }
}
