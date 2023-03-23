package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.diary.DiaryUpdateRequestDto;
import nero.diary.domain.diary.dto.diary.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.exception.DiaryNotFoundException;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserService userService;
    private final DiaryQueryService diaryQueryService;

    public void write(DiaryWriteRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserEmail());
        Diary diary = getDiary(requestDto, user);
        diaryRepository.save(diary);
    }

    private Diary getDiary(DiaryWriteRequestDto requestDto, User user) {
        return DiaryWriteRequestDto.builder()
                .name(requestDto.getName())
                .content(requestDto.getContent())
                .user(user)
                .build()
                .toEntity();
    }

    public void update(DiaryUpdateRequestDto requestDto) {
        diaryQueryService.findUserByEmail(requestDto.getUserEmail());

        Diary diary = diaryRepository.findById(requestDto.getId())
                .orElseThrow(DiaryNotFoundException::new);

        diary.update(requestDto.getName(), requestDto.getContent());
    }

    public void delete(Long diaryId, String userEmail){
        diaryQueryService.findUserByEmail(userEmail);

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);
        diaryRepository.delete(diary);
    }
}
