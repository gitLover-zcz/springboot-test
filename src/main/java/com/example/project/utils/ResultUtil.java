package com.example.project.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    /**
     * 公共查询结果
     * @param page
     * @return
     */
    public static R<Map<String, Object>> buildPageR(IPage page) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("count", page.getTotal());
        data.put("records", page.getRecords());
        return R.ok(data);
    }

    /**
     * 公共操作结果
     * @param success
     * @return
     */
    public static R<Object> buildR(boolean success) {
        if (success) {
            return R.ok(null);
        }
        return R.failed("操作失败");
    }
}
