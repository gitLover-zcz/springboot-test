package com.example.project.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号表 Mapper 接口
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page, @Param(Constants.WRAPPER) Wrapper<Account> wrapper);

    /**
     * 根据accountId获取用户
     * @param id
     * @return
     */
    Account getAccountById(Long id);
}
