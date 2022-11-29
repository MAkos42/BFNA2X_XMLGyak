import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomModifyNFNA2X {

    public static void main(String argv[]) {
        File xmlFile = new File("BFNA2X_1109/carsBFNA2X.xml");
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);

        } catch (SAXException | IOException | ParserConfigurationException e) {
        }

        doc.getDocumentElement().normalize();

        // Node cars = doc.getFirstChild();

        Node supercar = doc.getElementsByTagName("supercars").item(0);

        NamedNodeMap attr = supercar.getAttributes();
        Node nodeAttr = attr.getNamedItem("company");
        nodeAttr.setTextContent("Lamborghini");

        NodeList list = supercar.getChildNodes();
        for(int i = 0; i < list.getLength(); i++){
            // Node node = list.item(i);
        }
    }
}