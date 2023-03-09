package nero.diary.domain.user.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.dto.user.UserUpdateRequestDto;
import nero.diary.domain.user.dto.user.UsersResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.AlreadyUserException;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserSaveRequestDto requestDto) {
        verifyUsername(requestDto.getUsername());
        String encodePassword = getEncode(requestDto.getPassword());
        userRepository.save(requestDto.toEntity(encodePassword));
    }

    private String getEncode(String password) {
        return passwordEncoder.encode(password);
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

    public UserResponseDto findUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(user);
    }

    public UserResponseDto findUserByEmail(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(user);
    }



    @Transactional
    public void update(Long id, UserUpdateRequestDto requestDto) {

        User user = getFindByUserId(id);
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

    public User getFindByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

}
