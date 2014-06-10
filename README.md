<meta http-equiv="content-type" content="text/html;charset=utf-8"/>

Előkészületek
=============
* jdk1.8.0
* eclipse kepler java8 support: <a href="http://www.eclipse.org/downloads/java8/">step-by-step guide</a>
* git (1.7+)
* repó itt:

<pre>
	git clone https://github.com/erosb/everit-java8-demo.git
	git checkout -b lambda-syntax origin/lambda-syntax
	git checkout -b def-impl origin/def-impl
	git checkout -b stream origin/stream
	git checkout -b stream-examples origin/stream-examples
</pre>


Default implementációk interfészekben
=====================================

* interfészekben korábban nem lehetett implementációt tenni, java8-ban lehet.
* a szintaxis kicsit más, default kulcsszóval kell kezdeni a deklarációt.
* interfészekbe statikus metódusokat is tehetünk, ezeket mindenképp implementálni kell.
* adattagokat változatlanul nem lehet tenni

Gyakori usecase: java7-ben ha egy interfésznek több implementációja volt, amelyekben az implementációk részben egyeztek,
akkor

- definiáltuk az interfészt
- létrehoztunk egy absztrakt osztályt, mely implementálja az interfésznek azon metódusait, melyek a konkrét implementációkban
azonosak
- az absztrakt osztályból származtattuk a konkrét implementációkat

Java8-ban az absztrakt osztályt ki lehet hagyni, a közös implementációk kerülhetnek az interfészbe.

Al-interfészekben:

* az ősinterfészben default implementációval nem rendelkező metódusnak implementációt lehet adni
* az ősinterfészben default implementációval rendelkező metódusnak új implementációt lehet adni (felüldefiniálás)
* az ősinterfészben default implementációval rendelkező metódust újra lehet definiálni absztrakt metódusként
* super minősítő interfész-kontextusban nem használható
* final módosító nem használható

	git checkout def-impl

Lambda
======

A "lambda" kifejezés névtelen függvényekre utal.
A névtelen függvények mindig (referencia-típusú) <strong>értékek</strong>.
A típusok mindig a kontextustól függ.
Korábban főleg névtelen belső osztályokkal helyettesítettük őket.

Funkcionális interfészek
------------------------

Java7-terminológiában funkcionális ("függvény-jellegű") interfészeknek nevezzük azokat az interfészeket, amelyek pontosan
egy metódust deklarálnak.

Java8-terminológiában: azok az interfészek, melyek legalább egy metódust deklarálnak, és pontosan egy metódusuknak nincs
alapértelmezett implementációja.

Példák:
Runnable, Comparator, ActionListener, stb

@FunctionalInterface annotáció: ha ezzel fel van annotálva egy interfész, akkor ennek - a fenti feltételeknek megfelelően
- funkcionális interfésznek kell lenni, különben a fordító hibát dob. Tehát ez egy a @Override annotációhoz hasonló
"biztonsági háló".

Lambda szintaxis
----------------
	(<paraméterlista>) -> <törzs>

paraméterlista: nem kell feltétlenül kiírni a típusokat, a fordító interferálja

a törzs lehet:

* kapcsoszárójelek között egy blokk
* egy kifejezés

	git checkout lambda-syntax
	
Példák: org.everit.demo.java8.User.java

Láthatóság
----------
Mivel a lambda függvények tulajdonképp névtelen osztályok, ezért nagyjából ugyanazok a szabályok vonatkoznak a változók
láthatóságára.

Java7-ben a névtelen osztályokban a tartalmazó metódusnak csak a final lokális változóit lehetett használni.
Java8-ban nem kell kiírni a final módosítót, azt viszont a fordító ellenőrzi, hogy a változó "effectively final"-e, azaz
történik-e értékadás a deklarációban szereplő keződértékadás után.

Metódusreferenciák
------------------

A lambda függvényeket arra használjuk, hogy egy funkcionális interfésznek új implementációját készítsük el vele.

Lehetőség van arra is, hogy egy meglévő metódust használjunk egy funkcionális interfész implementációjaként.

A meglévő metódusra való hivatkozást nevezzük metódusreferenciának. Elég sok variációja van, a :: minősítő operátorról
mindegyiket fel lehet ismerni.

Példák: org.everit.demo.java8.MethodReferenceExamples.java

A példákban most a java.util.function-t használjuk. Ebben a csomagban olyan interfészek vannak, melyek különböző
szignatúrájú függvényeket reprezentálnak.

Bővebben: http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-final.html

Stream API
==========

A stream API alapvetően kollekciók feldolgozására való. java.util.stream.Stream

Inkább Iterator-ra hasonlít, mint kollekcióra:

 * nem tárol elemeket
 * "egyszerhasználatos", a "forrás" minden eleme csak egyszer dolgozható fel
 * funkcionális API-t ad, lambda függvényekkel együtt érdemes használni
 * magának a stream-nek nem kell feltétlenül véges méretűnek lennie (szemben a kollekcióval, aminek mindig kell, hogy
 legyen mérete), végtelen stream feldolgozása leállítható
 rövidzár-kiértékelés jellegű művelettel (limit(int), findFirst())
 * a legtöbb stream metódus lazy-kiértékelés jellegű, pipeline-jellegű

