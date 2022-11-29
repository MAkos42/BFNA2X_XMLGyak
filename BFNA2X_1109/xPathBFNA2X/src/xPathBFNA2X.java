import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import javax.xml.xpath.*;

public class xPathBFNA2X {
    public static void main(String[] args) throws Exception {

        File xmlFile = new File("BFNA2X_1109/studentBFNA2X.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        XPath xpath = XPathFactory.newInstance().newXPath();

        String expression1 = "/class/student";
/*         String expression2 = "/class/student[@id=02]";
        String expression3 = "//student";        
        String expression4 = "/class/student[2]";        
        String expression5 = "/class/student[last()]";        
        String expression6 = "/class/student[last()-1]";        
        String expression7 = "/class/student[1] | /class/student[2]";        
        String expression8 = "/class/node()";
        String expression9 = "//student[@*]";        
        String expression10 = "//node()";        
        String expression11 = "/class/student[kor>20]";
        String expression12 = "//student/keresztnev | //student/vezeteknev"; */
        


        NodeList nodelist = (NodeList) xpath.compile(expression1).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist.getLength(); i++) {

            Node node = nodelist.item(i);

            System.out.println("Aktuális elem: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Hallgató ID: " + element.getAttribute("id"));
                System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Becenév: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("Életkor: " + element.getElementsByTagName("kor").item(0).getTextContent());
            }
        }
    }

}
