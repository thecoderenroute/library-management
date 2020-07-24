package template;

import org.junit.jupiter.api.Test;

class PersonTest {

    @Test
    public void testName(){
        Person p1 = new Person("J. K. Rowling");
        System.out.println(p1);
        Person p2 = new Person("J. R. R. Tolkein");
        System.out.println(p2);
    }

}