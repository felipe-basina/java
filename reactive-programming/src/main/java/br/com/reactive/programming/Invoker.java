package br.com.reactive.programming;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invoker {
	
	private static final String PRINT_PREFIX = "print";
	
	private Class<?> c;
	
	private Object t;
	
	public Invoker(Class<?> c) {
		super();
		this.c = c;
		try {
			this.t = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void callMethods() {
		for (Method method : c.getDeclaredMethods()) {
			if (method.getName().contains(PRINT_PREFIX)) {
				try {
					method.invoke(t, new Object[] {});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
}
