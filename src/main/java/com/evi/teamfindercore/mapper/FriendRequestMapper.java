package com.evi.teamfindercore.mapper;

import com.evi.teamfindercore.domain.FriendRequest;
import com.evi.teamfindercore.model.FriendRequestDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class FriendRequestMapper {

    public abstract FriendRequestDTO mapFriendRequestToFriendRequestDTO(FriendRequest friendRequest);


}
