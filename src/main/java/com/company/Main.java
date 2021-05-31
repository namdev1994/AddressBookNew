package com.company;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static AddressBook addressBook = new AddressBook();
    static Scanner sc = new Scanner(System.in); //initializing scanner class
    public static Map<String, AddressBook> addressBookList = new HashMap<String, AddressBook>();

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book System");
        boolean flag = true;
        while (flag) {
            System.out.println("1: Add new address book\n2: search persons by city name\n3: searchPersonsByState" +
                    "\n4: search persons as per state using  java stream \n5: search persons as per city by using java stream\n6: no of persons count by state" +
                    "\n7: no of persons count by city\n8: sortByName as per Alphabetically\n9: sort by city\n10: sort by state\n11: sort by zipcode\n12:Exit");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the Name of the address book");
                    String addressBookName = sc.next();
                    if (addressBookList.containsKey(addressBookName)) {
                        System.out.println("This address book is already Present ");
                    } else {
                        addAddressBook(addressBookName);//storing address book in map
                        break;

                    }
                case 2:
                    System.out.println("Enter Name of City: ");
                    String cityName = sc.next();
                    Main.searchPersonByCity(cityName);
                    break;
                case 3:
                    System.out.println("Enter Name of State: ");
                    String stateName = sc.next();
                    Main.searchPersonByState(stateName);
                    break;
                case 4:
                    System.out.println("Enter Name of State: ");
                    String stateName1 = sc.next();
                    Main.viewPersonByStateUsingHashmap(stateName1);
                    break;
                case 5:
                    System.out.println("Enter Name of City: ");
                    String cityName1 = sc.next();
                    Main.viewPersonByCityUsingHashMap(cityName1);
                    break;
                case 6:
                    System.out.println("Enter Name of State: ");
                    String stateName2 = sc.next();
                    Main.CountByState(stateName2);
                    break;
                case 7:
                    System.out.println("Enter Name of City: ");
                    String cityName2 = sc.next();
                    Main.CountByCity(cityName2);
                    break;
                case 8:
                    System.out.println("Sort Contact");
                    Main.sortContactByName();
                    break;
                case 9:
                    Main.sortContactByCity();
                    break;

                case 10:
                    Main.sortContactByState();
                    break;

                case 11:
                    Main.sortContactByZip();
                    break;

                case 12:
                    flag = false;
                    break;
            }
        }
    }
     //sorted as per zip display contacts
    private static void sortContactByZip() {
        for (Map.Entry<String,AddressBook>entry:addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.persons.stream().sorted(Comparator.comparing(Contact::getZip)).collect(Collectors.toList());

            for(Contact contact:sortedList){
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }
    // //sorted as per state display contacts
    private static void sortContactByState() {
        for (Map.Entry<String,AddressBook>entry:addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.persons.stream().sorted(Comparator.comparing(Contact::getState)).collect(Collectors.toList());

            for(Contact contact:sortedList){
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }
     //sorted as per city display contacts
    private static void sortContactByCity() {
        for (Map.Entry<String,AddressBook>entry:addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.persons.stream().sorted(Comparator.comparing(Contact::getCity)).collect(Collectors.toList());

            for(Contact contact:sortedList){
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }

    // sort contact as per names Alphabetically
    private static void sortContactByName() {
        for (Map.Entry<String,AddressBook>entry:addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.persons.stream().sorted(Comparator.comparing(Contact::getFirstName)).collect(Collectors.toList());

            for(Contact contact:sortedList){
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }

    private static void CountByCity(String cityName2) {
        int countPersonInCity=0;
        for(Map.Entry<String, AddressBook> entry: addressBookList.entrySet())
        {
            for(int i=0;i<(entry.getValue()).persons.size();i++)
            {
                Contact contact= (Contact) entry.getValue().persons.get(i);

                if(cityName2.equals(contact.getCity()))
                {
                    countPersonInCity++;
                }

            }
        }
        System.out.println("Total number of people in this city "+cityName2+": "+countPersonInCity);
    }
    private static void viewPersonByCityUsingHashMap(String cityName1) // search person by city using java stream
    {
        for (Map.Entry<String, AddressBook> entry : addressBookList.entrySet()) {
            AddressBook value = entry.getValue();
            ArrayList<Contact> contacts = value.personByCity.entrySet().stream().filter(findCity -> findCity.getKey().equals(cityName1)).map(Map.Entry::getValue).findFirst().orElse(null);
            for(Contact contact: contacts){
                System.out.println("First Name: "+contact.getFirstName()+" Last Name: "+ contact.getLastName());
            }
        }
    }
    private static void viewPersonByStateUsingHashmap(String stateName1) //search persons by using java steram functions
    {
        for (Map.Entry<String, AddressBook> entry : addressBookList.entrySet()) {
            AddressBook value = entry.getValue();
            ArrayList<Contact> contacts = value.personByState.entrySet().stream().filter(findState -> findState.getKey().equals(stateName1)).map(Map.Entry::getValue).findFirst().orElse(null);
            for(Contact contact: contacts){
                System.out.println("First Name: "+contact.getFirstName()+" Last Name: "+ contact.getLastName());
            }
        }
    }
    private static void searchPersonByState(String stateName) //search persons by state
    {
        for(Map.Entry<String,AddressBook> entry: addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            System.out.println("The Address Book: "+entry.getKey());
            value.getPersonNameByState(stateName);
        }
    }
    private static void searchPersonByCity(String cityName) //search persons by city
    {
        for(Map.Entry<String,AddressBook> entry: addressBookList.entrySet()){
            AddressBook value = entry.getValue();
            System.out.println("The Address Book: "+entry.getKey());
            value.getPersonNameByCity(cityName);
        }
    }
    private static void CountByState(String stateName2) // count no of persons in State
    {
        int count = 0;
        for (Map.Entry<String, AddressBook> entry : addressBookList.entrySet()) {
            for (int i = 0; i < (entry.getValue()).persons.size(); i++) {
                Contact contact = entry.getValue().persons.get(i);

                if (stateName2.equals(contact.getAddress())) {
                    count++;
                }

            }
        }
        System.out.println("Total number of people in State "+stateName2+": "+count);
    }
    // add contacts in addressBook like add edit delete
    private static void addAddressBook(String addressBookName) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add\n2. Edit\n3. Delete\n4. Exit");
            int check;
            System.out.println("Enter Your Choice");
            check = sc.nextInt();
            switch (check) {
                case 1:
                    addressBook.addContacts();
                    addressBook.displayContacts();
                    break;
                case 2:
                    addressBook.editContact();
                    addressBook.displayContacts();
                    break;
                case 3:
                    addressBook.deleteContact();
                    addressBook.displayContacts();
                    break;
                case 4:
                    addressBook.displayContacts();
                    flag = false;
                    break;
                default:
                    System.out.println("Enter valid number");
                    break;
            }
        }
        addressBookList.put(addressBookName, addressBook);
        System.out.println("Address Book Added Successfully");
    }
}
