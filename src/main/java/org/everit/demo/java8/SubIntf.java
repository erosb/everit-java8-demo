package org.everit.demo.java8;

interface SubIntf extends DefaultImpl {
	
	/**
	 * Az ősinterfészben default implementációval rendelkező metódusnak új implementációt lehet adni (felüldefiniálás).
	 */
	@Override
	default void myMethod() {
		System.out.println("SubIntf.myMethod()");
	}
	
	/**
	 * Az ősinterfészben default implementációval rendelkező metódust újra lehet definiálni absztrakt metódusként.
	 */
	void myMethod2();
	
	/**
	 * Az ősinterfészben default implementációval nem rendelkező metódusnak implementációt lehet adni.
	 */
	default void myAbstractMethod() {
		System.out.println("SubIntf.myAbstractMethod()");
	}

}
