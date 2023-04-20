package model.people;

import model.Government;
import model.people.enums.Troops;

public class Engineer extends Person{
    public Engineer(Government government, Troops troop) {
        super(government, 5,5);
    }
}