package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
            //logger.error(e);
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());

    }

    @ResponseBody
    @RequestMapping(value = "/game/initOnce", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String initOnce() {
        try {
            gameService.initOnce();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());

    }

  /*  @ResponseBody
    @RequestMapping(value = "/game/initPlayoff", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String initPlayOff() {
        try {
            gameService.refactor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("index", Code.SUCCESS.getCode());

    }*/


    @ResponseBody
    @RequestMapping(value = "/game/test", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String testLog() throws Exception {
        gameService.test();
        return toJson("index", Code.SUCCESS.getCode());

    }
}
