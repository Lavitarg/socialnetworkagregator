package service.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import service.models.Item;
import service.repo.ItemsRepo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class Parse {
    public static void parseFile() throws IOException, SAXException, ParserConfigurationException {
        ItemsRepo itemsRepo = new ItemsRepo();
        File xmlFile = new File("src/main/resources/templates/file.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        Element element = document.getDocumentElement();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("item");
        String show_title = "", show_tweet = "",show_data="";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeList1 = node.getChildNodes();
            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node node1 = nodeList1.item(j);
                if (node1.getNodeName() == "title") {
                    show_title = node1.getTextContent();
                }
                if (node1.getNodeName() == "pubDate") {
                    show_data = node1.getTextContent().substring(0,26);
                }
                if (node1.getNodeName() == "description") {
                    String line = node1.getTextContent();
                    while(line.contains("72x72")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(line);
                        int indexOf_image = stringBuilder.indexOf("72x72");
                        stringBuilder.setCharAt(indexOf_image, '6');
                        stringBuilder.setCharAt(indexOf_image + 1, '2');
                        stringBuilder.setCharAt(indexOf_image + 2, 'x');
                        stringBuilder.setCharAt(indexOf_image + 3, '6');
                        stringBuilder.setCharAt(indexOf_image + 4, '2');
                        line = stringBuilder.toString();
                    }
                    show_tweet = line;
                }
            }
            itemsRepo.addItems(new Item(show_title, show_tweet, show_data));
        }
    }
}

