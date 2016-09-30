package com.xuanru.service.impl;

import com.xuanru.common.Constants;
import com.xuanru.dto.resp.param.ExecuteResultDto;
import com.xuanru.service.IExcelParseService;
import com.xuanru.util.ParseExcelUtil;
import com.xuanru.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liaoxf on 2016-09-30 14:17.
 */
@Service
@Slf4j
public class ExcelParseService implements IExcelParseService {
    @Override
    public List<ExecuteResultDto> pareseResultFile(String fileName) {
        List<ExecuteResultDto> executeResultDtos = null;
        try {
            String postfix = ParseExcelUtil.getPostfix(fileName);
            if (Constants.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                executeResultDtos = parseMeetingXls(Constants.EXCLE_UPLOAD_PATH + fileName);
            } else if (Constants.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                executeResultDtos = parseMeetingXlsx(Constants.EXCLE_UPLOAD_PATH + fileName);
            }
        } catch (Exception e) {
            log.error("pareseResultFile", e);
        }

        return executeResultDtos;
    }

    private List<ExecuteResultDto> parseMeetingXls(String path)
            throws Exception {
        List<ExecuteResultDto> executeResultDtos = null;
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        ExecuteResultDto executeResultDto = null;
        // Read the Sheet 只解析第一个sheet
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
            return executeResultDtos;
        }
        // 根据title计算最大列数
        HSSFRow titleRow = hssfSheet.getRow(0);
        int colNum = 0;// 有效总列数
        if (titleRow != null) {
            for (int i = 0; i < 1000; i++) {
                if (StringUtil.isNotBlank(ParseExcelUtil.getValue(titleRow.getCell(i)))) {
                    colNum++;
                } else {
                    break;
                }
            }
        }
        // Read the Row
        // 第一行为title，不解析，故从第二行开始
        log.info("结果总记录数=[{}]", new Object[]{hssfSheet.getLastRowNum()});
        if (hssfSheet.getLastRowNum() >= 1 && colNum > 0) {
            executeResultDtos = new ArrayList<>();
            HSSFRow hssfRow = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    executeResultDto = new ExecuteResultDto();
                    executeResultDto.setUrl(ParseExcelUtil.getValue(hssfRow.getCell(0)));
                    executeResultDto.setParams(ParseExcelUtil.getValue(hssfRow.getCell(1)));
                    executeResultDto.setRequest_method(ParseExcelUtil.getValue(hssfRow.getCell(2)));
                    executeResultDto.setExpect_result(ParseExcelUtil.getValue(hssfRow.getCell(3)));
                    executeResultDto.setPass_flag(ParseExcelUtil.getValue(hssfRow.getCell(4)));
                    executeResultDto.setStatus_code(ParseExcelUtil.getValue(hssfRow.getCell(5)));
                    executeResultDto.setError_msg(ParseExcelUtil.getValue(hssfRow.getCell(6)));
                    executeResultDtos.add(executeResultDto);
                } else {
                    log.info("hssfRow is null.rowNum=[{}]", rowNum);
                }
            }

        } else {
            log.info("没有执行结果。file=[{}]", path);
        }

        return executeResultDtos;
    }

    private List<ExecuteResultDto> parseMeetingXlsx(String path)
            throws Exception {
        List<ExecuteResultDto> executeResultDtos = null;
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        ExecuteResultDto executeResultDto = null;
        // Read the Sheet 只解析第一个sheet
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        if (xssfSheet == null) {
            return executeResultDtos;
        }
        // 根据title计算最大列数
        XSSFRow titleRow = xssfSheet.getRow(0);
        int colNum = 0;// 有效总列数
        if (titleRow != null) {
            for (int i = 0; i < 1000; i++) {
                if (StringUtil.isNotBlank(ParseExcelUtil.getValue(titleRow.getCell(i)))) {
                    colNum++;
                } else {
                    break;
                }
            }
        }
        // Read the Row
        // 第一行为title，不解析，故从第二行开始
        log.info("结果总记录数=[{}]", new Object[]{xssfSheet.getLastRowNum()});
        if (xssfSheet.getLastRowNum() >= 1 && colNum > 0) {
            executeResultDtos = new ArrayList<>();
            XSSFRow xssfRow = null;
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    executeResultDto = new ExecuteResultDto();
                    executeResultDto.setUrl(ParseExcelUtil.getValue(xssfRow.getCell(0)));
                    executeResultDto.setParams(ParseExcelUtil.getValue(xssfRow.getCell(1)));
                    executeResultDto.setRequest_method(ParseExcelUtil.getValue(xssfRow.getCell(2)));
                    executeResultDto.setExpect_result(ParseExcelUtil.getValue(xssfRow.getCell(3)));
                    executeResultDto.setPass_flag(ParseExcelUtil.getValue(xssfRow.getCell(4)));
                    executeResultDto.setStatus_code(ParseExcelUtil.getValue(xssfRow.getCell(5)));
                    executeResultDto.setError_msg(ParseExcelUtil.getValue(xssfRow.getCell(6)));
                    executeResultDtos.add(executeResultDto);
                } else {
                    log.info("hssfRow is null.rowNum=[{}]", rowNum);
                }
            }

        } else {
            log.info("没有执行结果。file=[{}]", path);
        }

        return executeResultDtos;
    }

}
