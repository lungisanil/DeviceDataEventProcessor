package vowel.solutions.device.data.DeviceDataEventProcessor.translator;

import vowel.solutions.device.data.DeviceDataEventProcessor.entity.WorkoutEventEntity;
import vowel.solutions.device.data.DeviceDataEventProcessor.exception.InternalServerErrorException;
import vowel.solutions.device.data.device.data.event.consumer.WorkoutEventProcessRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsMnemonic;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

public class WorkoutEventProcessorTranslator {

    private WorkoutEventProcessorTranslator() {
        throw new InternalServerErrorException("You are a naughty developer, you shouldn't be creating objects of this class since " +
                "it only has static method(s)");
    }

    public static WorkoutEventPointsMnemonic getWorkoutEventPointsMnemonic(String mnemonic, String description, int pointsValue) {
        return new WorkoutEventPointsMnemonic()
                .setMnemonic(mnemonic)
                .setDescription(description)
                .setPointsValue(pointsValue);
    }

    public static WorkoutEventEntity getWorkoutEventEntity(WorkoutEventProcessRequest request) {
        return new WorkoutEventEntity()
                .setWorkoutId(request.getWorkoutId())
                .setWorkoutType(request.getWorkoutType())
                .setProcessed(request.getProcessed())
                .setDeviceName(request.getDeviceName())
                .setAverageHeartRate(request.getAverageHeartRate())
                .setAverageSpeed(request.getAverageSpeed())
                .setMemberId(request.getMemberId());
    }

    public static WorkoutEventPointsRequest getWorkoutEventToBeSentForPointsAllocation(WorkoutEventProcessRequest workoutEvent,
                                                                                       WorkoutEventPointsMnemonic workoutEventPointsMnemonic) {
        return new WorkoutEventPointsRequest()
                .setWorkoutEventProcessRequest(workoutEvent)
                .setWorkoutEventPointsMnemonic(workoutEventPointsMnemonic);
    }
}
