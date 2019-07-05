package com.self.study.study.all;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 生产者，持续不断的往kafka里写入一下结构的PV/UV日志数据
 * Uid,Timestamp,City Name,Browser,Duration Time,Url
 */
public class PvUvProducer {
    private static final String TOPIC_ID = "rt_dn_pvuv";

    public static void main(String[] args) {
        while (true) {
            // 随机生成消息
            String msg = String.format("%s,%s,%s,%s,%s,%s",
                    getUidByRandom(),
                    System.currentTimeMillis(),
                    getCityNameByRandom(),
                    getBrowserByRandom(),
                    getDurationTimeByRandom(),
                    getUrlByRandom());

            // 向kafka发送msg
            KafkaCommons.sendMsgToKafka(TOPIC_ID, msg);

            System.out.println("Kafka Send: " + msg);

            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println("Error Happens: " + e.getMessage());
            }
        }
    }

    /**
     * 随机获取一个用户编号
     *
     * @return
     */
    private static int getUidByRandom() {
        // 生产随机数
        Random r1 = new Random();

        // 生成一个随机的uid
        int uid = r1.nextInt(10000);

        return uid;
    }

    /**
     * 随机获取一个城市名称
     *
     * @return
     */
    private static String getCityNameByRandom() {
        int size = 10;

        // 生成随机数
        Random r1 = new Random();
        int index = r1.nextInt(size);

        return CITY_NAMES.get(index);
    }

    /**
     * 随机获取一个浏览器名称
     *
     * @return
     */
    private static String getBrowserByRandom() {
        int size = BROWSER_NAMES.size();

        // 生成随机数
        Random r1 = new Random();
        int index = r1.nextInt(size);

        return BROWSER_NAMES.get(index);
    }

    /**
     * 随机获取一个页面访问时间
     *
     * @return
     */
    private static int getDurationTimeByRandom() {
        // 生成随机数
        Random r1 = new Random();
        int durationTime = r1.nextInt(100);

        return durationTime;
    }

    /**
     * 随机获取一个URL地址
     *
     * @return
     */
    private static String getUrlByRandom() {
        int size = URL_NAMES.size();

        // 生成随机数
        Random r1 = new Random();
        // 生成域名索引
        int urlIndex = r1.nextInt(size);
        // 生成页面id
        int postId = r1.nextInt(200);

        return String.format("%s/post=%s", URL_NAMES.get(urlIndex), postId);
    }

    private static List<String> CITY_NAMES = Arrays.asList("北京", "上海", "天津", "重庆", "清远", "茂名", "汕头", "深圳", "惠州", "云浮", "梅州", "江门", "中山", "韶关", "河源", "汕尾", "阳江", "揭阳", "肇庆", "潮州", "湛江", "东莞", "珠海", "佛山", "广州", "沈阳", "盘锦", "阜新", "营口", "葫芦岛", "锦州", "丹东", "辽阳", "抚顺", "朝阳", "本溪", "铁岭", "鞍山", "大连", "南京", "南通", "苏州", "镇江", "无锡", "泰州", "常州", "连云港", "淮安", "徐州", "盐城", "宿迁", "扬州", "武汉", "宜昌", "荆州", "江汉", "十堰", "恩施", "黄冈", "孝感", "荆门", "咸宁", "黄石", "随州", "鄂州", "襄阳", "泸州", "攀枝花", "遂宁", "内江", "自贡", "宜宾", "绵阳", "南充", "广安", "达州", "巴中", "德阳", "阿坝藏族羌族自治州", "广元", "乐山", "甘孜藏族自治州", "成都", "眉山", "雅安", "资阳", "铜川", "商洛", "安康", "咸阳", "汉中", "宝鸡", "延安", "榆林", "渭南", "西安", "衡水", "邢台", "廊坊", "沧州", "承德", "唐山", "保定", "张家口", "邯郸", "石家庄", "秦皇岛", "阳泉", "朔州", "大同", "长治", "晋中", "临汾", "晋城", "运城", "吕梁", "忻州", "太原", "焦作、济源", "濮阳", "鹤壁", "漯河", "周口", "商丘", "郑州", "安阳", "新乡", "洛阳", "开封", "平顶山", "许昌", "南阳", "信阳", "驻马店", "三门峡", "通化", "白城", "延边朝鲜族自治州", "四平", "长春", "吉林", "白山", "辽源", "松原", "牡丹江", "佳木斯", "哈尔滨", "七台河", "齐齐哈尔", "大兴安岭", "伊春", "鸡西", "绥化", "鹤岗", "双鸭山", "黑河", "大庆", "兴安盟", "阿拉善盟", "呼伦贝尔盟", "呼和浩特", "包头", "乌兰察布盟", "乌海", "赤峰", "通辽", "巴彦淖尔盟", "鄂尔多斯", "锡林郭勒盟", "聊城", "日照", "莱芜", "威海", "枣庄", "济南", "菏泽", "烟台", "德州", "滨州", "淄博", "东营", "青岛", "临沂", "泰安", "济宁", "潍坊", "阜阳、亳州", "黄山", "巢湖", "安庆", "池州", "宿州", "淮南", "马鞍山", "蚌埠", "淮北", "芜湖", "铜陵", "宣城", "滁州", "六安", "合肥", "舟山", "杭州", "衢州", "宁波", "绍兴", "湖州", "嘉兴", "丽水", "金华", "台州", "温州", "泉州", "莆田", "龙岩", "漳州", "南平", "三明", "福州", "厦门", "宁德", "郴州", "衡阳", "永州", "益阳", "怀化", "常德", "邵阳", "娄底", "湘潭", "张家界", "长沙", "湘西州", "岳阳", "株洲", "柳州、来宾", "桂林", "防城港", "南宁、崇左", "百色", "钦州", "梧州、贺州", "玉林、贵港", "河池", "北海", "新余", "南昌", "萍乡", "景德镇", "赣州", "鹰潭", "吉安", "宜春", "抚州", "上饶", "九江", "遵义", "贵阳", "都匀", "安顺", "铜仁", "凯里", "六盘水", "毕节", "兴义", "丽江", "普洱", "玉溪", "楚雄", "保山", "文山", "怒江", "红河哈尼族彝族自治州", "迪庆", "曲靖", "西双版纳", "昆明", "德宏", "大理", "临沧", "昭通", "山南", "林芝", "拉萨", "日喀则", "阿里", "昌都", "那曲", "甘南", "临夏", "兰州", "定西", "平凉", "白银", "庆阳", "金昌、武威", "张掖", "酒泉、嘉峪关", "天水", "陇南", "中卫", "固原", "银川", "吴忠", "石嘴山", "海西州", "玉树", "格尔木", "西宁", "海北州", "黄南", "海东", "果洛", "海南州", "库尔勒、巴州", "阿克苏", "昌吉", "吐鲁番", "克州", "奎屯", "石河子", "克拉玛依", "博乐", "乌鲁木齐", "和田", "阿勒泰", "喀什", "哈密", "伊犁", "塔城", "海口", "三亚", "儋州");

    private static List<String> BROWSER_NAMES = Arrays.asList("Chrome", "IE8", "IE9", "IE10", "Safari", "FireFox", "Opera", "Sogou", "360");

    private static List<String> URL_NAMES = Arrays.asList("http://www.baidu.com", "http://www.google.com", "http://www.weibo.com", "http://www.mi.com", "http://www.360.com", "http://www.qq.com");

}
