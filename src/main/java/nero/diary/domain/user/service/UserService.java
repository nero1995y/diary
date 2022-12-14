package nero.diary.domain.user.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.UserResponseDto;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.dto.UsersResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.AlreadyUserException;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        User user = userRepository.findByUsername(username).
                orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(user);
    }


}
