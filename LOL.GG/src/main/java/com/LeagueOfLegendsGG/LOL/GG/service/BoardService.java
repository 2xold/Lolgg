package com.LeagueOfLegendsGG.LOL.GG.service;

import com.LeagueOfLegendsGG.LOL.GG.entity.BoardEntity;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLException;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLExceptionCode;
import com.LeagueOfLegendsGG.LOL.GG.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardEntity> getPageBoard(int pageNum) {
        int pageNumLength = pageNum*10;
        List<BoardEntity> pageList = new ArrayList<>();
        for (int i = pageNumLength-10; i < pageNumLength; i++) {
            pageList.add(boardRepository.findById(i).orElse(null));
        }
        return pageList;
    }

    public void postContent(String username, String title, String content) {
        if (title == null || content == null) {
            throw new LoLException(LoLExceptionCode.IS_NOT_VALID);
        } else {
            BoardEntity boardEntity = BoardEntity.builder()
                    .username(username)
                    .title(title)
                    .content(content)
                    .build();

            boardRepository.save(boardEntity);
        }
    }
}
