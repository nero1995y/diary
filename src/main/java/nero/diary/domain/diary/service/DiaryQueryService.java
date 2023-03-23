package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.diary.DiariesResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryRequestDto;
import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryUpdateRequestDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.exception.DiaryNotFoundException;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryService {
    private final DiaryRepository diaryRepository;

    private final UserService userService;

    public DiariesResponseDto findDiaryByUsername(DiarySearchCondition condition, Pageable pageable) {

        findUserByEmail(condition.getUserEmail());
        Page<DiaryResponseDto> search = diaryRepository.search(condition, pageable);
        return DiariesResponseDto.of(search);
    }

    public DiaryResponseDto findById(Long DiaryId, String userEmail) {
        findUserByEmail(userEmail);

        Diary diary = diaryRepository.findById(DiaryId)
                .orElseThrow(DiaryNotFoundException::new);

        return new DiaryResponseDto(diary);
    }

    public void findUserByEmail(String userEmail) {
        userService.findUserByEmail(userEmail);
    }
}
