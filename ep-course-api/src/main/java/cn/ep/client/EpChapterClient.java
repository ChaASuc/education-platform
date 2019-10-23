package cn.ep.client;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpWatchRecord;
import cn.ep.utils.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(
        name = "ep-course",
        configuration = FeignConfig.class
)
public interface EpChapterClient {

    @PostMapping("")
    ResultVO multInsert(@RequestBody List<EpChapter> chapters);


    @PostMapping("/record")
    ResultVO updateWatchRecord(@RequestBody EpWatchRecord record);
}
