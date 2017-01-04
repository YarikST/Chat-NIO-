package Server;


import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by admin-iorigins on 15.12.16.
 */

public class Grup {
    private static final Path PATH = Paths.get("");
    private Path path;
    private String name;

    private String member[];

    private String admin;

    public Grup(String name) {
        this.name = name;

        path = PATH.resolve(name);
        init();
    }

    private void init() {
        SAXBuilder parser = new SAXBuilder();
        FileReader fr = null;
        try {
             fr = new FileReader(PATH.resolve(Paths.get (name)).toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Document build = parser.build(fr);

            Element rootElement = build.getRootElement();

            List<Element> members = rootElement.getChild("members").getChildren();

            member = new String[members.size()];

            int i = 0;
            for (Element element : members) {
                member[i++] = element.getText();
            }

            Attribute admin1 = rootElement.getAttribute("admin");
            admin = admin1.getValue();

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Grup createGrup(String name, String admin, String... member) {


            Element rootElement = new Element("grup");
            Document doc = new Document(rootElement);

            rootElement.setAttribute("name", name);
            rootElement.setAttribute("admin", admin);

            Element grup = new Element("members");
            rootElement.addContent(grup);

            for (int i = 0; i < member.length; i++) {
                Element element = new Element("member");
                element.setText(member[i]);
                grup.addContent(element);
            }

                XMLOutputter outputter = new XMLOutputter();
                outputter.setFormat(Format.getPrettyFormat());
            FileWriter out = null;
            try {
                out = new FileWriter(PATH.resolve(Paths.get(name)).toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            outputter.output(doc, out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return new Grup(name);
    }

    public boolean isAdmin(String name) {
        return admin.equals(name);
    }

    public boolean isMember(String name) {
        for (String s : member) {
            if (s.equals(name)) {
                return true;
            }
        }

        return false;
    }
}
