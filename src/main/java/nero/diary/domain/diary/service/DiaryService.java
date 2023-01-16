package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.exception.DiaryNotFoundException;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    private final UserService userService;

    public void write(DiaryWriteRequestDto requestDto) {
        diaryRepository.save(requestDto.toEntity());
    }

    public DiariesResponseDto findDiary(String name, String username) {

        User user = userService.getFindByUsername(username);


        List<Diary> diaries = diaryRepository.findByNameAndUser(name,user)
                .orElseThrow(DiaryNotFoundException::new);

        return DiariesResponseDto.of(diaries);
    }
}
