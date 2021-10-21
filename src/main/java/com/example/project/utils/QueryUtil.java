package com.example.project.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;
import java.util.Set;

public class QueryUtil {
    /**
     * 构建MyQuery对象服务于分页查询方法
     * @param param
     * @param <T>
     * @return
     */
    public static <T> MyQuery<T> buildMyQuery(Map<String, String> param) {
        MyQuery myQuery = new MyQuery();
        QueryWrapper wrapper = new QueryWrapper();
        Long page = 1L;
        Long limit = 10L;
        Set<Map.Entry<String, String>> entries = param.entrySet();
        for (Map.Entry<String, String> entry: entries) {
            String value = entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                String key = entry.getKey();
                if ("page".equals(key)) {
                    page = Long.parseLong(value);
                } else if ("limit".equals(key)) {
                    limit = Long.parseLong(value);
                } else {
                    String[] strings = key.split("\\$");
                    switch (strings[0]) {
                        case "like":
                            wrapper.like(strings[1], value);
                            break;
                        case "ge":
                            wrapper.ge(strings[1], value);
                            break;
                        case "le":
                            wrapper.le(strings[1], value);
                            break;
                        case "eq":
                        default:
                            wrapper.like(strings[1], value);
                            break;
                    }
                }
            }
        }

        myQuery.setPage(new Page(page, limit));
        myQuery.setWrapper(wrapper);
        return myQuery;
    }
}
