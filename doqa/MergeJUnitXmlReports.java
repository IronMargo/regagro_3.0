import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class MergeJUnitXmlReports {

    public static void main(String[] args) {
        String reportsDirectory = "./doqa/xml_FOR_report"; // Путь, откуда берутся файлы для объединения
        String outputFilename = "./doqa/finished_reports/merged_test_report.xml"; // Путь куда сохраняется сформированный объединенный отчет
        mergeJUnitXmlReports(reportsDirectory, outputFilename);
    }

    public static void mergeJUnitXmlReports(String reportsDir, String outputFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document mergedDocument = builder.newDocument();
            Element mergedTestSuite = mergedDocument.createElement("testsuite");
            mergedDocument.appendChild(mergedTestSuite);

            int totalTests = 0;
            int totalErrors = 0;
            int totalFailures = 0;
            int totalSkipped = 0;
            double totalTime = 0.0;

            File dir = new File(reportsDir);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    if (child.isFile() && child.getName().endsWith(".xml")) {
                        Document doc = builder.parse(child);
                        Element testSuite = doc.getDocumentElement();

                        // Обработка каждого тестового случая
                        NodeList testCases = testSuite.getElementsByTagName("testcase");
                        for (int i = 0; i < testCases.getLength(); i++) {
                            Element testCase = (Element) testCases.item(i);

                            // Получение тегов
                            NodeList tagElements = testCase.getElementsByTagName("tag");
                            for (int j = 0; j < tagElements.getLength(); j++) {
                                Element tagElement = (Element) tagElements.item(j);
                                String tagName = tagElement.getAttribute("name");

                                // Добавление тега как атрибута testcase
                                testCase.setAttribute(tagName, "true");
                            }

                            // Добавление DisplayName как атрибута testcase
                            String displayName = testCase.getAttribute("name");
                            testCase.setAttribute("displayName", displayName);

                            mergedTestSuite.appendChild(mergedDocument.importNode(testCase, true));
                        }

                        totalTests += Integer.parseInt(testSuite.getAttribute("tests"));
                        totalErrors += Integer.parseInt(testSuite.getAttribute("errors"));
                        totalFailures += Integer.parseInt(testSuite.getAttribute("failures"));
                        totalSkipped += Integer.parseInt(testSuite.getAttribute("skipped"));
                        totalTime += Double.parseDouble(testSuite.getAttribute("time"));
                    }
                }
            }

            mergedTestSuite.setAttribute("tests", String.valueOf(totalTests));
            mergedTestSuite.setAttribute("errors", String.valueOf(totalErrors));
            mergedTestSuite.setAttribute("failures", String.valueOf(totalFailures));
            mergedTestSuite.setAttribute("skipped", String.valueOf(totalSkipped));
            mergedTestSuite.setAttribute("time", String.format("%.3f", totalTime));
            mergedTestSuite.setAttribute("name", "Merged Test Suite");

            // Сохранение объединенного отчета
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(mergedDocument);
            StreamResult result = new StreamResult(new File(outputFile));
            transformer.transform(source, result);
            System.out.println("Объединенный отчет сохранен в: " + outputFile);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
