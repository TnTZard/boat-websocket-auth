package xx.mrxie.websocket.auth.constant;

import java.util.HashMap;
import java.util.Map;

public interface Constants {

    String ONLINE = "online";
    String TOPIC = "topic";
    Long BOATID = 471L;



    //无人船控制和状态数据
    String uri = "ws://open.zwiivc.com/usvapi/control/471";


    //无人艇推送的数据
    String dataUrl = "ws://open.zwiivc.com/usvapi/read/boat/471";


    //监测数据，某个点监测完成后，主控会发送数据到基站
    String topic1 = "/monitor/record";
    /**
     * 无人船的当前位置，发送频率由传感器设备的频率决定，一般不低于5Hz，参数格式为： | WGS84纬度（8B）|
     * WGS84经度（8B）|
     * WGS84纬度：双精度浮点数
     * WGS84纬度：双精度浮点数
     */
    String topic2 = "/gps";
    /**
     * 无人船的艏向，发送频率同传感器设备的频率，一般不低于5Hz，参数格式为： | 艏向（4B）|
     * 艏向：航向：单精度浮点数，单位为度，正北为0，顺时针为正方向
     */
    String topic3 = "/hdt";
    /**
     * 无人船返回当前的航行任务，参数格式为： | 任务数据（不定长，最少20B）|
     * 任务数据：与/wp/set的内容一致。
     */
    String topic4 = "/wp/info";
    /**
     * 无人船上下线
     */
    String topic5 = "/boat/online/notify";



    // eiot.meeb.sz.gov.cn(58.250.156.11)
    String socketHost = "58.250.156.11";
    Integer port = 9100;

    String testMN = "BW039";


    // 溶解氧
    String DO = "w01009"; //65558
    // 电导率
    String spCond = "w01014"; // 65548
    // 氨氮
    String NH3_N = "w21003"; //65604
    // 透明度 （通过浊度转化）  SD = 1320.9/turb + 10.02
    String SD = "w89004";
    // 氧化还原电位（ORP）   //65544
    String ORP = "w89005";




    // 溶解氧
    String w01009 = "65558";
    // 电导率
    String w01014 = "65548";
    // 氨氮
    String w21003 = "65604";
    // （透明度）浊度转化  65702
    String w89004 = "65072";
    // 氧化还原电位（ORP）   //65544
    String w89005 = "65544";


    Map<String, String> dataMap = new HashMap<String, String>() {
        {
            put(DO, w01009);
            put(spCond, w01014);
            put(NH3_N, w21003);
            put(SD, w89004);
            put(ORP, w89005);
        }
    };



}
