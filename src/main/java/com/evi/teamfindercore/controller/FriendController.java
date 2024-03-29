package com.evi.teamfindercore.controller;

import com.evi.teamfindercore.model.FriendDTO;
import com.evi.teamfindercore.model.FriendRequestDTO;
import com.evi.teamfindercore.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/requests/{invitedUserId}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable Long invitedUserId) {
        friendService.sendFriendRequest(invitedUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestDTO>> getFriendRequests() {
        return ResponseEntity.ok(friendService.loadFriendRequests());
    }

    @PutMapping("/requests/accept/{requestId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long requestId) {
        friendService.acceptFriendRequest(requestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/requests/decline/{requestId}")
    public ResponseEntity<?> declineFriendRequest(@PathVariable Long requestId) {
        friendService.declineRequest(requestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FriendDTO>> getFriendList() {
        return ResponseEntity.ok(friendService.getFriendList());
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long friendId){
        friendService.removeFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public List<Long> removeAllFriends() {
        return friendService.removeAllFriends();
    }

    @PutMapping
    public ResponseEntity<Void> rollbackDelete(@RequestParam List<Long> removedIds) {
        friendService.rollbackFriendsDeletion(removedIds);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
