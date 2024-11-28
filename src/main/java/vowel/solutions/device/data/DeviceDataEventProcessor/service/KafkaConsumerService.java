package vowel.solutions.device.data.DeviceDataEventProcessor.service;

public interface KafkaConsumerService<M> {
    void readMessage(M message);
}
