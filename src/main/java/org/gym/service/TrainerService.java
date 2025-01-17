package org.gym.service;

import jakarta.validation.Valid;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

import java.util.List;

public interface TrainerService {

    /**
     * Creates a new Trainer.
     * <p>
     * This method accepts a {@link TrainerDto} object containing the details of the new Trainer and passes it to the service layer
     * to handle the creation of the Trainer.
     * <p>
     *
     * @param trainerDTO - an object containing the details of the new Trainer
     * @return The created {@link TrainerDto} with the assigned ID and relevant information
     */
    TrainerDto create(@Valid TrainerDto trainerDTO);

    /**
     * Retrieves a Trainer by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainerDto} of the Trainer with the given ID.
     * <p>
     *
     * @param username - the unique Username of the Trainer to be retrieved
     * @return The {@link TrainerDto} containing the Trainer's information
     * @throws EntityNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto select(String username) throws EntityNotFoundException;

    /**
     * Updates an existing Trainer.
     * <p>
     * This method updates the details of the Trainer identified by the given ID with the provided {@link TrainerDto}.
     * <p>
     *
     * @param username   - the unique Username of the Trainer to be updated
     * @param trainerDto - an object containing the updated information for the Trainer
     * @return The updated {@link TrainerDto} of the Trainer
     * @throws EntityNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto update(String username, @Valid TrainerDto trainerDto) throws EntityNotFoundException;

    /**
     * Authenticates a Trainer based on their credentials.
     * <p>
     * This method checks if the provided password for the given Trainer matches the one stored in the system.
     * It first retrieves the Trainer by their username, and then compares the provided password
     * with the stored password using a password encoder.
     * <p>
     *
     * @param username The unique Username of the trainer.
     * @param password Trainer's password
     * @return A boolean indicating whether the provided password matches the stored password. Returns `true` if they match, otherwise `false`.
     */
    boolean authenticateTrainer(String username, String password);

    /**
     * Changes the active status of a Trainer.
     * <p>
     * This method updates the "isActive" status of a Trainer's account based on the provided username.
     * It first retrieves the Trainer using the username, then modifies the status to either active or inactive.
     * The updated Trainer entity is saved in the repository.
     * <p>
     *
     * @param username The unique username of the Trainer whose status is to be updated.
     * @param isActive A Boolean indicating the new status of the Trainer. `true` for active, `false` for inactive.
     */
    void changeStatus(String username, Boolean isActive);

    /**
     * Changes the password of a Trainer.
     * <p>
     * This method updates the password for the given Trainer. The new password is set in the TrainerDto object
     * and saved in the repository after converting the DTO back to an entity.
     * <p>
     *
     * @param username     Unique Trainer's username
     * @param lastPassword The last password that was set for Trainer.
     * @param newPassword  The new password to be set for the Trainer.
     */
    void changePassword(String username, String lastPassword, String newPassword);

    /**
     * Retrieves a list of Trainers who are not assigned to the given Trainee.
     * <p>
     * This method queries the system for all Trainers and filters out those who are already assigned to the Trainee
     * based on the provided Trainee username. The result is a list of Trainers who are available to be assigned to the Trainee.
     * <p>
     *
     * @param traineeUsername The unique username of the Trainee whose unassigned Trainers are to be retrieved.
     * @return A list of {@link TrainerDto} objects representing Trainers who are not assigned to the Trainee.
     * @throws EntityNotFoundException if the Trainee with the provided username is not found.
     */
    List<TrainerDto> getUnassignedTrainersList(String traineeUsername);

    /**
     * Updates the list of Trainers assigned to a specific Trainee.
     * <p>
     * This method updates the list of Trainers assigned to a Trainee based on the provided list of Trainer usernames.
     * It first retrieves the Trainee by username, then finds the Trainers by their usernames and associates them with the Trainee.
     * The updated list of Trainers is saved in the Trainee entity and persisted in the repository.
     * <p>
     *
     * @param traineeUsername   The unique username of the Trainee whose Trainers list is to be updated.
     * @param trainersUsernames A list of usernames of the Trainers to be assigned to the Trainee.
     * @return A list of {@link TrainerDto} objects representing the updated Trainers assigned to the Trainee.
     * @throws EntityNotFoundException if the Trainee or any Trainer is not found.
     */
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames);
}
