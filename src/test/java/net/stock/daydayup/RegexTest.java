/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup;

import net.stock.daydayup.constant.CommonData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.FormatStyle.*;

import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:dailm
 * @create at :2022/9/13 14:48
 */
public class RegexTest {
    public static void main(String[] args) throws ParseException {
        Pattern p = Pattern.compile(CommonData.dateRegex);
        Matcher m = p.matcher("披露重股票募集配套资金，2022年12月1日降低啊发法撒旦发，2023年11月15日啊沙发上。");
        DateFormat formatter3 = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        while (m.find()){
            String strDate = m.group();
            Date day = formatter3.parse(strDate);
            System.out.println(strDate);
            System.out.println(sdf2.format(day));
        }
    }
}
