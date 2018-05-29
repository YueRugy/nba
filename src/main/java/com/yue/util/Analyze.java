package com.yue.util;

import com.yue.constant.GamePrimary;
import com.yue.constant.GameSuccess;
import com.yue.constant.GameType;
import com.yue.entity.Game;
import com.yue.entity.Team;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;

/**
 * Created by yue on 2018/5/29
 */
public class Analyze {


    public static Game getGame(Element e, Team team) {

        Game game = new Game();
        game.setGameType(GameType.season.getValue());
        game.setTeam(team);

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
}
