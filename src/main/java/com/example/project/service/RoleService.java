package com.example.project.service;

import com.example.project.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增角色及角色所具有的资源
     * @param role
     * @return
     */
    boolean saveRole(Role role);

    /**
     * 修改角色及角色所具有的资源
     * @param role
     * @return
     */
    boolean updateRole(Role role);
}
