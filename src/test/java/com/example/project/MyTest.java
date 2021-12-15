package com.example.project;

import com.example.project.service.impl.ResourceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {
    @Autowired
    private ResourceServiceImpl resourceService;

    @Test
    public void testSearch() {
        resourceService.listResourceByRoleId(1);
    }
}
