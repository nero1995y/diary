package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    private final UserService userService;

    @Transactional
    public void write(DiaryWriteRequestDto requestDto) {

        Diary diary = getDiary(requestDto,
                userService.getFindByUsername(requestDto.getUsername()));

        diaryRepository.save(diary);
    }

    public DiariesResponseDto findDiaryByUsername(DiarySearchCondition condition, Pageable pageable) {

        userService.findUserByEmail(condition.getUserEmail());
        Page<DiaryResponseDto> search = diaryRepository.search(condition, pageable);
        return DiariesResponseDto.of(search);
    }

    private Diary getDiary(DiaryWriteRequestDto requestDto, User user) {
        return DiaryWriteRequestDto.builder()
                .name(requestDto.getName())
                .content(requestDto.getName())
                .user(user)
                .build()
                .toEntity();
    }
}
