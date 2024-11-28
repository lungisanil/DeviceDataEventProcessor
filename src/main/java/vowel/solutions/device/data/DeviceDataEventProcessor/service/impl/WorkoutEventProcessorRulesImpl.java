package vowel.solutions.device.data.DeviceDataEventProcessor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.DeviceDataEventProcessor.entity.WorkoutEventEntity;
import vowel.solutions.device.data.DeviceDataEventProcessor.exception.InternalServerErrorException;
import vowel.solutions.device.data.DeviceDataEventProcessor.repository.WorkoutEventProcessorRepository;
import vowel.solutions.device.data.DeviceDataEventProcessor.service.WorkoutEventProcessorRules;
import vowel.solutions.device.data.DeviceDataEventProcessor.translator.WorkoutEventProcessorTranslator;
import vowel.solutions.device.data.device.data.event.consumer.WorkoutEventProcessRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsMnemonic;

@Service(value = "WorkoutEventProcessorRulesImpl")
public class WorkoutEventProcessorRulesImpl implements WorkoutEventProcessorRules {
    private final WorkoutEventProcessorRepository workoutEventProcessorRepository;

    @Autowired
    public WorkoutEventProcessorRulesImpl(WorkoutEventProcessorRepository workoutEventProcessorRepository) {
        this.workoutEventProcessorRepository = workoutEventProcessorRepository;
    }

    @Override
    public WorkoutEventPointsMnemonic process(WorkoutEventProcessRequest request) {
        WorkoutEventEntity workoutEventEntity = WorkoutEventProcessorTranslator.getWorkoutEventEntity(request);

        try {
            WorkoutEventPointsMnemonic workoutEventPointsMnemonic = this.calculateWorkoutEventPointsMnemonic(request);
            this.workoutEventProcessorRepository.save(workoutEventEntity);
            return workoutEventPointsMnemonic;
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Eroor occurred when trying to process workout with Id : %s", workoutEventEntity.getWorkoutId()));
        }
    }

    private WorkoutEventPointsMnemonic calculateWorkoutEventPointsMnemonic(WorkoutEventProcessRequest request) {
        Float averageSpeed = request.getAverageSpeed();
        Float averageHeartRate = request.getAverageHeartRate();

        if ((averageHeartRate > 0 && averageHeartRate <= 10) || (averageSpeed > 0 && averageSpeed <= 10)) {
            return WorkoutEventProcessorTranslator.getWorkoutEventPointsMnemonic("WORKOUT_LOW", "Chilled Workout", 50);
        } else if ((averageHeartRate > 10 && averageHeartRate <= 20) || (averageSpeed > 10 && averageSpeed <= 20)) {
            return WorkoutEventProcessorTranslator.getWorkoutEventPointsMnemonic("WORKOUT_MEDIUM", "Moderate Workout", 100);
        } else if (averageHeartRate > 20 || averageSpeed > 20) {
            return WorkoutEventProcessorTranslator.getWorkoutEventPointsMnemonic("WORKOUT_HIGH", "Intense Workout", 200);
        } else {
            return WorkoutEventProcessorTranslator.getWorkoutEventPointsMnemonic("WORKOUT_TOO_LOW", "Please continue to workout", 0);
        }
    }
}
