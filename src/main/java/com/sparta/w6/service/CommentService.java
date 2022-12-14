package com.sparta.w6.service;



import com.sparta.w6.controller.response.CommentResponseDto;
import com.sparta.w6.controller.response.ResponseDto;
import com.sparta.w6.domain.Comment;
import com.sparta.w6.domain.Member;
import com.sparta.w6.domain.Content;
import com.sparta.w6.jwt.TokenProvider;
import com.sparta.w6.repository.CommentRepository;
import com.sparta.w6.request.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final TokenProvider tokenProvider;
  private final ContentService contentService;

  @Transactional
  public ResponseDto<?> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
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

    Content content = contentService.isPresentContent(requestDto.getContentId());
    if (null == content) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    Comment comment = Comment.builder()
        .member(member)
        .content(content)
        .commentText(requestDto.getCommentText())
        .build();

    commentRepository.save(comment);

    return ResponseDto.success(
        CommentResponseDto.builder()
            .id(comment.getId())
            .author(comment.getMember().getLoginId())
            .commentText(comment.getCommentText())
            .createdAt(comment.getCreatedAt())
            .modifiedAt(comment.getModifiedAt())
            .build()
    );
  }

  //            .heart(comment.getHeart())
//  @Transactional(readOnly = true)
//  public ResponseDto<?> getAllSubCommentsByComment(Long id) {
//    Comment comment = isPresentComment(id);
//
//    return ResponseDto.success(
//            CommentResponseDto.builder()
//                    .id(comment.getId())
//                    .commentText(comment.getCommentText())
//                    .author(comment.getMember().getLoginId())
////                    .heart(comment.getHeart())
//                    .createdAt(comment.getCreatedAt())
//                    .modifiedAt(comment.getModifiedAt())
//                    .build()
//    );
//  }

  @Transactional
  public ResponseDto<?> updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
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

    Content content = contentService.isPresentContent(requestDto.getContentId());
    if (null == content) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    Comment comment = isPresentComment(id);
    if (null == comment) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
    }

    if (comment.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
    }

    comment.update(requestDto);
    return ResponseDto.success(
        CommentResponseDto.builder()
            .id(comment.getId())
            .author(comment.getMember().getLoginId())
            .commentText(comment.getCommentText())
//            .heart(comment.getHeart())
            .createdAt(comment.getCreatedAt())
            .modifiedAt(comment.getModifiedAt())
            .build()
    );
  }


  @Transactional
  public ResponseDto<?> deleteComment(Long id, HttpServletRequest request) {
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

    Comment comment = isPresentComment(id);
    if (null == comment) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
    }

    if (comment.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
    }

    commentRepository.delete(comment);
    return ResponseDto.success("success");
  }

  @Transactional(readOnly = true)
  public Comment isPresentComment(Long id) {
    Optional<Comment> optionalComment = commentRepository.findById(id);
    return optionalComment.orElse(null);
  }

  @Transactional
  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("refreshToken"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }

}
