package org.everit.demo.java8;

import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceExamples {

    {
        /*
         * Példányszintű metódusra hivatkozunk.
         * 
         * Osztálynév :: metódusnév
         * 
         * Ez a típusú metódusreferencia olyan függvényként használható, melynek egyetlen paraméterének típusa a
         * minősítő osztály, visszatérési értékének típusa a hivatkozott metódus visszatérési típusa.
         */
        Function<User, String> userNameFn = User::getName;
        // userNameFn = (u) -> u.getName();

        /*
         * Konstruktorra hivatkozunk.
         * 
         * Osztálynév :: new
         * 
         * Ez a típusú metódusreferencia olyan függvényként használható, melynek paraméterlistája üres, a visszatérési
         * értékének típusa a minősítő osztály
         */
        Supplier<String> stringSupplier = String::new;
        // ugyanez: stringSupplier = () -> new String();

        /*
         * Példányszintű metódusra hivatkozunk, de objektummal minősítünk.
         *
         * objektum :: metódusnév
         *
         * Ez a típusú metódusreferencia olyan függvényként használható, melynek paraméterlistája üres, visszatérési
         * értékének típusa a metódus visszatérési típusa
         */
        User u = new User(23, "blahh");
        stringSupplier = u::getName;
        // stringSupplier = () -> u.getName();

        // lehet így egyben is:
        // stringSupplier = new User(23, "blahh")::getName;
    }

}
