package net.stock.daydayup.constant;

/**
 * 公共数据
 */
public interface CommonData {
    /**
     * f1:交易状态
     * f2:现价
     * f3:涨跌幅
     * f4:涨跌额
     * f5：交易量
     * f6:交易额
     * f7:振幅
     * f8:换手率
     * f9:市盈率（动）
     * f10:量比
     * f11:
     * f12:Code
     * f13:
     * f14:Name
     * f15:最高
     * f16:最低
     * f17:今开
     * f18:昨收
     * f20:总市值
     * f21:流通市值
     * f22:
     * f23:市净率
     * f24:
     * f25:市盈率（TTM）
     * f62:
     */
    //查询当天所有股票信息
    String listStockUrl="http://17.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124049501677394074295_1655813740688&pn=${pageNum}&pz=${pageSize}&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&wbp2u=|0|0|0|web&fid=f3&fs=m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23,m:0+t:81+s:2048&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1655813740755";

    static final String stockPre = "jQuery1124049501677394074295_1655813740688";
    //涨停次数
    String zttjUrl = "http://push2ex.eastmoney.com/getTopicZTPool?cb=callbackdata7452717&ut=7eea3edcaed734bea9cbfc24409ed989&dpt=wz.ztzt&Pageindex=${pageIndex}&pagesize=${pageSize}&sort=zttj%3Adesc&date=${date}&_=1655817396514";

    final static String zttjPre = "callbackdata7452717";
    //查询所有概念
    static String listNotion = "https://15.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112404994384427702476_1660003587460&pn=${pageNum}&pz=${pageSize}&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&wbp2u=|0|0|0|web&fid=f3&fs=m:90+t:3+f:!50&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f208,f209,f222&_=1660003587471";

    final static String notionPre = "jQuery112404994384427702476_1660003587460";

    //查询所有行业
    static String listIndustry = "http://59.push2.eastmoney.com/api/qt/clist/get?cb=jQuery11240415130758141401_1660003854627&pn=${pageNum}&pz=${pageSize}&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&wbp2u=|0|0|0|web&fid=f3&fs=m:90+t:2+f:!50&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f208,f209,f222&_=1660003854633";

    final static String industryPre = "jQuery11240415130758141401_1660003854627";

    //个股题材 深圳:SZ,上海：SH code=SZ002222
    static String stockTopic = "https://emweb.securities.eastmoney.com/PC_HSF10/CoreConception/PageAjax?code=${code}";

    //板块
    static String listArea = "https://19.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112409765183226443759_1660729722000&pn=${pageNum}&pz=${pageSize}&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&wbp2u=|0|0|0|web&fid=f3&fs=m:90+t:1+f:!50&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f208,f209,f222&_=1660729722006";

    final static String areaPre = "jQuery112409765183226443759_1660729722000";

    //Sina股票接口  order=D(降序)/A(升序) code=cn_600104&start=20190716&end=20191113
    //T+1数据，当天数据需要从其他API获取
    //日期、开盘价、收盘价、涨跌额、涨跌幅、最低价、最高价、总手、总成交额、换手率
    final static String sohuStockDataApi="https://q.stock.sohu.com/hisHq?code=cn_${code}&start=${startDate}&end=${endDate}&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

    //http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=[市场][股票代码]&scale=[周期]&ma=no&datalen=[长度]
    //返回结果：获取5、10、30、60分钟JSON数据；day日期、open开盘价、high最高价、low最低价、close收盘价、volume成交量；向前复权的数据。
    //注意，最多只能获取最近的1023个数据节点。
    //http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sh603031&scale=30&ma=no&datalen=1023
    //http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sz000002&scale=30&ma=20&datalen=1023（增加20周期均价）

    //日期正则表达式
    static final String dateRegex = "^\\d{4}年\\d{1,2}月\\d{1,2}日";
}
