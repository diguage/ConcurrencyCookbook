package com.diguage.books.concurrencycookbook.chapter4.recipe4;

import java.util.concurrent.Callable;

/**
 * Executor取第一个结果示例：验证任务
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 23:16
 */
public class TaskValidator implements Callable<String> {
    private UserValidator validator;
    private String name;
    private String password;

    public TaskValidator(UserValidator validator, String name, String password) {
        this.validator = validator;
        this.name = name;
        this.password = password;
    }

    @Override
    public String call() throws Exception {
        if (!validator.validate(name, password)) {
            System.out.printf("%s: The user has not been found\n",
                    validator.getName());
            throw new Exception("Error validating user");
        }
        System.out.printf("%s: The user has been found\n",
                validator.getName());

        return validator.getName();
    }
}
