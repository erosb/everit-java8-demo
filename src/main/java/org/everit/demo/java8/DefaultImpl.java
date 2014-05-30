package org.everit.demo.java8;

interface DefaultImpl {
	
	static void myStaticMethod() {
		System.out.println("DefaultImpl.myStaticMethod()");
	}
	
	// static void myAbstractStaticMethod();
	
	void myAbstractMethod();
	
	default void myMethod() {
		System.out.println("DefaultImpl.myMethod()");
	}
	
	default void myMethod2() {
		System.out.println("DefaultImpl.myMethod2()");
	}
	
	
	
}
