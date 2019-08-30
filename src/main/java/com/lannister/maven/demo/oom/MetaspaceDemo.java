package com.lannister.maven.demo.oom;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

//添加VM参数: -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
public class MetaspaceDemo {
	static class OOMTest{}
	public static void main(String[] args) {
		int i = 0;
		
		try {
			while(true) {
				i++;
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(OOMTest.class);
				enhancer.setUseCache(false);
				enhancer.setCallback(new MethodInterceptor() {
					
					public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
						// TODO Auto-generated method stub
						return proxy.invokeSuper(obj, args);
					}
				});
				enhancer.create();
			}
		} catch (Throwable e) {
			// TODO: handle exception
			System.out.println("************多少次后发生了错误： " + i);
			e.printStackTrace();
		}
	}
}
