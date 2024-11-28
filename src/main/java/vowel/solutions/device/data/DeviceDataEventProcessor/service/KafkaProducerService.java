package vowel.solutions.device.data.DeviceDataEventProcessor.service;

public interface KafkaProducerService<M> {
    void sendMessage(M message);
}
