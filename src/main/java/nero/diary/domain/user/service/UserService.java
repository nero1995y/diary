package nero.diary.domain.user.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.UserResponseDto;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.dto.UserUpdateRequestDto;
import nero.diary.domain.user.dto.UsersResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.AlreadyUserException;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(UserSaveRequestDto requestDto) {
        verifyUsername(requestDto.getUsername());

        userRepository.save(requestDto.toEntity());
    }

    public void verifyUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {throw new AlreadyUserException();
                });

    }

    public UsersResponseDto findAll() {
        return UsersResponseDto.of(userRepository.findAll());
    }

    public UserResponseDto findUser(String username) {
        User user = getFindByUsername(username);
        return new UserResponseDto(user);
    }

    @Transactional
    public void update(String username, UserUpdateRequestDto requestDto) {

        User user = getFindByUsername(username);
        user.update(requestDto.toEntity());

    }

    @Transactional
    public void remove(String username) {
        User findByUsername = getFindByUsername(username);
        userRepository.deleteById(findByUsername.getId());
    }



    public User getFindByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(UserNotFoundException::new);
    }

}
