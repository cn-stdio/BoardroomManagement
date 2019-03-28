package com.ectocyst.component;

import com.ectocyst.utils.TimeUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ectocyst.service.impl.BoardroomServiceImpl.END_TIME;
import static com.ectocyst.service.impl.BoardroomServiceImpl.START_TIME;

/**
 * @author Seagull_gby
 * @date 2019/3/7 16:41
 * Description: 起始时间处理
 */

@Component
public class StartTimeDeal {

    /**
     * 将日期格式时间提取小时信息
     * @param startTime 日期格式时间
     * @return 小时
     * @throws ParseException
     */
    public String timeToHour(Date startTime) throws ParseException {

        TimeUtil timeUtil = new TimeUtil();
        String hour = null;

        hour = timeUtil.getFormatDateOfh(startTime);

        if(hour.substring(0, 1).equals("0")) {
            hour = hour.substring(1, 2);
        } else {
            hour = hour.substring(0, 2);
        }

        return hour;
    }

    /**
     * 将一天中空闲时间返回
     * @param dates 一天中所有预约的时间
     * @return 空闲时间集合
     */
    public List<Date> timeToFreeTime(List<Date> dates) {
        List<Date> freeDates = new ArrayList<>();
        Date nowDate = new Date();



        return freeDates;
    }

    /**
     * 将 HH 类型整型变量转为时间段
     * @param tt HH类型整型变量
     * @return 13:00-14:00 格式时间段
     */
    public String hourIntToTime(int tt) {
        String time = null;

        int ttPlus = tt + 1;

        if(tt<10 && ttPlus<10) {
            time = "0" + String.valueOf(tt) + ":00-0" + String.valueOf(ttPlus) + ":00";
        } else if (ttPlus==10) {
            time = "0" + String.valueOf(tt) + ":00-" + String.valueOf(ttPlus) + ":00";
        } else if (tt >= 10) {
            time = String.valueOf(tt) + ":00-" + String.valueOf(ttPlus) + ":00";
        }

        return time;
    }

    /**
     * 从当前时间开始获取一天可预约的空闲时间
     * @param dates 预约时间集合
     * @param d 日期
     * @return 空闲时间集合
     * @throws ParseException
     */
    public List<String> getFreeTimeOfDate(List<Date> dates, String d) throws ParseException {

        TimeUtil timeUtil = new TimeUtil();
        Date nowDate = new Date();

        String startTime = timeUtil.getFormatDateOfHH(nowDate);

        /* 将预约的时间段加入集合中 */
        List<String> dateString = new ArrayList<>();
        for (Date dd : dates) {
            String hour = timeUtil.getFormatDateOfHH(dd);
            int tt = Integer.parseInt(hour);
            String tm = this.hourIntToTime(tt);
            dateString.add(tm);
        }

        /* 将从当前时间到 END_TIME 的所有时间段加入集合中 */
        List<String> freeDateString = new ArrayList<>();
        if (d.equals(timeUtil.getFormatDateOfmd(nowDate))) {
            int currentTime = Integer.parseInt(startTime);
            while(currentTime != END_TIME) {
                String currentTimeString = this.hourIntToTime(currentTime);
                freeDateString.add(currentTimeString);

                currentTime ++;
            }
        } else {
            int currentTime = START_TIME;
            while(currentTime != END_TIME) {
                String currentTimeString = this.hourIntToTime(currentTime);
                freeDateString.add(currentTimeString);

                currentTime ++;
            }
        }

        /* 将全部时间段中的预约时间段移除 */
        dateString.forEach(dateS -> {
            for(String fds : freeDateString) {
                if(fds.equals(dateS)) {
                    freeDateString.remove(fds);
                    break;
                }
            }
        });

        return freeDateString;

    }

    /**
     * 将当前的日期转换为整时日期（如 08:59 转换为 08:00）
     * @param nowDate
     * @return
     * @throws ParseException
     */
    public Date getDateByNowTime(Date nowDate) throws ParseException {
        TimeUtil timeUtil = new TimeUtil();

        String currentDate = timeUtil.getFormatDateOfyMd(nowDate);
        String nowDateH = timeUtil.getFormatDateOfHH(nowDate);
        int nowDateI = Integer.valueOf(nowDateH);
        String nd = currentDate + " " + this.hourIntToTime(nowDateI).substring(0, 5);
        Date nowTime = timeUtil.getFormatStringOfH(nd);

        return nowTime;
    }
}
