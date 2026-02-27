package com.xpeho.spring_boot_java_random_user.data.converters;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.data.models.db.User;
import org.springframework.stereotype.Service;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;


@Service
public class UserConverter {
    // Domain -> DAO
    public User toDao(UserEntity entity) {
        User user = new User();
        user.setId(entity.id());
        user.setFirstName(entity.firstname());
        user.setLastName(entity.lastname());
        user.setEmail(entity.email());
        user.setPictureUrl(entity.picture());
        return user;
    }
    // DAO -> Domain
    public UserEntity toDomain(User user) {
        return new UserEntity(
            user.getId(),
            // gender
            null, 
            user.getFirstName(),
            user.getLastName(),
            // civility
            null, 
            user.getEmail(),
            // phone
            null, 
            user.getPictureUrl(),
            // nat
            null  
        );
    }
    // API -> Domain
    public UserEntity fromApiModel(RandomUserResultDAO model) {
        String firstName = model.getName() != null ? model.getName().getFirst() : null;
        String lastName = model.getName() != null ? model.getName().getLast() : null;
        String civility = model.getName() != null ? model.getName().getTitle() : null;
        String picture = model.getPicture() != null ? model.getPicture().getMedium() : null;
        return new UserEntity(
            null,
            model.getGender(),
            firstName,
            lastName,
            civility,
            model.getEmail(),
            model.getPhone(),
            picture,
            model.getNat()
        );
    }

    // Domain -> DTO
    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(
            entity.id(),
            entity.gender(),
            entity.firstname(),
            entity.lastname(),
            entity.civility(),
            entity.email(),
            entity.phone(),
            entity.picture(),
            entity.nat()
        );
    }
}
