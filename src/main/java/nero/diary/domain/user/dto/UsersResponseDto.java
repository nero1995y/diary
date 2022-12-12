package nero.diary.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponseDto {

    private List<UserResponseDto> userResponseDtoList;

    public static UsersResponseDto of (List<User> users) {
        return new UsersResponseDto(toResponse(users));
    }

    private static List<UserResponseDto> toResponse(List<User> users) {
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}
