package cn.huanji.utils;

import cn.hutool.core.date.DateUtil;

import java.util.*;

/**
 * Created by Hyanzhao on 2019/6/28.
 */
public class DateUtils {


    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY_MM = "yyyyMM";

    /**
     * 获取两个时间点之间的月份时间
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getMonthsBetween(Date start, Date end) {
        List<Date> dates = new LinkedList<>();
        long betweenMs = DateUtil.betweenMonth(start, end, false);
        for (int i = 0; i <= betweenMs; i++) {
            dates.add(DateUtil.offsetMonth(start, i));
        }
        return dates;
    }

    /**
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return
     */
    public static List<Date> getMonthsBetween(String start, String end) {
        Date startDate = DateUtil.parse(start, DateUtils.DATE_FORMAT_YYYY_MM_DD);
        Date endDate = DateUtil.parse(end, DateUtils.DATE_FORMAT_YYYY_MM_DD);
        return DateUtils.getMonthsBetween(startDate, endDate);
    }

    /**
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return yyyy-MM
     */
    public static List<String> getStrMonthsBetween(String start, String end) {
        List<String> dates = new LinkedList<>();
        List<Date> date = DateUtils.getMonthsBetween(start, end);
        for (int i = 0; i < date.size(); i++) {
            dates.add(DateUtil.format(date.get(i), DateUtils.DATE_FORMAT_YYYY_MM));
        }
        return dates;
    }

    /**
     * 补充序列空格，例如
     * {name: "2019-01", value: 19},
     * {name: "2019-03", value: 21},
     * 转为
     * {name: "2019-01", value: 19},
     * {name: "2019-02", value: 0},
     * {name: "2019-03", value: 21},
     *
     * @param start
     * @param end
     * @param values
     * @param defaultValue
     * @return
     */
    public static List<Map<String, Object>> getMapBetween(String start, String end,
                                                          String keyForTime, String keyForVal,
                                                          List<Map<String, Object>> values, Object defaultValue) {
        List<Map<String, Object>> mapList = new LinkedList<>();
        if (values == null) {
            return mapList;
        }
        List<String> dates = DateUtils.getStrMonthsBetween(start, end);
        //初始化返回值
        for (int i = 0; i < dates.size(); i++) {
            Map<String, Object> value = new HashMap<>();
            value.put(keyForTime, dates.get(i));
            value.put(keyForVal, defaultValue);
            mapList.add(value);
        }
        //赋值
        for (int i = 0; i < mapList.size(); i++) {
            String key = (String) mapList.get(i).get(keyForTime);
            for (int j = 0; j < values.size(); j++) {
                Map<String, Object> value = values.get(j);
                Integer temp = (Integer) value.get(keyForTime);
                if (key.equals(String.valueOf(temp))) {
                    mapList.get(i).put(keyForVal, value.get(keyForVal));
                }
            }
        }

        return mapList;
    }


    public static void main(String[] args) {
        List<String> dates = DateUtils.getStrMonthsBetween("2019-01-01", "2019-12-31");
        for (int i = 0; i < dates.size(); i++) {
            System.out.println(dates.get(i));
        }
    }
}
