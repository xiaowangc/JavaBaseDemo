package com.chige.utils.xml;


import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.internal.txw2.output.XmlSerializer;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author wangyc
 * @date 2023/4/19
 */
public class XmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * 把java类解析成XML字符串
     * java类需要包含@XmlRootElement注解
     * @param object java类
     */
    public static String beanToXmlString(Object object) {
        ByteArrayOutputStream dataStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息

            dataStream = new ByteArrayOutputStream();
            marshaller.marshal(object, dataStream);
            return dataStream.toString("UTF-8");// "GBK"
        } catch (Exception e) {
            LOGGER.error("Bean解析成XML字符串失败，原因：", e);
            return null;
        }finally {
            if (Objects.nonNull(dataStream)) {
                try {
                    dataStream.close();
                } catch (IOException e) {
                    LOGGER.error("输出流关闭异常，原因:", e);
                }
            }
        }
    }

    /**
     * 将xml字符串转换成bean对象
     */
    public static Object getBeanFromXml(String xml, Class<?> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            return unMarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            LOGGER.error("XML转换失败,原因: ", e);
            return null;
        }
    }

    /**
     * 将xml字符串转换成bean对象
     */
    public static <T> T xmlStrToBean(String xml, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            return (T)unMarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            LOGGER.error("XML转换失败,原因: ", e);
            return null;
        }
    }

    public static String xml2json(String xml) {
        if (StringUtils.isBlank(xml)){
            return "";
        }
        //读取Xml文件
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        //开启自动配置
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            //输入流事件
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String json = output.toString().replace("@", "").replace("#", "").replace("\"{", "{").replace("}\"", "}").replace("\\", "");
        LOGGER.info("XML转json："+json);

        return json;
    }


    public static void main(String[] args) throws IOException {
        User user = new User();
        user.setUserAge("19");
        user.setUserName("哈哈");
        user.setIdNo("");
        user.setList(Arrays.asList("1","2"));
        Child child = new Child();
        child.setName("孩子米鞥");
        user.setChild(child);
        String xmlString = beanToXmlString(user);
        System.out.println(xmlString);
        System.out.println(xml2json(xmlString));

        Car car = new Car();
        car.setBrand("广汽");
        car.setPrice("109.22");
        String s = beanToXmlString(car);
        System.out.println(s);
        Car car1 = xmlStrToBean(s, Car.class);
        System.out.println(car1.getBrand());
        System.out.println(car1.getPrice());

    }

}
