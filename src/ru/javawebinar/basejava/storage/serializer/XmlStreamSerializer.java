package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Organisation;
import ru.javawebinar.basejava.model.OrganisationListSection;
import ru.javawebinar.basejava.model.Position;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextListSection;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer(){
        xmlParser = new XmlParser(
          Resume.class, Organisation.class, OrganisationListSection.class, Position.class, TextListSection.class, TextSection.class
        );
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException{
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(resume, writer);
        }
    }
    @Override
    public Resume doRead(InputStream is) throws IOException{
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(reader);
        }
    }

}
