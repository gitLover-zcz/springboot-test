package com.example.project.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Account;
import com.example.project.entity.Role;
import com.example.project.service.AccountService;
import com.example.project.service.ResourceService;
import com.example.project.service.RoleService;
import com.example.project.utils.ResultUtil;
import com.example.project.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AccountService accountService;

    @GetMapping("toList")
    public String toList() {
        return "role/roleList";
    }

    /**
     * 查询数据
     * @param roleName
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public R<Map<String, Object>> list(String roleName, Long page, Long limit) {
        Page<Role> rolePage = roleService.lambdaQuery()
                .like(StringUtils.isNotBlank(roleName), Role::getRoleName, roleName)
                .orderByDesc(Role::getRoleId).page(new Page<>(page, limit));

        return ResultUtil.buildPageR(rolePage);
    }

    /**
     * 新增
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd() {
        return "role/roleAdd";
    }

    /**
     * 新增客户
     * @param role
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Role role) {
        return ResultUtil.buildR(roleService.saveRole(role));
    }

    /**
     * 修改
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "role/roleUpdate";
    }

    /**
     * 修改客户
     * @param role
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Role role) {
        return ResultUtil.buildR(roleService.updateRole(role));
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id) {
        Integer count = accountService.lambdaQuery().eq(Account::getRoleId, id).count();
        if (count > 0) {
            return R.failed("有账号正拥有该角色");
        }
        return ResultUtil.buildR(roleService.removeById(id));
    }

    /**
     * 进入详情页
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "role/roleDetail";
    }

    @GetMapping({"listResource", "listResource/{roleId}", "listResource/{roleId}/{flag}"})
    @ResponseBody
    public R<List<TreeVO>> listResource(@PathVariable(required = false) Long roleId
            , @PathVariable(required = false) Integer flag) {
        return R.ok(resourceService.listResource(roleId, flag));
    }
}
