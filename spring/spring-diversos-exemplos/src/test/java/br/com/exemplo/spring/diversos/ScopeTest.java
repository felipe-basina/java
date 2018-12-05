package br.com.exemplo.spring.diversos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScopeTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void createPrototypeInstances() {
		ComponentePrototype prototype1 = this.context.getBean(ComponentePrototype.class);
		ComponentePrototype prototype2 = this.context.getBean(ComponentePrototype.class);
		Assert.assertFalse(prototype1 == prototype2);
		Assert.assertFalse(prototype1.hashCode() == prototype2.hashCode());
	}
	
	@Test
	public void createSingletonInstances() {
		ComponenteSingleton singleton1 = this.context.getBean(ComponenteSingleton.class);
		ComponenteSingleton singleton2 = this.context.getBean(ComponenteSingleton.class);
		Assert.assertTrue(singleton1 == singleton2);
		Assert.assertTrue(singleton1.hashCode() == singleton2.hashCode());
	}

}
