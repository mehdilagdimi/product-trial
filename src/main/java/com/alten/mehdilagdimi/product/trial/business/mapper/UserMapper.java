package com.alten.mehdilagdimi.product.trial.business.mapper;
import com.alten.mehdilagdimi.product.trial.business.util.HashUtil;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.domain.UserReq;
import com.alten.mehdilagdimi.product.trial.domain.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedByName = "toEncryptedPassw")
    UserAccount toEntity(UserReq userReq);
    UserResp toDtoResp(UserAccount userAccount);

    @Named("toEncryptedPassw")
    default String toEncryptedPassw(String password){
        return HashUtil.toEncryptedPassw(password);
    }

}
