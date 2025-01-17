package org.gym.facade;

import org.gym.dto.TraineeDto;

public interface TraineeFacade {

    TraineeDto createTrainee(TraineeDto traineeDto);
    TraineeDto getTraineeByUsername(String username) throws EntityNotFoundException;
    TraineeDto updateTrainee(String username, TraineeDto traineeDto) throws EntityNotFoundException;

    /**
     * Deletes a trainee's record by their unique ID.
     * <p>
     * This method deletes the trainee from the system based on the provided ID.
     * It ensures that the record is removed from the database, and handles any necessary validation.
     * <p>
     * @param username The unique Username of the trainee to be deleted.
     */
    void deleteTrainee(String username) throws EntityNotFoundException;

    /**
     * Changes the status (active or inactive) for a trainee.
     * <p>
     * @param username The unique Username of the trainee.
     * @param isActive A Boolean value indicating whether the trainee is active.
     */
    void changeTraineeStatus(String username, Boolean isActive);

    /**
     * Authenticates a trainee using their credentials.
     * <p>
     * @param username The unique Username of the trainee.
     * @param password Trainee's password
     * @return A Boolean value indicating whether the authentication was successful.
     */
    boolean authenticateTrainee(String username, String password);

    /**
     * Changes the password for a trainee.
     * <p>
     * @param username     Unique Trainee's username
     * @param lastPassword The last password that was set for Trainee.
     * @param newPassword  The new password to be set for the Trainee.
     */
    void changeTraineePassword(String username, String lastPassword, String newPassword);
}
