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
        String firstName = model.name != null ? model.name.first : null;
        String lastName = model.name != null ? model.name.last : null;
        String civility = model.name != null ? model.name.title : null;
        String picture = model.picture != null ? model.picture.medium : null;
        return new UserEntity(
            null,
            model.gender,
            firstName,
            lastName,
            civility,
            model.email,
            model.phone,
            picture,
            model.nat
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
