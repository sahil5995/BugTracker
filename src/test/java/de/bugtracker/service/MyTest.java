package de.bugtracker.service;

import com.sun.source.tree.ContinueTree;

import javax.swing.text.MaskFormatter;
import java.util.*;

public class MyTest {
    static HashMap<Person, Double> map = new HashMap<>();


    public static void main(String[] args) {
        new MyTest();
        List<String> rejectedTx = new ArrayList<>();

        rejectedTx.add("John,Doe,john@doe.com,200.05,TR0001");
//        rejectedTx.add("John,Doe,john@doe.com,200,TR0002");
//        rejectedTx.add("John,Doe,john@doe.com,300,TR0003");
        System.out.println(findRejectedTransactions(rejectedTx,200));




    }

    public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {
        List<String> rejectedTx = new ArrayList<>();


        for(String tr :transactions)
        {

            String[] info =tr.split(",");
            String firstName=info[0];
            String lastname = info[1];
            String email = info[2];
            double amount = Double.valueOf(info[3]);
            String transaction = info[4];

            Person person = new Person(firstName,lastname,email, amount, transaction);
            if(map.containsKey(person)){

                if(map.get(person)>creditLimit){
                    continue;
                }

                Double total = map.get(person)+amount;
                if(total>creditLimit){
                    map.replace(person,total) ;
                    rejectedTx.add(transaction);
                    continue;
                }else{
                map.replace(person,total) ;}
            }else {
                if(amount>creditLimit){
                rejectedTx.add(transaction);
            }else{
                map.put(person, amount);}
            }
        }

        return rejectedTx;
    }



    static class Person {
        String name;
        String lastname;
        String email;
        double amount;
        String transaction;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getTransaction() {
            return transaction;
        }

        public void setTransaction(String transaction) {
            this.transaction = transaction;
        }

        public Person(String name, String lastname, String email, double amount, String transaction) {
            this.name = name;
            this.lastname = lastname;
            this.email = email;
            this.amount = amount;
            this.transaction = transaction;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Person))
                return false;
            Person other = (Person)o;
            return (this.email.equals(other.getEmail())) && (this.name.equals(other.getName())) && (this.lastname.equals(other.getLastname()));
            //return (this.amount==other.amount) && (this.lastname == other.lastname) && (this.email == other.email);
        }

        @Override
        public int hashCode()
        {
            return 1;
        }

    }




    private List<String> createClassicManu(){
        List<String>   list = new ArrayList<>();
        list.add("strawberry");
        list.add("banana");
        list.add("pineapple");
        list.add("mango");
        list.add("peach");
        list.add("honey");
        Collections.sort(list);
        return list;
    }

    private List<String> createFreezieManu(){
        List<String>   list = new ArrayList<>();
        list.add("blackberry");
        list.add("blueberry");
        list.add("black currant");
        list.add("grape");
        list.add("juice");
        list.add("yogurt");
        Collections.sort(list);
        return list;
    }

    private List<String> createGreenieManu(){
        List<String>   list = new ArrayList<>();
        list.add("green apple");
        list.add("lime");
        list.add("avocado");
        list.add("spinach");
        list.add("ice");
        list.add("apple juice");
        Collections.sort(list);
        return list;
    }


    private List<String> JustDesserts(){
        List<String>   list = new ArrayList<>();
        list.add("banana");
        list.add("ice cream");
        list.add("chocolate");
        list.add("peanut");
        list.add("cherry");

        Collections.sort(list);
        return list;
    }
    public void meth(String name){





        //Classic: strawberry, banana, pineapple, mango, peach, honey
    }
}
