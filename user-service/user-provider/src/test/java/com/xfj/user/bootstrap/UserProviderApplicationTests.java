package com.xfj.user.bootstrap;

import com.xfj.user.services.bl.UserRegisterServiceBl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class UserProviderApplicationTests {

    @Autowired
    UserRegisterServiceBl userRegisterServiceBl;

    @Test
    public void testTransaction()  {
		try {
//			userRegisterServiceBl.testBussness();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
