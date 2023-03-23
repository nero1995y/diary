package nero.diary.domain.diary.repository.custom;

import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryRepositoryCustom {

    Page <DiaryResponseDto> search(DiarySearchCondition condition, Pageable pageable);

}
