package com.company;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {

    private static final String ADDRESSBOOK_FILE_NAME = "addressBook.txt";
    static ArrayList<Contact> temppersons = new ArrayList<Contact>();
    static ArrayList<Contact> persons = new ArrayList<Contact>();
    public static Map<String, ArrayList<Contact>> addressBookList = new HashMap<String, ArrayList<Contact>>();
    static Scanner sc = new Scanner(System.in);
    public HashMap<String, ArrayList<Contact>> personByState;
    public HashMap<String, ArrayList<Contact>> personByCity;


    public static void addContacts() {
        String firstName, lastName, address, city, email, phoneNumber,state;
        int zip;
        System.out.println("Enter First Name: ");
        firstName = sc.next();
        System.out.println("Enter LastName: ");
        lastName = sc.next();
        System.out.println("Enter Address: ");
        address = sc.next();
        System.out.println("Enter city: ");
        city = sc.next();
        System.out.println("Enter state: ");
        state = sc.next();
        System.out.println("Enter zip: ");
        zip = sc.nextInt();
        System.out.println("Enter Phone Number: ");
        phoneNumber = sc.next();
        System.out.println("Enter Email Id: ");
        email = sc.next();
        //contact class object
        Contact contact = new Contact(firstName, lastName, address, city,state,zip,phoneNumber, email);
        temppersons.add(contact);
        List distelemenet= temppersons.stream().distinct().collect(Collectors.toList());
        persons = new ArrayList<Contact>(distelemenet);

        try {
            writeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeDataToCSV();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    // write data to CSV file
    public static void writeDataToCSV() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = Files.newBufferedWriter(Paths.get("C:\\Users\\hp\\IdeaProjects\\AddressBookService\\src\\main\\resources\\contact.csv"));) {
            StatefulBeanToCsvBuilder<Contact> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Contact> beanWriter = builder.build();
            beanWriter.write(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Read data from CSV file
    public void readDataFromCSV() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\hp\\IdeaProjects\\AddressBookService\\src\\main\\resources\\contact.csv"));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("First Name - " + nextRecord[3]);
                System.out.println("Last Name - " + nextRecord[4]);
                System.out.println("Address - " + nextRecord[0]);
                System.out.println("City - " + nextRecord[1]);
                System.out.println("State - " + nextRecord[6]);
                System.out.println("Email - " + nextRecord[2]);
                System.out.println("Phone - " + nextRecord[5]);
                System.out.println("Zip - " + nextRecord[7]);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
    public static void writeData() throws IOException {
        System.out.println(persons);

        StringBuffer contactBuffer = new StringBuffer();
        persons.forEach(contact -> {
            String contactDataString = contact.toString().concat("\n");
            System.out.println(contactDataString);
            contactBuffer.append(contactDataString);
        });
        try {
            Files.write(Paths.get(ADDRESSBOOK_FILE_NAME), contactBuffer.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readData() {
        try {
            Files.lines(new File("addressBook.txt").toPath()).map(String::trim).forEach(System.out::println);

        } catch (IOException ignored) {

        }
    }


    public static void editContact() {
        System.out.println("Enter First Name: ");
        String checkFirstName = sc.next();      // Take Name From User To Check
        boolean flag = false;
        for (Contact contact : persons) {

            if (contact.firstName.equals(checkFirstName)) //check Name Equals
            {

                flag = true;
                System.out.println("1. First Name\n" + "2.Last Name\n" + "3.Address\n" + "4.city\n" + "5.zip\n" + "6.phoneNumber\n" + "7.email");
                int choice = sc.nextInt(); //take choice for edit

                switch (choice) {

                    case 1:
                        System.out.println("Enter First Name : ");
                        String firstName = sc.next();
                        contact.firstName = firstName;
                        break;
                    case 2:
                        System.out.println("Enter First Name : ");
                        String lastName = sc.next();
                        contact.lastName = lastName;
                        break;
                    case 3:
                        System.out.println("Enter Address : ");
                        String address = sc.next();
                        contact.address = address;
                        break;
                    case 4:
                        System.out.println("Enter city : ");
                        String city = sc.next();
                        contact.city = city;
                        break;
                    case 5:
                        System.out.println("Enter zip : ");
                        int zip = sc.nextInt();
                        contact.zip = zip;
                        break;
                    case 6:
                        System.out.println("Enter phone Number : ");
                        String phoneNumber = sc.next();
                        contact.phoneNumber = phoneNumber;
                        break;
                    case 7:
                        System.out.println("Enter email : ");
                        String email = sc.next();
                        contact.email = email;
                        break;
                    default:
                        System.out.println("invalid choice");
                }
                break;
            }
        }
        if (flag == false) {
            System.out.println(checkFirstName + " Not Found!");
        }

    }

    public static void deleteContact() {

        System.out.println("Enter First Name: ");
        String checkFirstName = sc.next();      // Take Name From User To Check
        boolean flag = false;
        for (Contact contact : persons) {

            if (contact.firstName.equals(checkFirstName)) //check Name Equals
            {
                flag = true;
                persons.remove(contact);   //delete contact
                break;
            }
        }
        if (flag == false) {
            System.out.println(checkFirstName + " is Not Found");
        }
    }

    public static void displayContacts() // display Contacts
    {
        int i = 1;
        for (Contact contact : persons) {
            System.out.print("Contact: " + i);
            System.out.print("\tFirst Name : " + contact.firstName);
            System.out.print("\tLast Name : " + contact.lastName);
            System.out.print("\tAddress : " + contact.address);
            System.out.print("\tCity : " + contact.city);
            System.out.print("\tstate : " + contact.state);
            System.out.print("\tzip : " + contact.zip);
            System.out.print("\tPhoane Number : " + contact.phoneNumber);
            System.out.print("\temail : " + contact.email + "\n");
            i++;
        }
    }

    public void getPersonNameByCity(String city) {
        List<Contact> list  = persons.stream().filter(p ->p.city.equals(city)).collect(Collectors.toList());
        for(Contact contact: list){
            System.out.println("First Name: "+contact.firstName);
            System.out.println("Last Name: "+contact.lastName);
        }
    }

    public void getPersonNameByState(String state) {
        List<Contact> list  = persons.stream().filter(p ->p.city.equals(state)).collect(Collectors.toList());
        for(Contact contact: list){
            System.out.println("First Name: "+contact.firstName);
            System.out.println("Last Name: "+contact.lastName);
        }
    }
    }

