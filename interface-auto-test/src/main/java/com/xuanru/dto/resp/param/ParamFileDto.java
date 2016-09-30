package com.xuanru.dto.resp.param;

import com.xuanru.dto.resp.BaseRespDto;

/**
 * Created by Liaoxf on 2016-09-30 15:33.
 */
public class ParamFileDto extends BaseRespDto {
    private String fileName;

    private String downloadPath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
