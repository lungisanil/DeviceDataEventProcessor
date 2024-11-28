package vowel.solutions.device.data.DeviceDataEventProcessor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.DeviceDataEventProcessor.exception.InternalServerErrorException;
import vowel.solutions.device.data.DeviceDataEventProcessor.service.KafkaConsumerService;
import vowel.solutions.device.data.DeviceDataEventProcessor.service.KafkaProducerService;
import vowel.solutions.device.data.DeviceDataEventProcessor.service.WorkoutEventProcessorRules;
import vowel.solutions.device.data.DeviceDataEventProcessor.translator.WorkoutEventProcessorTranslator;
import vowel.solutions.device.data.device.data.event.consumer.WorkoutEventProcessRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsMnemonic;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

@Service
public class WorkoutEventProcessorKafkaConsumerService implements KafkaConsumerService<WorkoutEventProcessRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutEventProcessorKafkaConsumerService.class);

    @Value("${spring.kafka.producer.topic.name}")
    private String producerTopic;

    @Value("${spring.kafka.consumer.topic.name}")
    private String consumerTopic;

    private final WorkoutEventProcessorRules workoutEventProcessorRules;
    private final KafkaProducerService<WorkoutEventPointsRequest> kafkaProducerService;

    @Autowired
    public WorkoutEventProcessorKafkaConsumerService(WorkoutEventProcessorRules workoutEventProcessorRules,
                                                     KafkaProducerService<WorkoutEventPointsRequest> kafkaProducerService) {
        this.workoutEventProcessorRules = workoutEventProcessorRules;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void readMessage(WorkoutEventProcessRequest workoutEvent) {
        workoutEvent.setProcessed(1);
        LOGGER.info(String.format("Message received from " + consumerTopic + " -> %s", workoutEvent.toString()));

        WorkoutEventPointsMnemonic workoutEventPointsMnemonic = this.workoutEventProcessorRules.process(workoutEvent);

        LOGGER.info(String.format("Workout event updated on the DB and points calculated for the workout event -> %s", workoutEvent));

        WorkoutEventPointsRequest outgoingWorkoutEvent = WorkoutEventProcessorTranslator.getWorkoutEventToBeSentForPointsAllocation(workoutEvent, workoutEventPointsMnemonic);

        try {
            this.kafkaProducerService.sendMessage(outgoingWorkoutEvent);
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Cannot send the workout event to kafka topic : %s with id : %s",
                    producerTopic, workoutEvent.getWorkoutId()));
        }
        LOGGER.info(String.format("Workout event message with id : %s sent to topic : %s",
                outgoingWorkoutEvent.getWorkoutEventProcessRequest().getWorkoutId(), producerTopic));
    }
}
