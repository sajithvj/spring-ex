package com.example.exxtest.service;

import com.example.exxtest.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaService {
    private final KafkaTemplate<String, EmployeeVO> kafkaTemplate;
    private List<EmployeeVO> employeeVOList;


    @Autowired
    public KafkaService(KafkaTemplate<String, EmployeeVO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;

    }
    String kafkaTopic = "java_in_use_topic";
    public void send(EmployeeVO message){
        kafkaTemplate.send(kafkaTopic,message);
    }

    @KafkaListener(topics = "${kafka.topic.name}",
            groupId = "${general.topic.group.id}")
    public void consume(String message) {
        System.out.println("Received Message: " + message );
    }

    @KafkaListener(topics = "${kafka.topic.name}",
            groupId = "${user.topic.group.id}",
            containerFactory = "userKafkaListenerContainerFactory")
    public void consume(EmployeeVO employeeVO) {
        System.out.println("Received Message: " +employeeVO.toString() );
        employeeVOList = new ArrayList<>(10);
        employeeVOList.add(employeeVO);
    }

    public List<EmployeeVO> getEmployeeVOList() {
        return employeeVOList;
    }
}
