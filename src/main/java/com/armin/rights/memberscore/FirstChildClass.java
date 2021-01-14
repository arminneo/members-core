package com.armin.rights.memberscore;

import java.time.DayOfWeek;

public class FirstChildClass extends ParentClass {

    public boolean doSomething() {
        return false;
    }

    public void switchIt(DayOfWeek day) {

        switch (day) {
            case MONDAY:
            case TUESDAY:
                WEDNESDAY:
                doSomething();
                break;
        }

    }
}
