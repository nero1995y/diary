package nero.diary.domain.diary.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping("/api/v1/diary")
    public ResponseEntity<Void> write(@RequestBody DiaryWriteRequestDto requestDto) {

        log.info(">>> {}" ,requestDto.toString());

        diaryService.write(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/diaries/{diaryName}")
    public ResponseEntity<DiariesResponseDto> diaryList(@PathVariable String diaryName,
                                                        @RequestParam String username) {
        return ResponseEntity.ok(diaryService.findDiary(diaryName, username));
    }

    @GetMapping("/api/v1/diaries")
    public ResponseEntity<DiariesResponseDto> diaryListByUsername(@RequestParam String username) {
        return ResponseEntity.ok(diaryService.findDiaryByUsername(username));
    }
}
