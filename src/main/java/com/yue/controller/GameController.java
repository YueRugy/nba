package com.yue.controller;

import com.yue.constant.Code;
import com.yue.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(GameController.class);

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

    @RequestMapping(value = "/game/test", method = RequestMethod.GET)
    public String testLog() throws Exception {
        throw new Exception("aaa");
     //   return toJson("index", Code.SUCCESS.getCode());

    }
}
