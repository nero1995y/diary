package nero.diary.domain.diary.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.diary.DiariesResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryUpdateRequestDto;
import nero.diary.domain.diary.dto.diary.DiaryWriteRequestDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.service.DiaryQueryService;
import nero.diary.domain.diary.service.DiaryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiaryApiController {

    private final DiaryService diaryService;
    private final DiaryQueryService diaryQueryService;

    @PostMapping("/api/v2/diary")
    public ResponseEntity<Void> write(@RequestBody DiaryWriteRequestDto requestDto) {

        diaryService.write(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v2/diaries")
    public ResponseEntity<DiariesResponseDto> diaryListByUsername(DiarySearchCondition condition,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(diaryQueryService.findDiaryByUsername(condition, pageable));
    }

    @GetMapping("/api/v2/diary/{id}")
    public ResponseEntity<DiaryResponseDto> diarySingle(@PathVariable Long id,
                                                        @RequestParam String userEmail) {

        return ResponseEntity.ok(diaryQueryService.findDiary(id, userEmail));
    }

    @PatchMapping("/api/v2/diary/{id}")
    public ResponseEntity<Void> diaryUpdate(@PathVariable Long id,
                                            @RequestBody DiaryUpdateRequestDto diaryUpdateRequestDto) {

        diaryService.update(id,diaryUpdateRequestDto);
        return ResponseEntity.ok().build();
    }
}
