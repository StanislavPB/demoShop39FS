package org.demoshop39fs.repository;

import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.photoLink = :photoLink WHERE u.id = :userId")
    void updatePhotoLinkById(Integer userId, String photoLink);

    Optional<User> findByEmail(String email);

    List<User> findByLastName(String lastName);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(String email);

    Optional<User> findFirstByConfirmationCodes(ConfirmationCode code);
}