stream példányosítás:

 * kollekcióból: Collection.stream(), Collection.parallelStream()
 * tömbből: Arrays.stream(Object[]), Stream.of(Object[])
 * karakteres inputból: BufferedReader.lines()
 * üres stream: Stream.<T> empty()
 * meg még sokminden más: IntStream.range(int, int), JarFile.stream()
 
 
A stream metódusokat két kategóriába lehet csoportosítani:

- köztes (intermediate) metódusok: Stream visszatérési értékűek
- terminális metódusok: a feldolgozás utolsó lépésében hívjuk őket (visszatérési értékük nem stream)
	
Egy stream pipeline

- egy forrásból (pl. Collection)
- 0 vagy több köztes műveletből (stream.filter(), stream.map())
- egy terminális műveletből (stream.forEach(), stream.count())
áll.

A köztes műveletek mindig lazy metódusok. Egy stream.filter(predicate) nem szűr semmit sem, csak létrehoz egy új
streamet, melyet bejárva a megszűrt elemeket kapjuk vissza.
Ez azt jelenti, hogy a <strong>forrás bejárása nem kezdődik meg a terminális művelet meghívásáig</strong>.

A terminális művelet meghívása majdnem mindig a forrás teljes bejárását jelenti. Pontosabban:

* ha rövidzár-kiértékelés jellegű köztes metódushívás van a pipeline-ban (pl. stream.limit(int)), akkor nyilván csak
 egy részét járjuk be a forrásnak
* ha a terminális művelet stream.iterator() akkor ez nem járja be a forrást. Ezt
 akkor lehet érdemes használni, ha a stream API-val nem tudjuk megoldani, amit szeretnénk.
 
Párhuzamosítás
--------------

 * Collection.parallelStream()
 * stream.parallel()
 
Fontos: párhuzamos streameknél a műveletek legyenek  non-interfering stateless függvények. Ilyet ne:

	String implode(List<String> entries) {
		final StringBuilder sb = new StringBuilder();
		entries.parallelStream().forEach(entry -> sb.append(entry));
		return sb.toString();
	}

<i>Kérdés: hogyan lehet a párhuzamosan futó szálak számát szabályozni?</i>

Mellékhatásokat igyekezzünk kerülni.
  
Stream példák
-------------

	git checkout 51718dae493a30b2ef
	git checkout 4387ff866be77a
	git checkout b238a5e846cc2fb7d854c0
	git checkout 5c13022d7856c8905a
	git checkout 51ebb14eb1a6d3f7b9216ff
	git checkout 8afda1581f51c30531db6b3
	git checkout 5e94e153ee235512a56f1de


Primitív streamek
-----------------

A Stream API alapvető osztálya a Stream<T>. A T Object alosztály, így ha Stream-et akarnánk primitív értékek feldolgozására
használni, akkor az állandó autoboxinggal járna.

A Stream<T> mellett vannak primitív streamek:

 * IntStream
 * DoubleStream
 * LongStream

Stream<T> példányt leképezni a stream.mapTo* függvényekkel tudunk:

 * IntStream stream.mapToInt()
 * DoubleStream stream.mapToDouble()
 * LongStream stream.mapToLong()
 
Feladatok
---------

	git checkout stream-examples

Stream-ek felhasználási területei
---------------------------------
- elsősorban memóriabeli adatfeldolgozásra
- esetleg IO-ra (lásd java.nio.file.Files)
- adatbáziskezelésre nem


Reflectionnel kapcsolatos újdonságok
====================================

Repeating annotations
---------------------

Java7-ben nem lehet ugyanazzal az annotációval de különböző paraméterekkel ellátni ugyanazt az elemet, ezért tömb-jellegű
annotációkat használunk, pl.:

	public @interface Property {  ...  }
	
	public @interface Properties {
		Property[] value();
	}
	
És így használjuk:

	@Properties({
		@Property(name = "eosgi.testEngine", value = "junit4"),
		@Property(name = "eosgi.testId", value = "blobstoreTest"),
		@Property(name = "blobstore.target")
	})
	public class BlobstoreTest { ... }

Java8-ban lehet ismételhető annotációkat használni. Ez kicsit megkönnyíti a használatukat, de egyébként csak syntax sugar,
ugyanis az annotáció definíciójánál ugyanúgy két annotációra van szükség.

A fenti annotációkat java8-ban újradefiniálhatjuk így:

	@Repeatable(Properties.class)
	public @interface Property {  ...  }
	
	public @interface Properties {
		Property[] value();
	}
	

Az annotáció használatánál viszont nem kell kiírni a @Properties annotációt:

	@Property(name = "eosgi.testEngine", value = "junit4"),
    @Property(name = "eosgi.testId", value = "blobstoreTest"),
    @Property(name = "blobstore.target")
	public class BlobstoreTest { ... }

A annotációk reflectionnel történő feldolgozása is ugyanúgy történik mint korábban, tehát először a konténer-annotációt
kérdezzük le (@Properties), utána ebből a tömb elemeit (@Property).

Reflection API
--------------

A java.lang.reflect csomagban
* a Method és a Constructor kapott egy közös ősosztályt: Executable
* új osztály: Parameter
	a formális paraméternevek elérhetőek futásidőben, de csak akkor, ha -parameters kapcsolóval futtattuk a javac-ot
	(default <code>false</code>)
	<i>megjegyzés: a standard API-ban levő osztályokat nem így fordították...</i>

Amiről nem volt szó
===================
- új datetime API
- "annotations on java types"
