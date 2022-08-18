package net.stock.daydayup.constant;

/**
 * 公共数据
 */
public interface CommonData {
    //查询当天所有股票信息
    String listStockUrl="http://17.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124049501677394074295_1655813740688&pn=${pageNum}&pz=${pageSize}&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&wbp2u=|0|0|0|web&fid=f3&fs=m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23,m:0+t:81+s:2048&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1655813740755";
    //涨停次数
    String zttjUrl = "http://push2ex.eastmoney.com/getTopicZTPool?cb=callbackdata7452717&ut=7eea3edcaed734bea9cbfc24409ed989&dpt=wz.ztzt&Pageindex=0&pagesize=170&sort=zttj%3Adesc&date=20220621&_=1655817396514";

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
}
