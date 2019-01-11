package liu.cn.ilxj;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class XmlToJsonUtil {
    public static StringBuffer stringBuffer = new StringBuffer();
    public static void main(String[] args) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(new File("aa.xml"));
        Element rootElement = read.getRootElement();
        stringBuffer.append("{");
        stringBuffer.append(rootElement.getName() + ":{");
        readXml(rootElement);
        stringBuffer.append("}");
        stringBuffer.append("}");
        System.out.println(stringBuffer.toString());
    }
    public static void readXml(Element rootElement){
        List<Element> elements = rootElement.elements();
        for (int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String name = element.getName();
            stringBuffer.append(name + ":{");
            List<Attribute>attributes = element.attributes();
            if (attributes.size()>0){
                for (int j=0;j<attributes.size();j++){
                    Attribute attribute = attributes.get(j);
                    stringBuffer.append(attribute.getName()+":" + attribute.getValue());
                    if (j!=attributes.size()-1) stringBuffer.append(",");
                }
                List<Element> elements2 = element.elements();
                if (elements2.size()==0){
                    String text = element.getText();
                    if (text!=null && !"".equals(text)) stringBuffer.append(",value:"+text);
                }else {
                    if (elements.size()>0) stringBuffer.append(",");
                    readXml(element);
                }

            }else {
                if (element.elements().size()>0)readXml(element);
                else stringBuffer.append("value:" + element.getText());
            }
            stringBuffer.append("}");
            if (i!=elements.size()-1)stringBuffer.append(",");
        }
    }
}
