package hu.domparse.bfna2x;

import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class DomReadBFNA2X {

    public static void main(String argv[]) throws Exception {

        File xmlFile = new File("XMLTaskBFNA2X/XMLBFNA2X.xml"); // fájlbeolvasás
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        WriteAllChildren(doc.getChildNodes(), 0); // rekurzív olvasás megkezdése

        FileWriter wf = new FileWriter("XMLTaskBFNA2X/XMLBFNA2X.txt"); // output fájl létrehozása

        StringBuilder sb = new StringBuilder();

        WriteChildrenToSB(sb, doc.getChildNodes(), 0);

        wf.write(sb.toString());

        wf.close();

    }

    private static void WriteAllChildren(NodeList childNodes, int indent) {
        for (int i = 0; i < childNodes.getLength(); i++) { // minden gyerek node-on végig futva
            Node node = childNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) { // Element típusú node-ok kiválasztása
                Element element = (Element) node;

                NamedNodeMap attributes = element.getAttributes(); // attribútumok keresése

                indent(indent);
                if (attributes.getLength() > 0) // attribútumokkal rendelkező Element-ek kiírása
                    System.out.println("[" + element.getNodeName() + "] " + attributes.item(0).toString() + " BEGIN");
                else // Attribútum nélküli elemek kiírása
                    System.out.println("[" + element.getNodeName() + "] BEGIN");

                WriteAllChildren(element.getChildNodes(), indent + 1); // 1-el behúzva rekurzív meghívása

                indent(indent);
                System.out.println("[" + element.getNodeName() + "] END");
            }

            if (node.getNodeType() == Node.TEXT_NODE) { // szöveggel rendelkező node-ok tartalmának kiírása
                if (!node.getNodeValue().trim().isEmpty()) {
                    indent(indent);
                    System.out.println(node.getNodeValue());
                }
            }

        }
    }

    private static void WriteChildrenToSB(StringBuilder sb, NodeList childNodes, int indent) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                NamedNodeMap attributes = element.getAttributes();

                indent(sb, indent);
                if (attributes.getLength() > 0)
                    sb.append("[" + element.getNodeName() + "] " + attributes.item(0).toString() + " BEGIN\n");
                else // Attribútum nélküli elemek kiírása
                    sb.append("[" + element.getNodeName() + "] BEGIN\n");

                WriteChildrenToSB(sb, element.getChildNodes(), indent + 1); // 1-el behúzva rekurzív meghívása

                indent(sb, indent);
                sb.append("[" + element.getNodeName() + "] END\n");
            }

            if (node.getNodeType() == Node.TEXT_NODE) { // szöveggel rendelkező node-ok tartalmának kiírása
                if (!node.getNodeValue().trim().isEmpty()) {
                    indent(sb, indent);
                    sb.append(node.getNodeValue() + "\n");
                }
            }

        }
    }

    private static void indent(int indent) { // behúzás megvalósítása
        for (int k = 0; k < indent; k++) {
            System.out.print("    ");
        }
    }

    private static void indent(StringBuilder sb, int indent) { // behúzás megvalósítása
        for (int k = 0; k < indent; k++) {
            sb.append("    ");
        }
    }
}