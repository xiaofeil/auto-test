package com.xuanru.service;

import com.xuanru.dto.resp.param.ExecuteResultDto;

import java.util.List;

/**
 * Created by Liaoxf on 2016-09-30 14:16.
 */
public interface IExcelParseService {

    List<ExecuteResultDto> pareseResultFile(String fileName);
}
