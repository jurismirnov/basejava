package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Organisation;
import ru.javawebinar.basejava.model.OrganisationListSection;
import ru.javawebinar.basejava.model.Position;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextListSection;
import ru.javawebinar.basejava.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            writeList(dos, resume.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                String typeString = type.toString();
                if (typeString.equals("PERSONAL") || typeString.equals("OBJECTIVE")) {
                    dos.writeUTF(((TextSection) section).getText());
                } else if (typeString.equals("ACHIEVEMENT") || typeString.equals("QUALIFICATIONS")) {
                    writeList(dos, ((TextListSection) section).getRecords(), dos::writeUTF);
                } else if (typeString.equals("EXPERIENCE") || typeString.equals("EDUCATION")) {
                    writeList(dos, ((OrganisationListSection) section).getOrganisationList(), organisation -> {
                        dos.writeUTF(organisation.getFirmName());
                        dos.writeUTF(organisation.getHttpLink());
                        writeList(dos, organisation.getPositionList(), position -> {
                            dos.writeInt(position.getStartDate().getYear());
                            dos.writeInt(position.getStartDate().getMonth().getValue());
                            dos.writeInt(position.getEndDate().getYear());
                            dos.writeInt(position.getEndDate().getMonth().getValue());
                            dos.writeUTF(position.getPosition());
                            dos.writeUTF(position.getDescription());
                        });
                    });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            readRecords(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private <T> void writeList(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        String typeString = type.toString();
        if (typeString.equals("PERSONAL") || typeString.equals("OBJECTIVE")) {
            return new TextSection(dis.readUTF());
        } else if (typeString.equals("ACHIEVEMENT") || typeString.equals("QUALIFICATIONS")) {
            return new TextListSection(readList(dis, dis::readUTF));
        } else if (typeString.equals("EXPERIENCE") || typeString.equals("EDUCATION")) {
            return new OrganisationListSection(
                    readList(dis, () -> new Organisation(
                            dis.readUTF(), dis.readUTF(),
                            readList(dis, () -> new Position(
                                    LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                    LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                    dis.readUTF(), dis.readUTF()
                            ))
                    )));
        } else{
            return null;
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private void readRecords(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }
}
