package com.SimbirSoft.Practice.service.impl;
import com.SimbirSoft.Practice.dto.room.ConnectToRoomDto;
import com.SimbirSoft.Practice.dto.room.RoomNameDto;
import com.SimbirSoft.Practice.dto.room.RoomRenameDto;
import com.SimbirSoft.Practice.exception.ForbiddenActionExceptions;
import com.SimbirSoft.Practice.exception.NotFoundException;
import com.SimbirSoft.Practice.model.Room;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.model.enums.Role;
import com.SimbirSoft.Practice.repository.RoomRepo;
import org.apache.kafka.connect.errors.AlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl roomService;
    @Mock
    private RoomRepo roomRepository;

    private final RoomNameDto roomNameDto = new RoomNameDto();
    private RoomRenameDto roomRenameDto;
    private User user;
    private Room room;
    private final ConnectToRoomDto connectToRoomDto = new ConnectToRoomDto();


    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("QWERTY")
                .password("qwerty")
                .role(Role.ROLE_USER)
                .build();

        roomNameDto.setName("testRoom");
        roomRenameDto = new RoomRenameDto("testRoom", "definitelyTestRoom");
        connectToRoomDto.setRoomName("testRoom");
    }

    @Test
    void canCreateRoomWithAppropriateProperties() {
        //given
        Room expectedRoom = Room.builder()
                .name("testRoom")
                .owner(user)
                .participants(Collections.singleton(user))
                .build();
        //when
        doReturn(Optional.empty()).when(roomRepository).findByName(anyString());
        roomService.create(roomNameDto, user);

        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(expectedRoom);
    }
    @Test
    void throwsExceptionWhenSaveRoomAndThatNameAlreadyExists() {
        //given
        Room existingRoom = Room.builder()
                .name("testRoom")
                .build();
        //when
        doReturn(Optional.of(existingRoom)).when(roomRepository).findByName(anyString());
        //then
        assertThrows(AlreadyExistsException.class, () -> roomService.create(roomNameDto, user));
    }

    @Test
    void canDeleteRightRoom() {
        //given
        Room exceptedToDelete = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        //when
        doReturn(Optional.of(exceptedToDelete)).when(roomRepository).findByName(anyString());
        roomService.delete(roomNameDto, user);
        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).delete(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(exceptedToDelete);
    }
    @Test
    void throwsExceptionWhenDeletingRoom_AndRoomWithThatNameDoesNotExist() {
        //when
        doReturn(Optional.empty()).when(roomRepository).findByName(anyString());
        //then
        assertThrows(NotFoundException.class, () -> roomService.delete(roomNameDto, user));
    }
    @Test
    void throwsExceptionWhenDeletingRoom_AndUserIsNotOwner() {
        //given
        Room roomToDelete = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        User notOwnerUser = User.builder()
                .id(2L)
                .username("QWERTY")
                .password("qwerty")
                .role(Role.ROLE_USER)
                .build();
        //when
        doReturn(Optional.of(roomToDelete)).when(roomRepository).findByName(anyString());
        //then
        assertThrows(ForbiddenActionExceptions.class, () -> roomService.delete(roomNameDto, notOwnerUser));
    }
    @Test
    void canDeleteAnyRoomWhenUserIsAdmin() {
        //given
        Room roomToDelete = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        User userAdmin = User.builder()
                .id(2L)
                .username("ADMIN")
                .password("admin")
                .role(Role.ROLE_ADMIN)
                .build();
        //when
        doReturn(Optional.of(roomToDelete)).when(roomRepository).findByName(anyString());
        roomService.delete(roomNameDto, userAdmin);

        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).delete(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(roomToDelete);
    }

    @Test
    void canAppropriateRenameRoom() {
        //given
        Room roomWithOldName = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        Room expectedRoom = Room.builder()
                .name("definitelyTestRoom")
                .owner(user)
                .build();
        //when
        doReturn(Optional.of(roomWithOldName)).when(roomRepository).findByName(roomRenameDto.getOldName());
        doReturn(Optional.empty()).when(roomRepository).findByName(roomRenameDto.getNewName());
        roomService.rename(roomRenameDto, user);

        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(expectedRoom);
    }
    @Test
    void throwsExceptionWhenRoomToRenameIsNotPresent() {
        //when
        doReturn(Optional.empty()).when(roomRepository).findByName(roomRenameDto.getOldName());
        //then
        assertThrows(NotFoundException.class, () -> roomService.rename(roomRenameDto, user));
    }
    @Test
    void throwsExceptionWhenRenamingRoom_AndNewNameAlreadyExists() {
        //given
        Room roomWithOldName = Room.builder()
                .name("testRoom")
                .build();
        Room roomThatHaveNewName = Room.builder()
                .name("newName")
                .build();
        //when
        doReturn(Optional.of(roomWithOldName)).when(roomRepository).findByName(roomRenameDto.getOldName());
        doReturn(Optional.of(roomThatHaveNewName)).when(roomRepository).findByName(roomRenameDto.getNewName());
        //then
        assertThrows(AlreadyExistsException.class, () -> roomService.rename(roomRenameDto, user));
    }
    @Test
    void throwsExceptionWhenRenamingRoom_AndUserIsNotOwner() {
        //given
        Room roomToRename = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        User otherUser = User.builder()
                .username("QWERTY")
                .password("qwerty")
                .role(Role.ROLE_USER)
                .build();
        //when
        doReturn(Optional.of(roomToRename)).when(roomRepository).findByName(roomRenameDto.getOldName());
        doReturn(Optional.empty()).when(roomRepository).findByName(roomRenameDto.getNewName());
        //then
        assertThrows(ForbiddenActionExceptions.class, () -> roomService.rename(roomRenameDto, otherUser));
    }
    @Test
    void canRenameAnyRoomWhenUserIsAdmin() {
        //given
        Room roomToRename = Room.builder()
                .name("testRoom")
                .owner(user)
                .build();
        User userAdmin = User.builder()
                .id(2L)
                .username("ADMIN")
                .password("admin")
                .role(Role.ROLE_ADMIN)
                .build();
        Room expectedRoom = Room.builder()
                .name("definitelyTestRoom")
                .owner(user)
                .build();
        //when
        doReturn(Optional.of(roomToRename)).when(roomRepository).findByName(roomRenameDto.getOldName());
        doReturn(Optional.empty()).when(roomRepository).findByName(roomRenameDto.getNewName());
        roomService.rename(roomRenameDto, userAdmin);
        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(expectedRoom);

    }

    @Test
    void canAppropriateAddUserToRoom() {
        //given
        User userOwner = User.builder()
                .username("QWERTY")
                .password("qwerty")
                .build();
        Room roomToAddUser = Room.builder()
                .name("testRoom")
                .owner(userOwner)
                .participants(new HashSet<User>(){{add(userOwner);}})
                .build();
        Set<User> expectedSetOfUsers = new HashSet<>();
        expectedSetOfUsers.add(userOwner);
        expectedSetOfUsers.add(user);
        Room expectedRoom = Room.builder()
                .name("testRoom")
                .owner(userOwner)
                .participants(expectedSetOfUsers)
                .build();
        //then
        doReturn(Optional.of(roomToAddUser)).when(roomRepository).findByName(anyString());
        roomService.addUserToRoom(connectToRoomDto, user);
        //then
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(argumentCaptor.capture());
        Room capturedRoom = argumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(expectedRoom);
        assertThat(capturedRoom.getParticipants()).containsExactlyInAnyOrder(user, userOwner);

    }
    @Test
    void throwsExceptionWhenAddingUserToRoom_AndRoomIsNotPresent() {
        //when
        doReturn(Optional.empty()).when(roomRepository).findByName(anyString());
        //then
        assertThrows(NotFoundException.class, () -> roomService.addUserToRoom(connectToRoomDto, user));
    }
    @Test
    void throwsExceptionWhenAddingUserToRoom_AndUserIsAlreadyPresentInThatRoom() {
        //given
        User userOwner = User.builder()
                .username("QWERTY")
                .password("qwerty")
                .build();
        Set<User> existingUsers = new HashSet<>();
        existingUsers.add(userOwner);
        existingUsers.add(user);
        Room roomThatAlreadyHaveParticularUser = Room.builder()
                .name("testRoom")
                .owner(userOwner)
                .participants(existingUsers)
                .build();
        //when
        doReturn(Optional.of(roomThatAlreadyHaveParticularUser)).when(roomRepository).findByName(anyString());
        //then
        assertThrows(AlreadyExistsException.class, () -> roomService.addUserToRoom(connectToRoomDto, user));
    }
}