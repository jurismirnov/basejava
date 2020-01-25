package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainTestSqlStorage {

    public static void main(String[] args) {
        Properties props = new Properties();
        Storage sqlStorage = new SqlStorage(props.getProperty("dbUrl"), props.getProperty("dbUser"), props.getProperty("dbPassword"));
        Storage storage ;//= new SqlStorage;(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
        final String UUID1 = "uuid1";
        final String UUID2 = "uuid2";
        final String UUID3 = "uuid3";
        final String UUID4 = "uuid4";
        final String DUMMY = "dummy";
        final String FULL_NAME1 = "Sacha Sidorova";
        final String FULL_NAME2 = "Maria Vasina";
        final String FULL_NAME3 = "Boris Petrov";
        final String FULL_NAME4 = "Anton Ivanov";
        final Resume R1;
        final Resume R2;
        final Resume R3;
        final Resume R4;


        R1 = new Resume(UUID1, FULL_NAME1);
        R2 = new Resume(UUID2, FULL_NAME2);
        R3 = new Resume(UUID3, FULL_NAME3);
        R4 = new Resume(UUID4, FULL_NAME4);

        R1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONENR, "11111");
        R2.addContact(ContactType.EMAIL, "mail2@ya.ru");
        R2.addContact(ContactType.PHONENR, "22222");
        R3.addContact(ContactType.EMAIL, "mail3@ya.ru");
        R3.addContact(ContactType.PHONENR, "33333");
        R4.addContact(ContactType.EMAIL, "mail4@ya.ru");
        R4.addContact(ContactType.PHONENR, "44444");

        //storage.clear();
       // Resume newResume = new Resume("uuid_save", "test");
        //storage.save(R1);
        //storage.save(R2);
        //storage.save(R3);
        //storage.save(R4);
        //Resume gotResume = storage.get("uuid1");
        //System.out.println(gotResume);

       //List<Resume> allResumeList = storage.getAllSorted();
        //System.out.println(allResumeList);

        //System.out.println(getResumeTable());
        //response.getWriter().write(htmlStart + table + htmlEnd);
    }

}

