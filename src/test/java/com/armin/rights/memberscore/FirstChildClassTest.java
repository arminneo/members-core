package com.armin.rights.memberscore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstChildClassTest {

    @Test
    void doSomething() {
        FirstChildClass first = new FirstChildClass();
        boolean something = first.doSomething();
        assertFalse(something);
    }
}
