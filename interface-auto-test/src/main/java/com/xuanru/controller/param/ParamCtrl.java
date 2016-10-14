package com.xuanru.controller.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanru.common.Constants;
import com.xuanru.dto.HttpNetResponse;
import com.xuanru.dto.resp.param.ExecuteResultDto;
import com.xuanru.dto.resp.param.ParamFileDto;
import com.xuanru.enums.RequestMethodEnum;
import com.xuanru.service.impl.ExcelParseService;
import com.xuanru.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@Slf4j
public class ParamCtrl {

    @Resource
    private ExcelParseService excelParseService;

    /**
     * 展示所有脚本文件
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:32
     */
    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public String login(ModelMap model, HttpServletRequest request) {
        File file = new File(Constants.EXCLE_UPLOAD_PATH);
        List<ParamFileDto> fileDtoList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            ParamFileDto paramFileDto = null;
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().indexOf("_result") != -1)
                    continue;
                String postfix = ParseExcelUtil.getPostfix(files[i].getName());
                if (Constants.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)
                        || Constants.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    paramFileDto = new ParamFileDto();
                    paramFileDto.setFileName(files[i].getName());
                    paramFileDto.setDownloadPath(Constants.EXCLE_UPLOAD_PATH + files[i].getName());
                    fileDtoList.add(paramFileDto);
                }
            }
        }

        Collections.sort(fileDtoList, new Comparator<ParamFileDto>() {
            @Override
            public int compare(ParamFileDto o1, ParamFileDto o2) {
                String time1 = o1.getFileName().substring(o1.getFileName().indexOf("_") + 1, o1.getFileName().lastIndexOf(Constants.POINT));
                String time2 = o2.getFileName().substring(o2.getFileName().indexOf("_") + 1, o2.getFileName().lastIndexOf(Constants.POINT));
                return time2.compareTo(time1);
            }
        });
        model.put("fileDtoList", fileDtoList);
        return "param/listall";
    }

    /**
     * 上传脚本
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:32
     */
    @RequestMapping(value = "/configFileUpload", method = RequestMethod.POST)
    public String configFileUpload(@RequestParam(value = "uploadfile") CommonsMultipartFile file,
                                   ModelMap model, HttpSession session) throws IOException {

        try {
            if (file != null && !file.isEmpty()) {
                String uploadRet = UploadUtil.uploadExcel(file);
                if (uploadRet != null) {
                    log.info("upload excel success,filename=[{}]", uploadRet);
                } else {
                    log.info("upload excel fail");
                }
            } else {
                log.info("MultipartFile is null");
                model.put("errMsg", "MultipartFile is null");
            }
        } catch (Exception e) {
            log.error("", e);
            model.put("errMsg", e.getMessage());
        }

        return "redirect:listall";
    }

    /**
     * 查看执行结果
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:32
     */
    @RequestMapping(value = "/showExecuteResult", method = RequestMethod.POST)
    public String showExecuteResult(String fileName, ModelMap model,
                                    HttpServletRequest request) {
        log.info("show {} execute result ", fileName);
        File file = new File(Constants.EXCLE_UPLOAD_PATH + fileName);
        List<ExecuteResultDto> executeResultDtos = new ArrayList<>();
        if (file.exists()) {
            String resultFileName = fileName.substring(0, fileName.indexOf(".")) + "_result"
                    + fileName.substring(fileName.indexOf("."));
            File resultFile = new File(Constants.EXCLE_UPLOAD_PATH + resultFileName);
            if (resultFile.exists()) {
                // 解析excel文件
                executeResultDtos = excelParseService.pareseResultFile(resultFileName);
            } else {
                model.put("errMsg", "\\u811a\\u672c\\u8fd8\\u672a\\u6267\\u884c\\uff0c\\u6ca1\\u6709\\u7ed3\\u679c\\u6587\\u4ef6"); // 脚本还未执行，没有结果文件
                return "redirect:listall";
            }
        } else {
            model.put("errMsg", fileName + "\\u6587\\u4ef6\\u4e0d\\u5b58\\u5728");
            return "redirect:listall";
        }
        model.put("executeResultDtos", executeResultDtos);
        return "param/param_edit";
    }

    /**
     * 删除文件
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:32
     */
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public String deleteFile(String fileName, ModelMap model,
                             HttpServletRequest request) {
        log.info("deleteFile {}  ", fileName);
        File file = new File(Constants.EXCLE_UPLOAD_PATH + fileName);
        String resultFileName = fileName.substring(0, fileName.indexOf(".")) + "_result"
                + fileName.substring(fileName.indexOf("."));
        File resultFile = new File(Constants.EXCLE_UPLOAD_PATH + resultFileName);
        if (resultFile.exists()) {
            resultFile.delete();
        }
        if (file.exists()) {
            file.delete();
        }

        model.put("errMsg", fileName + "\\u5220\\u9664\\u6210\\u529f");
        return "redirect:listall";
    }

    /**
     * 执行脚本
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:33
     */
    @RequestMapping(value = "/executeScript", method = RequestMethod.POST)
    public String executeScript(String fileName, ModelMap model,
                                HttpServletRequest request) {
        log.info("executeScript: {} ", fileName);
        File file = new File(Constants.EXCLE_UPLOAD_PATH + fileName);
        List<ExecuteResultDto> scriptDtos = new ArrayList<>();
        List<ExecuteResultDto> resultDtos = new ArrayList<>();
        try {
            if (file.exists()) {
                // 解析excel文件
                scriptDtos = excelParseService.pareseResultFile(fileName);
                String resultFileName = fileName.substring(0, fileName.indexOf(".")) + "_result"
                        + fileName.substring(fileName.indexOf("."));
                HttpNetResponse netResponse = null;
                String expectResult = null;
                ExecuteResultDto resultDto = null;
                for (ExecuteResultDto scriptDto : scriptDtos) {
                    resultDto = new ExecuteResultDto();
                    ObjectUtil.copyProperties(scriptDto, resultDto);
                    if (RequestMethodEnum.GET.getDesc().equals(scriptDto.getRequest_method())) {
                        netResponse = HttpClientUtil.sendHttpClientGet(scriptDto.getUrl() + "?" + scriptDto.getParams(), Constants.CHARSET_UTF_8);
                    } else if (RequestMethodEnum.POST.getDesc().equals(scriptDto.getRequest_method())) {
                        netResponse = HttpClientUtil.sendHttpClientPost(scriptDto.getUrl(), transferParams(scriptDto.getParams()), Constants.CHARSET_UTF_8);
                    } else if (RequestMethodEnum.PUT.getDesc().equals(scriptDto.getRequest_method())) {
                        netResponse = HttpClientUtil.sendHttpClientPut(scriptDto.getUrl(), transferParams(scriptDto.getParams()), Constants.CHARSET_UTF_8);
                    }

                    if (netResponse != null) {
                        log.info("=========result:" + JSONObject.toJSONString(netResponse));
                        resultDto.setStatus_code(new Integer(netResponse.getStatusCode()).toString());
                        resultDto.setActual_result(netResponse.getRespContent());
                        if (netResponse.isSuccess()) {
                            resultDto.setPass_flag(Constants.SUCCESS);
                            expectResult = scriptDto.getExpect_result();
                            Map<String, String> expectMap = null; // 预期结果json键值对
                            if (StringUtil.isNotBlank(expectResult)) {
                                if (!expectResult.contains("=")) { // 预期结果非json键值对，直接对比返回值是否相等
                                    if (!expectResult.trim().equals(netResponse.getRespContent().trim())) {
                                        resultDto.setPass_flag(Constants.FAIL);
                                    }
                                } else {
                                    expectMap = transferParams(expectResult);
                                    if (expectMap.size() > 0) {
                                        Map resultMap = new HashMap();
                                        analysisJson(JSONObject.toJSON(netResponse), resultMap);
                                        // 判断实际结果与期望结果是否匹配
                                        Iterator<Map.Entry<String, String>> iter = expectMap.entrySet().iterator();
                                        int count = 0;
                                        while (iter.hasNext()) {
                                            Map.Entry entry = iter.next();
                                            if (resultMap.containsKey(entry.getKey())) {
                                                count++;
                                                try {
                                                    String truthResult = (String) resultMap.get(entry.getKey());
                                                    if (!entry.getValue().equals(truthResult))
                                                        resultDto.setPass_flag(Constants.FAIL);
                                                } catch (Exception e) {
                                                    log.error("接口实际返回结果非字符串");
                                                }
                                            }
                                        }
                                        // 实际结果中没有预期结果中的key
                                        if (count != expectMap.size() && Constants.SUCCESS.equals(resultDto.getPass_flag())) {
                                            resultDto.setPass_flag(Constants.FAIL);
                                        }
                                    }
                                }
                            }
                        } else {
                            resultDto.setPass_flag(Constants.FAIL);
                            resultDto.setError_msg(netResponse.getError().getErrorMsg());
                        }
                    } else {
                        log.info("http response is null");
                    }

                    resultDtos.add(resultDto);
                }

                File outFile = new File(Constants.EXCLE_UPLOAD_PATH + resultFileName);
                OutputStream ouputStream = new FileOutputStream(outFile);
                ExportExcel<ExecuteResultDto> export = new ExportExcel<ExecuteResultDto>();
                String headers[] = new String[]{"URL", "参数", "请求方式", "期望结果", "是否成功", "HTTP返回码", "异常描述", "接口实际返回结果"};

                export.exportExcel(DateUtil.dateToStrByTemplate(new Date(), Constants.DATE_TEMPLATE), headers, resultDtos, ouputStream, null);
            } else {
                model.put("errMsg", fileName + "\\u6587\\u4ef6\\u4e0d\\u5b58\\u5728"); // 文件不存在
                return "redirect:listall";
            }
        } catch (Exception e) {
            log.error("", e);
        }

        model.put("errMsg", "\\u6267\\u884c\\u5b8c\\u6bd5"); // 执行完毕
        return "redirect:listall";
    }

    public static void analysisJson(Object objJson, Map map) {
        //如果obj为json数组
        if (objJson instanceof JSONArray) {
            JSONArray objArray = (JSONArray) objJson;
            for (int i = 0; i < objArray.size(); i++) {
                analysisJson(objArray.get(i), map);
            }
        }
        //如果为json对象
        else if (objJson instanceof JSONObject) {
            toHashMap(objJson, map);
//            JSONObject jsonObject = (JSONObject) objJson;
//            Iterator it = jsonObject.keySet().iterator();
//            while (it.hasNext()) {
//                String key = it.next().toString();
//                Object object = jsonObject.get(key);
//                //如果得到的是数组
//                if (object instanceof JSONArray) {
//                    JSONArray objArray = (JSONArray) object;
//                    analysisJson(objArray, map);
//                }
//                //如果key中是一个json对象
//                else if (object instanceof JSONObject) {
////                    analysisJson((JSONObject) object, map);
//                    toHashMap(object, map);
//                }
//                //如果key中是其他
//                else {
//                    map.put(key, object);
//                }
//            }
        }
    }

    public static void toHashMap(Object object, Map map) {
        // 将json字符串转换成jsonObject
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            if(value != null){
                if(!(value instanceof String)){
                    value = value.toString();
                }
            }
            map.put(key, value);
        }
    }

    public static void main(String[] args) {
//        String json = "{\"respContent\":\"{\\\"data\\\":{\\\"firstInvest\\\":[{\\\"fundCode\\\":\\\"400009\\\",\\\"fundName\\\":\\\"东方稳健回报债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":5.09},{\\\"fundCode\\\":\\\"400013\\\",\\\"fundName\\\":\\\"东方保本混合型基金\\\",\\\"invstTypeMark\\\":\\\"保本型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":6.42}],\\\"steadyIncome\\\":[{\\\"fundCode\\\":\\\"400030\\\",\\\"fundName\\\":\\\"东方添益债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":10.71},{\\\"fundCode\\\":\\\"070005\\\",\\\"fundName\\\":\\\"嘉实债券\\\",\\\"invstTypeMark\\\":\\\"债券型\\\",\\\"minAmount\\\":1000,\\\"oneYear\\\":6.46}],\\\"highIncome\\\":[{\\\"fundCode\\\":\\\"400001\\\",\\\"fundName\\\":\\\"东方龙混合\\\",\\\"invstTypeMark\\\":\\\"混合型\\\",\\\"minAmount\\\":100,\\\"oneYear\\\":23.61},{\\\"fundCode\\\":\\\"080005\\\",\\\"fundName\\\":\\\"长盛量化红利混合\\\",\\\"invstTypeMark\\\":\\\"混合型\\\",\\\"minAmount\\\":1000,\\\"oneYear\\\":24.01}]},\\\"error\\\":[],\\\"success\\\":true}\",\"statusCode\":200,\"success\":true}";
//        String json = "{\"data\":{\"firstInvest\":[{\"fundCode\":\"400009\",\"fundName\":\"东方稳健回报债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":100,\"oneYear\":5.09},{\"fundCode\":\"400013\",\"fundName\":\"东方保本混合型基金\",\"invstTypeMark\":\"保本型\",\"minAmount\":100,\"oneYear\":6.42}],\"steadyIncome\":[{\"fundCode\":\"400030\",\"fundName\":\"东方添益债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":100,\"oneYear\":10.71},{\"fundCode\":\"070005\",\"fundName\":\"嘉实债券\",\"invstTypeMark\":\"债券型\",\"minAmount\":1000,\"oneYear\":6.46}],\"highIncome\":[{\"fundCode\":\"400001\",\"fundName\":\"东方龙混合\",\"invstTypeMark\":\"混合型\",\"minAmount\":100,\"oneYear\":23.61},{\"fundCode\":\"080005\",\"fundName\":\"长盛量化红利混合\",\"invstTypeMark\":\"混合型\",\"minAmount\":1000,\"oneYear\":24.01}]},\"error\":[],\"success\":true}";
        String json = "{\"buyerBudget\":258,\"eventType\":\"商务会议\",\"lastUpdateDatetime\":0,\"acceptReschedule\":\"N\",\"inquiryDateTime\":0,\"inquiryType\":\"NORMAL\",\"eventDuration\":24,\"buyerName\":\"nxr1\",\"eventDate\":1476806400000,\"priority\":0,\"placeName\":\"国投北楼303w@2\",\"placeId\":\"ZN7YJ0\",\"hasHotel\":\"N\",\"hasDinning\":\"N\",\"buyerId\":\"30F8D32A7EA9455CBFAC435D4A548195\",\"comments\":\"测试，请勿处理!\",\"headcount\":5,\"cityName\":\"北京\",\"buyerMobileNum\":\"18800030327\",\"originalChannel\":\"百场汇\",\"buyerIMId\":\"bch-gredje6\"}";
        Map map = (Map)net.sf.json.JSONObject.fromObject(json);
//        analysisJson(net.sf.json.JSONObject.fromObject(json),map);

        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(json);
        Iterator iter = jsonObject.keys();
        while (iter.hasNext()){
            Object key = iter.next();
            System.out.println(key + "----" + map.get(key));
        }

        Map parammap = new HashMap();
        toHashMap(json, parammap);
        System.out.println("======>" + JSONObject.toJSONString(HttpClientUtil.sendHttpClientPut("http://api.baichanghui.com/orderservice/inquiries", parammap, Constants.CHARSET_UTF_8)));

//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()){
//            Object key = iter.next();
//            System.out.println(key + "----" + map.get(key));
//        }
    }

    /**
     * 转换参数
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:55
     */
    private Map transferParams(String param) {
        Map<String, String> params = new HashMap<>();
        if (StringUtil.isNotBlank(param)) {
            String[] paramArr = param.split("&");
            for (int i = 0; i < paramArr.length; i++) {
                String arr[] = paramArr[i].split("=");
                if (arr != null && arr.length == 2)
                    params.put(arr[0], arr[1]);
            }
        }
        return params;
    }

    /**
     * 下载结果文件
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-30 17:31
     */
    @RequestMapping(value = "/excel/exportResult")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,
                            @RequestHeader("User-Agent") String userAgent, String fileName,
                            ModelMap model) throws Exception {

        try {
//            File file = new File(Constants.EXCLE_UPLOAD_PATH + fileName);
            String resultFileName = fileName.substring(0, fileName.indexOf(".")) + "_result"
                    + fileName.substring(fileName.indexOf("."));
            File resultFile = new File(Constants.EXCLE_UPLOAD_PATH + resultFileName);
            List<ExecuteResultDto> executeResultDtos = new ArrayList<>();
            if (resultFile.exists()) {
                // 解析excel文件
                executeResultDtos = excelParseService.pareseResultFile(resultFileName);
            } else {
                model.put("errMsg", resultFileName + "\\u6587\\u4ef6\\u4e0d\\u5b58\\u5728"); // 文件不存在
                return;
            }
            String expName = "auto-test";
            expName += "_" + DateUtil.dateToStrByTemplate(new Date(), Constants.DATE_TEMPLATE) + "_result";
            String filename = "filename=\"" + expName + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1) {
                    filename = "filename=\"" + expName + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1) {
                    filename = "filename*=UTF-8''" + expName;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1) {
                    filename = "filename=\""
                            + new String(expName.getBytes("UTF-8"), "ISO8859-1") + "\"";
                }
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1) {
                    expName = MimeUtility.encodeText(expName, "UTF8", "B");
                    filename = "filename=\"" + expName + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1) {
                    filename = "filename*=UTF-8''" + expName;
                }
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;" + filename + ".xlsx");
            OutputStream ouputStream = response.getOutputStream();
            ExportExcel<ExecuteResultDto> export = new ExportExcel<ExecuteResultDto>();
            String headers[] = new String[]{"URL", "参数", "请求方式", "期望结果", "是否成功", "HTTP返回码", "异常描述", "接口实际返回结果"};

            export.exportExcel(expName, headers, executeResultDtos, ouputStream, null);
        } catch (Exception e) {
            log.error("", e);
            model.put("errMsg", e.getMessage());
        }
    }

    @ExceptionHandler
    public void exp(Exception ex) {
        log.error("", ex);
    }
}
