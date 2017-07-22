package quant.fin;


import org.javalite.http.Http;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EcbDao {

    private static final String ENDPOINT = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml?99b6166c995b24457806cab0d783ac92";
    private static final DocumentBuilderFactory DBFACTORY = DocumentBuilderFactory.newInstance();
    private static final DocumentBuilder DBUILDER;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    static {
        try {
            DBUILDER = DBFACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new AssertionError(e);
        }
    }


    public List<Rate> getRates(CurrencyPair currencyPair){

        String currency = currencyPair.toString().substring(4);
        List<Rate> rates = new ArrayList<>(90);

        NodeList nodeList = nodeList(restCall());
        LocalDate date = null;
        boolean parseRate = false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                String time = eElement.getAttribute("time");
                if(!time.isEmpty()){
                    date = LocalDate.parse(time, FORMAT);
                    parseRate = true;
                    continue;
                }

                if(parseRate && currency.equals(eElement.getAttribute("currency"))){
                    rates.add(new Rate(date, new BigDecimal(eElement.getAttribute("rate"))));
                    parseRate = false;
                }
            }
        }


        return rates;
    }

    private NodeList nodeList(InputStream inputStream){
        try {
            Document doc = DBUILDER.parse(inputStream);
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName("Cube");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream restCall(){
        return Http.get(ENDPOINT).getInputStream();
    }

}
