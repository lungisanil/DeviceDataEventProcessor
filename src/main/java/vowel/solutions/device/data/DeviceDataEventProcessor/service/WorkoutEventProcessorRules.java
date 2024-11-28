package vowel.solutions.device.data.DeviceDataEventProcessor.service;

import vowel.solutions.device.data.device.data.event.consumer.WorkoutEventProcessRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsMnemonic;

public interface WorkoutEventProcessorRules {

    WorkoutEventPointsMnemonic process(WorkoutEventProcessRequest request);
}
