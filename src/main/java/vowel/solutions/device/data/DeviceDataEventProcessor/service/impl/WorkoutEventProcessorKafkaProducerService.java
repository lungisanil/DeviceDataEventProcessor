package vowel.solutions.device.data.DeviceDataEventProcessor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.DeviceDataEventProcessor.exception.InternalServerErrorException;
import vowel.solutions.device.data.DeviceDataEventProcessor.service.KafkaProducerService;
import vowel.solutions.device.data.device.data.event.consumer.WorkoutEventProcessRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

@Service(value = "WorkoutProcessEventKafkaProducerService")
public class WorkoutEventProcessorKafkaProducerService implements KafkaProducerService<WorkoutEventPointsRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutEventProcessorKafkaProducerService.class);
    private final KafkaTemplate<String, WorkoutEventProcessRequest> kafkaTemplate;

    @Value("${spring.kafka.producer.topic.name}")
    private String producerTopic;

    @Autowired
    public WorkoutEventProcessorKafkaProducerService(KafkaTemplate<String, WorkoutEventProcessRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(WorkoutEventPointsRequest message) {
        LOGGER.info("Preparing to send the message to the topic : {}", producerTopic);

        Message<WorkoutEventPointsRequest> workoutMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, producerTopic)
                .build();

        try {
            this.kafkaTemplate.send(workoutMessage);
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Failed to send the message with workout id : %s to the topic",
                    workoutMessage.getPayload().getWorkoutEventProcessRequest().getWorkoutId()));
        }
        LOGGER.info(String.format("Message sent %s", message));
    }
}
