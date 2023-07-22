package com.evi.teamfindercore.mapper;


import com.evi.teamfindercore.domain.UserFriend;
import com.evi.teamfindercore.model.FriendDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true),injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class FriendMapper {


    public abstract FriendDTO mapUsersFriendsToFriendDTO(UserFriend userFriend);

}
