package com.example.exxtest.serializer;


import com.example.exxtest.vo.EmployeeVO;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;


import java.io.*;
import java.util.Map;

public class KafkaMessageSerilizer implements Closeable,AutoCloseable,Serializer<EmployeeVO>, Deserializer<EmployeeVO> {

    @Override
    public EmployeeVO deserialize(String topic, byte[] data) {
       EmployeeVO object = null;
        try(ByteArrayInputStream b = new ByteArrayInputStream(data)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                object =  (EmployeeVO) o.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        return object;
    }

    @Override
    public EmployeeVO deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, EmployeeVO data) {
        byte[] output = null;
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(data);
            }
            output= b.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, EmployeeVO data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
