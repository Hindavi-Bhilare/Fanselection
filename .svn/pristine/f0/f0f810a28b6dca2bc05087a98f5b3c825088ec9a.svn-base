package com.velotech.fanselection.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser
{
    

    public Map<String, List<Double>> getDataFromPerfChart(String fileName)
    {
        Map<Integer, String> impellerDiaMap = new HashMap<Integer, String>();
        Map<String, List<Double>> impellerDataMap = new HashMap<String, List<Double>>();
       
        try
        {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            if (file.exists())
            {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();

                NodeList nodeList = docEle.getElementsByTagName("Cell");
                if (nodeList != null && nodeList.getLength() > 0)
                {
                    for (int i = 0; i < nodeList.getLength(); i++)
                    {
                        Node node = nodeList.item(i);
                        if (!node.getParentNode().hasAttributes() && node.getChildNodes().item(0).getAttributes().item(0).getNodeValue().equals("String"))
                        {
                            impellerDiaMap.put(Integer.parseInt(node.getAttributes().item(0).getNodeValue()), node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
                        }

                        if (node.getAttributes().item(node.getAttributes().getLength() - 1).getNodeName().equals("ss:StyleID"))
                        {
                            Integer tempIndex = 0;
                            List<Double> temp = null;
                            tempIndex = Integer.parseInt(node.getAttributes().item(0).getNodeValue());
                            if (impellerDataMap.containsKey(impellerDiaMap.get(tempIndex)))
                            {
                                temp = impellerDataMap.get(impellerDiaMap.get(tempIndex));
                            }
                            else
                            {
                                temp = new ArrayList<Double>();
                            }

                            temp.add(Double.parseDouble(node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue())); // name
                            node = nodeList.item(++i);                                                                                                  // of

                            temp.add(Double.parseDouble(node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue()));

                            impellerDataMap.put(impellerDiaMap.get(tempIndex), temp);                                                                                               // node

                        }
                    }
                }
                else
                {
                    System.exit(1);
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return impellerDataMap;

    }

    public String getPumModel(String fileName)
    {
        String pumpModel = null;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);

            if (file.exists())
            {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
                // name of the pump
                pumpModel = docEle.getElementsByTagName("Worksheet").item(0).getAttributes().item(0).getNodeValue().split(".jpg")[0];

            }
            else
            {
                System.exit(1);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return pumpModel;
    }

}
