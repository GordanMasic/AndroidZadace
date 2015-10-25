package com.example.gordan.addpeople;

import android.text.Editable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by gordan on 10/24/15.
 */
public class PersonList {
    private static List<PersonModel> personList;

    public PersonList() {
        personList = new ArrayList<>();
    }

    public void addPerson(Editable name, Editable surname) {
        personList.add(new PersonModel(name.toString(), surname.toString()));
    }

    public PersonModel getPerson(int index) {
        return personList.get(index);
    }

    public int getSize() {
        return personList.size();
    }

    public void removePerson(UUID id) {
        Iterator<PersonModel> it = personList.iterator();
        while (it.hasNext()) {
            if (it.next().getID().equals(id)) {
                it.remove();
                return;
            }
        }
    }

    public static List<PersonModel> getPersonList(){
        return personList;
    }

    public void updatePerson(UUID id,String name, String surname){
        for(PersonModel person : personList){
            if(person.getID().equals(id)){
                person.setName(name);
                person.setSurname(surname);
            }
        }
    }

    public void sortByName(){
        Collections.sort(personList, new Comparator<PersonModel>() {
            @Override
            public int compare(PersonModel firstPerson, PersonModel secondPerson) {
                return firstPerson.getName().compareTo(secondPerson.getName());
            }
        });
    }

    public void sortBySurname(){
        Collections.sort(personList, new Comparator<PersonModel>() {
            @Override
            public int compare(PersonModel firstPerson, PersonModel secondPerson) {
                return firstPerson.getSurname().compareTo(secondPerson.getSurname());
            }
        });
    }
}
