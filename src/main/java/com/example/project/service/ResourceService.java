package com.example.project.service;

import com.example.project.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.vo.ResourceVO;
import com.example.project.vo.TreeVO;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 根据角色的ID，查询该角色所具有的资源
     * @param roleId
     * @return
     */
    List<ResourceVO> listResourceByRoleId(long roleId);

    /**
     * 查询系统资源，供前端使用渲染
     * @return
     * @param roleId
     * @param flag
     */
    List<TreeVO> listResource(Long roleId, Integer flag);

    HashSet<String> convert(List<ResourceVO> resourceVOS);
}
