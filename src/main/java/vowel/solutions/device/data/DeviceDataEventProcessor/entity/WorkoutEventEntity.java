package vowel.solutions.device.data.DeviceDataEventProcessor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WORKOUT_EVENT")
public class WorkoutEventEntity {
    @Id
    @Column(name = "WORKOUT_ID", updatable = false, nullable = false)
    private String workoutId;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "WORKOUT_TYPE", nullable = false)
    private String workoutType;

    @Column(name = "DEVICE_NAME", nullable = false)
    private String deviceName;

    @Column(name = "AVERAGE_SPEED", nullable = false)
    private Float averageSpeed;

    @Column(name = "AVERAGE_HEART_RATE", nullable = false)
    private Float averageHeartRate;

    @Column(name = "PROCESSED", nullable = false)
    private Integer processed;

    public String getWorkoutId() {
        return workoutId;
    }

    public WorkoutEventEntity setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public WorkoutEventEntity setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public WorkoutEventEntity setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public WorkoutEventEntity setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public Float getAverageSpeed() {
        return averageSpeed;
    }

    public WorkoutEventEntity setAverageSpeed(Float averageSpeed) {
        this.averageSpeed = averageSpeed;
        return this;
    }

    public Float getAverageHeartRate() {
        return averageHeartRate;
    }

    public WorkoutEventEntity setAverageHeartRate(Float averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
        return this;
    }

    public Integer getProcessed() {
        return processed;
    }

    public WorkoutEventEntity setProcessed(Integer processed) {
        this.processed = processed;
        return this;
    }

    @Override
    public String toString() {
        return "WorkoutEventEntity{" +
                "memberId=" + memberId +
                ", workoutType='" + workoutType + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", averageSpeed=" + averageSpeed +
                ", averageHeartRate=" + averageHeartRate +
                ", workoutId='" + workoutId + '\'' +
                ", processed=" + processed +
                '}';
    }
}