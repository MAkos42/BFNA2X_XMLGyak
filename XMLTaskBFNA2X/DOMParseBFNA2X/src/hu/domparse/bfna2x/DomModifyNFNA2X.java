package hu.domparse.bfna2x;

import java.io.File;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class DomModifyNFNA2X {

    public static void main(String argv[]) throws Exception {
        File xmlFile = new File("BFNA2X_1109/carsBFNA2X.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        // Node cars = doc.getFirstChild();

        Node supercar = doc.getElementsByTagName("supercars").item(0);

        NamedNodeMap attr = supercar.getAttributes();
        Node nodeAttr = attr.getNamedItem("company");
        nodeAttr.setTextContent("Lamborghini");

        NodeList list = supercar.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            // Node node = list.item(i);
        }
    }
}