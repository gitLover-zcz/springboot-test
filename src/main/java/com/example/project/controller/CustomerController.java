package com.example.project.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Customer;
import com.example.project.service.CustomerService;
import com.example.project.utils.MyQuery;
import com.example.project.utils.QueryUtil;
import com.example.project.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("toList")
    public String toList() {

        return "custom/customerList";
    }

    /**
     * 查询数据
     * @param param
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public R<Map<String, Object>> list(@RequestParam Map<String, String> param) {
//        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = Wrappers.<Customer>lambdaQuery()
//                .like(StringUtils.isNotBlank(realName), Customer::getRealName, realName)
//                .like(StringUtils.isNotBlank(phone), Customer::getPhone, phone)
//                .orderByDesc(Customer::getCustomerId);
//        Page<Customer> customerPage = customerService.page(new Page<>(page, limit), customerLambdaQueryWrapper);

//        Page<Customer> customerPage = customerService.lambdaQuery()
//                .like(StringUtils.isNotBlank(realName), Customer::getRealName, realName)
//                .like(StringUtils.isNotBlank(phone), Customer::getPhone, phone)
//                .orderByDesc(Customer::getCustomerId).page(new Page<>(page, limit));

        MyQuery<Customer> myQuery = QueryUtil.<Customer>buildMyQuery(param);
        Page<Customer> customerPage = customerService.page(myQuery.getPage(), myQuery.getWrapper().orderByDesc("customer_id"));

        R<Map<String, Object>> mapR = ResultUtil.buildPageR(customerPage);
        return mapR;
    }

    /**
     * 新增
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd() {
        return "custom/customerAdd";
    }

    /**
     * 新增客户
     * @param customer
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Customer customer) {
        return ResultUtil.buildR(customerService.save(customer));
    }

    /**
     * 修改
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "custom/customerUpdate";
    }

    /**
     * 修改客户
     * @param customer
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Customer customer) {
        return ResultUtil.buildR(customerService.updateById(customer));
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id) {
        return ResultUtil.buildR(customerService.removeById(id));
    }

    /**
     * 进入详情页
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "custom/customerDetail";
    }
}
