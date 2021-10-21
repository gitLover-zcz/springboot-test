package com.example.project.service.impl;

import com.example.project.entity.Customer;
import com.example.project.dao.CustomerMapper;
import com.example.project.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author zcz
 * @since 2021-09-27
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
