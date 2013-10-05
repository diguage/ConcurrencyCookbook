package com.diguage.books.concurrencycookbook.chapter4.recipe4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Executor取第一个结果示例：用户验证
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 23:12
 */
public class UserValidator {
    private String name;

    public UserValidator(String name) {
        this.name = name;
    }

    public boolean validate(String name, String password) {
        Random random = new Random();
        try {
            long duration = random.nextInt(10);
            System.out.printf("Validator %s: Validating a user during %d seconds\n",
                    this.name,
                    duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            return false;
        }

        return random.nextBoolean();
    }

    public String getName() {
        return name;
    }
}
