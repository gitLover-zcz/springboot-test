package com.example.project.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.project.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;

/**
 * 自动填充类
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");

        if (metaObject.hasSetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)}
        }

        if (metaObject.hasSetter("createAccountId")) {
            Object account = RequestContextHolder.getRequestAttributes()
                    .getAttribute("account", RequestAttributes.SCOPE_SESSION);
            if (account != null) {
                Long accountId = ((Account) account).getAccountId();
                this.strictInsertFill(metaObject, "createAccountId", Long.class, accountId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        if (metaObject.hasSetter("modifiedTime")) {
            this.strictUpdateFill(metaObject, "modifiedTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)}
        }

        if (metaObject.hasSetter("modifiedAccountId")) {
            Object account = RequestContextHolder.getRequestAttributes()
                    .getAttribute("account", RequestAttributes.SCOPE_SESSION);
            if (account != null) {
                Long accountId = ((Account) account).getAccountId();
                this.strictUpdateFill(metaObject, "modifiedAccountId", Long.class, accountId);
            }
        }
    }
}
