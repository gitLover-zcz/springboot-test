package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.project.dao.RoleResourceMapper;
import com.example.project.entity.Role;
import com.example.project.dao.RoleMapper;
import com.example.project.entity.RoleResource;
import com.example.project.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(Role role) {
        save(role);
        List<Long> resourceIds = role.getResourceIds();
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            for (Long resourceId:resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(role.getRoleId());
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role) {
        updateById(role);
        roleResourceMapper.delete(Wrappers.<RoleResource>lambdaQuery()
                .eq(RoleResource::getRoleId, role.getRoleId()));
        List<Long> resourceIds = role.getResourceIds();
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            for (Long resourceId:resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(role.getRoleId());
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }
}
