import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomReadNFNA2X {

    public static void main(String argv[]) {
        File xmlFile = new File("usersBFNA2X.xml");
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);

        } catch (SAXException | IOException | ParserConfigurationException e) {
        }

        doc.getDocumentElement().normalize();

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("user");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            System.out.println("\nCurrent Element: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                String uid = elem.getAttribute("id");

                Node node1 = elem.getElementsByTagName("firstname").item(0);
                String fname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("lastname").item(0);
                String lname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("profession").item(0);
                String pname = node3.getTextContent();

                System.out.println("User id: " + uid);
                System.out.println("Firstname: " + fname);
                System.out.println("Lastname: " + lname);
                System.out.println("Profession: " + pname);
            }
        }
    }
}
