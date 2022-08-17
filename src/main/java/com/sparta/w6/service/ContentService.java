package com.sparta.w6.service;

import com.sparta.w6.controller.response.CommentResponseDto;
import com.sparta.w6.controller.response.ContentResponseDto;
import com.sparta.w6.controller.response.ResponseDto;
import com.sparta.w6.domain.Comment;
import com.sparta.w6.domain.Member;
import com.sparta.w6.domain.Content;
import com.sparta.w6.jwt.TokenProvider;
import com.sparta.w6.repository.CommentRepository;
import com.sparta.w6.repository.ContentRepository;
import com.sparta.w6.request.ContentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final CommentRepository commentRepository;



    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createContent(ContentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("refreshToken")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Content content = Content.builder()
                .url(requestDto.getUrl())
                .title(requestDto.getTitle())
                .text(requestDto.getText())
                .member(member)
                .build();
        contentRepository.save(content);
        return ResponseDto.success(
                ContentResponseDto.builder()
                        .id(content.getId())
                        .url(content.getUrl())
                        .title(content.getTitle())
                        .text(content.getText())
                        .author(content.getMember().getLoginId())
                        .createdAt(content.getCreatedAt())
                        .modifiedAt(content.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getContent(Long id) {
        Content content = isPresentContent(id);
        if (null == content) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByContent(content);
        List<CommentResponseDto> comments = new ArrayList<>();

        for (Comment comment : commentList) {

            comments.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getLoginId())
                            .commentText(comment.getCommentText())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        return ResponseDto.success(
                ContentResponseDto.builder()
                        .id(content.getId())
                        .url(content.getUrl())
                        .title(content.getTitle())
                        .text(content.getText())
                        .comments(comments)
                        .author(content.getMember().getLoginId())
                        .createdAt(content.getCreatedAt())
                        .modifiedAt(content.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllContent() {
        return ResponseDto.success(contentRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public ResponseDto<Content> updateContent(Long id, ContentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("refreshToken")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Content content = isPresentContent(id);
        if (null == content) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (content.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        content.update(requestDto);
        return ResponseDto.success(content);
    }

    @Transactional
    public ResponseDto<?> deleteContent(Long id, HttpServletRequest request) {
        if (null == request.getHeader("refreshToken")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Content content = isPresentContent(id);
        if (null == content) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (content.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        contentRepository.delete(content);
        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public Content isPresentContent(Long id) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        return optionalContent.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("refreshToken"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}