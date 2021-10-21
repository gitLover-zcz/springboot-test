package com.example.project.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.project.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.vo.ResourceVO;
import com.example.project.vo.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询当前登录人的资源
     * @param wrapper
     * @return
     */
    List<ResourceVO> listResource(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper);


    List<TreeVO> listResourceByRoleId(
            @Param(Constants.WRAPPER) Wrapper<Resource> wrapper
            , @Param("roleId") Long roleId
    );
}
