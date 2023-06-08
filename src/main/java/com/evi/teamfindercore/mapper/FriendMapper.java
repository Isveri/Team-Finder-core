package com.evi.teamfindercore.mapper;


import com.evi.teamfindercore.domain.Friend;
import com.evi.teamfindercore.model.FriendDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true),injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class FriendMapper {


    public abstract FriendDTO mapFriendToFriendDTO(Friend friend);

}
