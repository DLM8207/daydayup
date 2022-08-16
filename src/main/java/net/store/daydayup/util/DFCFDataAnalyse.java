package net.store.daydayup.util;

/**
 * 东财数据解析
 */
public class DFCFDataAnalyse {

    public static String getStockData(String respData,String pre){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(pre);
            respData = respData.substring(preIndex+pre.length()+1,respData.length()-2);
        }
        return respData;
    }
}
