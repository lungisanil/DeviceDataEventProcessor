package vowel.solutions.device.data.DeviceDataEventProcessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vowel.solutions.device.data.DeviceDataEventProcessor.entity.WorkoutEventEntity;

@Repository
public interface WorkoutEventProcessorRepository extends JpaRepository<WorkoutEventEntity, String> {
}
