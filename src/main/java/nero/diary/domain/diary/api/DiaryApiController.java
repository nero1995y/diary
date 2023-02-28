package nero.diary.domain.diary.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.service.DiaryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping("/api/v2/diary")
    public ResponseEntity<Void> write(@RequestBody DiaryWriteRequestDto requestDto) {

        diaryService.write(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v2/diaries")
    public ResponseEntity<DiariesResponseDto> diaryListByUsername(DiarySearchCondition condition,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(diaryService.findDiaryByUsername(condition, pageable));
    }
}
