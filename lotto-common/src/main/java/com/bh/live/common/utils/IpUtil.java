package com.bh.live.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;
/**
 *@author WuLong
 *@date 2019/7/26 16:47
 *@description IP地址获取
 */
public class IpUtil {
    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        try {
            if (request == null) {
                return "";
            }
            String ip = request.getHeader("x-forwarded-for");
            if (CommonUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (CommonUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (CommonUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            final String[] arr = ip.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ip = str;
                    break;
                }
            }
            if (("0:0:0:0:0:0:0:1").equals(ip)) {
                return "127.0.0.1";
            }
            /*if (isInnerIP(ip)) {
                return getNetworkIp();
            }*/
            return ip;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取Ip地址
     *
     * @param request 请求对象
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 私有IP：A类  10.0.0.0-10.255.255.255
     * B类  172.16.0.0-172.31.255.255
     * C类  192.168.0.0-192.168.255.255
     * 当然，还有127这个网段是环回地址
     */
    public static boolean isInnerIP(String ipAddress) {
        long ipNum = getIpNum(ipAddress);
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        return isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals("127.0.0.1");
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getLocalAddress() {
        StringBuilder IFCONFIG = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        IFCONFIG.append(inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return IFCONFIG.toString();
    }

    public static long getLongIp(HttpServletRequest request) {
        try {
            return ipToLong(getIpAddress(request));
        } catch (Exception e) {
            return 0L;
        }
    }

    //将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIp) {
        try {
            if (CommonUtil.isEmpty(strIp)) {
                return 0;
            }
            long[] ip = new long[4];
            //先找到IP地址字符串中.的位置
            int position1 = strIp.indexOf(".");
            int position2 = strIp.indexOf(".", position1 + 1);
            int position3 = strIp.indexOf(".", position2 + 1);
            //将每个.之间的字符串转换成整型
            ip[0] = Long.parseLong(strIp.substring(0, position1));
            ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIp.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //将十进制整数形式转换成127.0.0.1形式的ip地址
    public static String longToIP(Long longIp) {
        try {
            if (null == longIp) {
                return "";
            }
            if (longIp.longValue() < 1) {
                return "127.0.0.1";
            }
            StringBuffer sb = new StringBuffer("");
            //直接右移24位
            sb.append(String.valueOf((longIp >>> 24)));
            sb.append(".");
            //将高8位置0，然后右移16位
            sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
            sb.append(".");
            //将高16位置0，然后右移8位
            sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
            sb.append(".");
            //将高24位置0
            sb.append(String.valueOf((longIp & 0x000000FF)));
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    public static String getRemoteRealIp(HttpServletRequest request) {
        String realIp = "";
        if (null == request) {
            return realIp;
        }
        realIp = (String) request.getHeader("X-Real-IP");
        realIp = !CommonUtil.isEmpty(realIp) ? realIp : request.getRemoteAddr();
        return realIp;
    }


    // IP的正则
    private static Pattern pattern = Pattern
            .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");
    public static final String DEFAULT_ALLOW_ALL_FLAG = "*";// 允许所有ip标志位
    public static final String DEFAULT_DENY_ALL_FLAG = "0"; // 禁止所有ip标志位

    /**
     * getAvaliIpList:(根据IP白名单设置获取可用的IP列表).
     *
     * @param allowIp ip列表
     * @return
     */
    private static Set<String> getAvaliIpList(String allowIp) {
        String[] splitRex = allowIp.split(";");// 拆分出白名单正则
        Set<String> ipList = new HashSet<String>(splitRex.length);
        for (String allow : splitRex) {
            if (allow.contains("*")) {// 处理通配符 *
                String[] ips = allow.split("\\.");
                String[] from = new String[]{"0", "0", "0", "0"};
                String[] end = new String[]{"255", "255", "255", "255"};
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++) {
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }
                }

                StringBuilder fromIP = new StringBuilder();
                StringBuilder endIP = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]", s.split(";")[0]) + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                ipList.add(allow);
            } else {// 处理单个 ip 或者 范围
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }

        }

        return ipList;
    }

    /**
     * 对单个IP节点进行范围限定
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        int len = arg.length();
        if (len == 1) {
            com.add("0;255");
        } else if (len == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
            String s2 = complete(arg, 2);
            if (s2 != null) {
                com.add(s2);
            }
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255) {
            return null;
        }
        if (Integer.valueOf(end) > 255) {
            end = "255";
        }
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     *
     * @param ip
     * @return
     */
    public static boolean validate(String ip) {
        String[] temp = ip.split("-");
        for (String s : temp) {
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * isPermited:(根据IP,及可用Ip列表来判断ip是否包含在白名单之中).
     *
     * @param ip
     * @param ipList
     * @return
     * @date 2017-4-17 下午03:01:03
     */
    private static boolean isPermited(String ip, Set<String> ipList) {
        if (ipList.isEmpty() || ipList.contains(ip)) {
            return true;
        }
        for (String allow : ipList) {
            if (allow.indexOf("-") > -1) {// 处理 类似 192.168.0.0-192.168.2.1
                ip = ip.trim();
                int idx = allow.indexOf('-');
                String beginIP = allow.substring(0, idx);
                String endIP = allow.substring(idx + 1);
                if (!(ipToLong(beginIP) <= ipToLong(ip) && ipToLong(ip) <= ipToLong(endIP))) {
                    return false;
                }
                return true;
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                int splitIndex = allow.indexOf("/");
                // 取出子网段
                String ipSegment = allow.substring(0, splitIndex); // 192.168.3.0
                // 子网数
                String netmask = allow.substring(splitIndex + 1);// 24
                // ip 转二进制
                long ipLong = ipToLong(ip);
                //子网二进制
                long maskLong = (2L << 32 - 1) - (2L << Integer.valueOf(32 - Integer.valueOf(netmask)) - 1);
                // ip与和子网相与 得到 网络地址
                String calcSegment = longToIP(ipLong & maskLong);
                // 如果计算得出网络地址和库中网络地址相同 则合法
                if (ipSegment.equals(calcSegment)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * isPermited:(根据IP地址，及IP白名单设置规则判断IP是否包含在白名单).
     *
     * @param ip
     * @param ipWhiteConfig
     * @return
     * @date 2017-4-17 下午03:01:37
     */
    public static boolean isPermited(String ip, String ipWhiteConfig) {
        if (null == ip || "".equals(ip)) {
            return false;
        }
        //ip格式不对
        if (!pattern.matcher(ip).matches()) {
            return false;
        }
        //允许所有ip
        if (DEFAULT_ALLOW_ALL_FLAG.equals(ipWhiteConfig)) {
            return true;
        }
        //禁止所有ip
        if (DEFAULT_DENY_ALL_FLAG.equals(ipWhiteConfig)) {
            return false;
        }
        Set<String> ipList = getAvaliIpList(ipWhiteConfig);
        return isPermited(ip, ipList);
    }


    /**
     * 通过ip获取地理位置
     *
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    public static String getAddresses(String content, String encoding) {
        // 这里调用淘宝API
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = getResult(urlStr, content, encoding);
        if (returnStr != null) {
            // 处理返回的省市区信息
            returnStr = decodeUnicode(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";//无效IP，局域网测试
            }
            return returnStr;
        }
        return null;
    }

    /**
     * @param urlStr   请求的地址
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private static String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            logger.warn("获取ip失败：" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    /**
     * unicode 转换成 中文
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }


    /**
     * 拼接返回地址为 国家省份市
     *
     * @param ip
     * @return
     */
    public static String getSplicingAddress(String ip) {
        String addresses = IpUtil.getAddresses("ip=" + ip, "utf-8");
        if (null == addresses || "0".equals(addresses)) {
            return "";
        }
        JSONObject json = JSONObject.parseObject(addresses);
        String country = json.getJSONObject("data").getString("country");
        String region = json.getJSONObject("data").getString("region");
        String city = json.getJSONObject("data").getString("city");
        //国省市
        String address = country;
        address += region + "省";
        address += city + "市";
        if(CommonUtil.isEmpty(city) || "内网IP".equalsIgnoreCase(city)){
            address = city;
        }
        return address;
    }


    /**
     * 获取本地IP地址
     *
     * @throws SocketException
     */
    public static String getLocalIP(){
        try {
            if (isWindowsOS()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                return getLinuxLocalIp();
            }
        } catch (Exception e) {
            throw new ResultJsonException(CodeMsg.E_50007);
        }

    }
    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        return ip;
    }


    // 测试
    public static void main(String[] args) {

        // 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
        String ip = "219.136.134.157";
        String address = "";
        String splicingAddress = IpUtil.getSplicingAddress(ip);
        // address = IpUtil.getAddresses("ip="+ip, "utf-8");
        //   boolean innerIP = IpUtil.isInnerIP(ip);
        /*        long l = IpUtil.ipToLong(ip);*/
        // boolean validate = IpUtil.validate(ip);
        System.out.println(splicingAddress);
        // 输出结果为：广东省,广州市,越秀区
    }
}
