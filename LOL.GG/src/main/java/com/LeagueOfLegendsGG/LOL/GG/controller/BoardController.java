package com.LeagueOfLegendsGG.LOL.GG.controller;

import com.LeagueOfLegendsGG.LOL.GG.dto.BoardDto;
import com.LeagueOfLegendsGG.LOL.GG.entity.BoardEntity;
import com.LeagueOfLegendsGG.LOL.GG.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/{pageNum}")
    public ResponseEntity<List<BoardEntity>> getPage(@PathVariable int pageNum) {
        List<BoardEntity> pageBoard = boardService.getPageBoard(pageNum);

        return ResponseEntity.ok().body(pageBoard);
    }

    @GetMapping("/post")
    public ResponseEntity<String> getAddContent() {
        return ResponseEntity.ok().body("컨텐트 완료");
    }
    @PostMapping("/post")
    public ResponseEntity<String> postAddContent(@RequestBody BoardDto dto, Authentication authentication) {
        boardService.postContent(authentication.getName(), dto.getTitle(), dto.getContent());
        return ResponseEntity.ok().body("등록 완료");
    }
}
