package com.yue.util;

import com.yue.constant.GamePrimary;
import com.yue.constant.GameSuccess;
import com.yue.constant.GameType;
import com.yue.dao.GameDao;
import com.yue.entity.Game;
import com.yue.entity.Team;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by yue on 2018/5/29
 */
public class Analyze {


    public static Game getGame(Element e, Team team, GameType gameType, String gameTime, GameDao gameDao) {
        String urlData = e.select("td.result_out").first().getElementsByTag("a").first().attr("href");
        List<Game> list = gameDao.findByUrl(urlData);
        if (list != null && list.size() == 2) {
            return null;
        }

        if (list != null && list.size() == 1) {
            if (Objects.equals(list.get(0).getTeam().getId(), team.getId())) {
                return null;
            }
        }

        Game game = new Game();


        game.setOpponentTeam(team);

        game.setGameType(gameType.getValue());
        game.setTeam(team);
        game.setGameTime(gameTime);
        String dateStr = e.select("td.date_out").first().text();
        game.setDate(TimeUtil.strToDate(dateStr));
        String resultStr = e.select("td.wl").first().text();
        if ("胜".equals(resultStr)) {
            game.setResult(GameSuccess.success.getCode());
        } else if ("负".equals(resultStr)) {
            game.setResult(GameSuccess.faild.getCode());
        }

        String priStr = e.select("td.ha").first().text();

        if ("客".equals(priStr)) {
            game.setPri(GamePrimary.no.getCode());
        } else if ("主".equals(priStr)) {
            game.setPri(GamePrimary.yes.getCode());
        }
        game.setUrl(e.select("td.result_out").first().getElementsByTag("a").first().attr("href"));

        int fga = Integer.parseInt(e.select("td.fga").first().text());
        game.setShootNumber(fga);
        int fg = Integer.parseInt(e.select("td.fg").first().text());
        game.setShoot(fg);
        if (fga > 0) {
            BigDecimal fgb = new BigDecimal(fg);
            BigDecimal fgab = new BigDecimal(fga);
            fgb = fgb.divide(fgab, 10, BigDecimal.ROUND_HALF_UP);

            game.setShootRate(fgb);
        } else {
            game.setShootRate(new BigDecimal(0));
        }

        int threepa = Integer.parseInt(e.select("td.threepa").first().text());
        game.setTShootNumber(threepa);
        int threep = Integer.parseInt(e.select("td.threep").first().text());
        game.setTShoot(threep);

        if (threepa > 0) {
            BigDecimal tb = new BigDecimal(threep);
            BigDecimal tab = new BigDecimal(threepa);
            tb = tb.divide(tab, 10, BigDecimal.ROUND_HALF_UP);
            game.setTShootRate(tb);
        } else {
            game.setTShootRate(new BigDecimal(0));
        }


        int fta = Integer.parseInt(e.select("td.fta").first().text());
        game.setPShootNumber(fga);
        int ft = Integer.parseInt(e.select("td.ft").first().text());
        game.setPShoot(fg);

        if (fta > 0) {
            BigDecimal bigDecimal = new BigDecimal(ft);
            BigDecimal bigDecimal1 = new BigDecimal(fta);
            bigDecimal = bigDecimal.divide(bigDecimal1, 10, BigDecimal.ROUND_HALF_UP);
            game.setPShootRate(bigDecimal);
        } else {
            game.setPShootRate(new BigDecimal(0));
        }

        game.setRebounds(Integer.parseInt(e.select("td.trb").first().text()));
        game.setORebound(Integer.parseInt(e.select("td.orb").first().text()));
        game.setTRebound(Integer.parseInt(e.select("td.drb").first().text()));

        game.setAssists(Integer.parseInt(e.select("td.ast").first().text()));
        game.setSteal(Integer.parseInt(e.select("td.stl").first().text()));
        game.setBlock(Integer.parseInt(e.select("td.blk").first().text()));
        game.setMiss(Integer.parseInt(e.select("td.tov").first().text()));
        game.setFoul(Integer.parseInt(e.select("td.pf").first().text()));
        game.setScore(Integer.parseInt(e.select("td.pts").first().text()));


        return game;

    }



    public static void simple(Element data, Game newGame) {
        int fga = Integer.parseInt(data.select("td.fga").first().text());
        newGame.setShootNumber(fga);
        int fg = Integer.parseInt(data.select("td.fg").first().text());
        newGame.setShoot(fg);
        String fr = data.select("td.fgper").first().text();
        if (StringUtils.isEmpty(fr)) {
            newGame.setShootRate(new BigDecimal(0.0));
        } else {
            fr=StringUtil.removeSpecifiedChar(fr,"%");
            newGame.setShootRate(new BigDecimal(fr));
        }


        int threepa = Integer.parseInt(data.select("td.threepa").first().text());
        newGame.setTShootNumber(threepa);
        int threep = Integer.parseInt(data.select("td.threep").first().text());
        newGame.setTShoot(threep);
        String threepper = data.select("td.threepper").first().text();
        if (StringUtils.isEmpty(threepper)) {
            newGame.setTShootRate(new BigDecimal(0.0));
        } else {
            threepper=StringUtil.removeSpecifiedChar(threepper,"%");
            newGame.setTShootRate(new BigDecimal(threepper));
        }

        int fta = Integer.parseInt(data.select("td.fta").first().text());
        newGame.setPShootNumber(fta);
        int ft = Integer.parseInt(data.select("td.ft").first().text());
        newGame.setPShoot(ft);
        String ftper = data.select("td.ftper").first().text();
        if (StringUtils.isEmpty(ftper)) {
            newGame.setPShootRate(new BigDecimal(0.0));
        } else {
            ftper=StringUtil.removeSpecifiedChar(ftper,"%");
            newGame.setPShootRate(new BigDecimal(ftper));
        }
        newGame.setRebounds(Integer.parseInt(data.select("td.trb").first().text()));
        newGame.setORebound(Integer.parseInt(data.select("td.orb").first().text()));
        newGame.setTRebound(Integer.parseInt(data.select("td.drb").first().text()));

        newGame.setAssists(Integer.parseInt(data.select("td.ast").first().text()));
        newGame.setSteal(Integer.parseInt(data.select("td.stl").first().text()));
        newGame.setBlock(Integer.parseInt(data.select("td.blk").first().text()));
        newGame.setMiss(Integer.parseInt(data.select("td.tov").first().text()));
        newGame.setFoul(Integer.parseInt(data.select("td.pf").first().text()));
        newGame.setScore(Integer.parseInt(data.select("td.pts").first().text()));
    }
}
