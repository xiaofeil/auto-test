package com.xuanru.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by Liaoxf on 2016-10-12 20:32.
 */
public class JsonUtil {

    public static Object json2Map(String jsonstring) {

        Stack<Map> maps = new Stack<Map>(); //用来表示多层的json对象
        Stack<List> lists = new Stack<List>(); //用来表示多层的list对象
        Stack<Boolean> islist = new Stack<Boolean>();//判断是不是list
        Stack<String> keys = new Stack<String>(); //用来表示多层的key

        String keytmp = null;
        Object valuetmp = null;
        StringBuilder builder = new StringBuilder();
        char[] cs = jsonstring.toCharArray();

        for (int i = 0; i < cs.length; i++) {

            switch (cs[i]) {
                case '{': //如果是{map进栈
                    maps.push(new HashMap());
                    islist.push(false);
                    break;
                case ':'://如果是：表示这是一个属性建，key进栈
                    keys.push(builder.toString());
                    builder = new StringBuilder();
                    break;
                case '[':
                    islist.push(true);
                    lists.push(new ArrayList());
                    break;
                case ','://这是一个分割，因为可能是简单地string的键值对，也有可能是string=map
                    //的键值对，因此valuetmp 使用object类型；
                    //如果valuetmp是null 应该是第一次，如果value不是空有可能是string，那是上一个键值对，需要重新赋值
                    //还有可能是map对象，如果是map对象就不需要了

                    boolean listis = islist.peek();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();
                    builder = new StringBuilder();
                    if (!listis) {
                        keytmp = keys.pop();
                        maps.peek().put(keytmp, valuetmp);
                    } else
                        lists.peek().add(valuetmp);

                    break;
                case ']':
                    islist.pop();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();
                    builder = new StringBuilder();
                    lists.peek().add(valuetmp);
                    valuetmp = lists.pop();
                    break;
                case '}':
                    islist.pop();
                    //这里做的和，做的差不多，只是需要把valuetmp=maps.pop();把map弹出栈
                    keytmp = keys.pop();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();

                    builder = new StringBuilder();
                    maps.peek().put(keytmp, valuetmp);
                    valuetmp = maps.pop();
                    break;
                default:
                    builder.append(cs[i]);
                    break;
            }

        }
        return valuetmp;
    }

    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    public static void main(String[] args) {
//        String json = "{\"respContent\":\"{\\\"data\\\":{\\\"firstInvest\\\":[{\\\"fundCode\\\":\\\"400009\\\",\\\"fundName\\\":\\\"东方稳健回报债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":5.09},{\\\"fundCode\\\":\\\"400013\\\",\\\"fundName\\\":\\\"东方保本混合型基金\\\",\\\"invstTypeMark\\\":\\\"保本型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":6.42}],\\\"steadyIncome\\\":[{\\\"fundCode\\\":\\\"400030\\\",\\\"fundName\\\":\\\"东方添益债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":10.71},{\\\"fundCode\\\":\\\"070005\\\",\\\"fundName\\\":\\\"嘉实债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":1000,\\\"oneYear\\\":6.46}],\\\"highIncome\\\":[{\\\"fundCode\\\":\\\"400001\\\",\\\"fundName\\\":\\\"东方龙混合\\\",\\\"invstTypeMark\\\":\\\"混合型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":23.61},{\\\"fundCode\\\":\\\"080005\\\",\\\"fundName\\\":\\\"长盛量化红利混合\\\",\\\"invstTypeMark\\\":\\\"混合型\\\",\\\"minAmount\\\":1000,\\\"oneYear\\\":24.01}]},\\\"error\\\":[],\\\"success\\\":true}\",\"statusCode\":200,\"success\":true}";
        String json = "{\"data\":{\"firstInvest\":[{\"fundCode\":\"400009\",\"fundName\":\"东方稳健回报债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":100,\"oneYear\":5.09},{\"fundCode\":\"400013\",\"fundName\":\"东方保本混合型基金\",\"invstTypeMark\":\"保本型\",\"minAmount\":100,\"oneYear\":6.42}],\"steadyIncome\":[{\"fundCode\":\"400030\",\"fundName\":\"东方添益债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":100,\"oneYear\":10.71},{\"fundCode\":\"070005\",\"fundName\":\"嘉实债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":1000,\"oneYear\":6.46}],\"highIncome\":[{\"fundCode\":\"400001\",\"fundName\":\"东方龙混合\",\"invstTypeMark\":\"混合型\",\"minAmount\":100,\"oneYear\":23.61},{\"fundCode\":\"080005\",\"fundName\":\"长盛量化红利混合\",\"invstTypeMark\":\"混合型\",\"minAmount\":1000,\"oneYear\":24.01}]},\"error\":[],\"success\":true}";
        System.out.println(json2Map(json));
        Map map = parseJSON2Map(json);
        Map temp = null;

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            temp = parseJSON2Map((String) map.get(key));
            map.putAll(temp);
            System.out.println(key + "----" + map.get(key));
        }
    }
}
