package com.angelblog.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.angelblog.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author Alcedo
 *
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

   // public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static final String IP_URL = "http://ip.taobao.com/outGetIpInfo";
 //   http://ip.taobao.com/outGetIpInfo?ip=218.205.192.48&accessKey=alibaba-inc
//    {"data":{"area":"","country":"中国","isp_id":"100025","queryIp":"218.205.192.48",
//            "city":"北京","ip":"218.205.192.48","isp":"移动",
//            "county":"","region_id":"110000","area_id":"",
//            "county_id":null,"region":"北京","country_id":"CN","city_id":"110100"},
//        "msg":"query success","code":0}

    public static String getRealAddressByIP(String ip)
    {
        String address = "";
        try
        {
            address = HttpUtils.sendPost(IP_URL, "ip=" + ip+"&accessKey=alibaba-inc");
            JSONObject json = JSONObject.parseObject(address);
            JSONObject object = json.getObject("data", JSONObject.class);
            String region = object.getString("region");
            String city = object.getString("city");
            address = region + " " + city;
        }
        catch (Exception e)
        {
            log.error("根据IP获取所在位置----------错误消息：" + e.getMessage());
        }
        return address;
    }
}
