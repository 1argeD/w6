package com.sparta.w6.controller;



import com.sparta.w6.controller.response.ResponseDto;
import com.sparta.w6.request.ContentRequestDto;
import com.sparta.w6.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class ContentController {

    private final ContentService contentService;

    @RequestMapping(value = "/api/content", method = RequestMethod.POST)
    public ResponseDto<?> createContent(@RequestBody ContentRequestDto requestDto,
                                     HttpServletRequest request) {
        return contentService.createContent(requestDto, request);
    }

    @RequestMapping(value = "/api/content/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }

    @RequestMapping(value = "/api/content", method = RequestMethod.GET)
    public ResponseDto<?> getAllContents() {
        return contentService.getAllContent();
    }

    @RequestMapping(value = "/api/content/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateContent(@PathVariable Long id, @RequestBody ContentRequestDto contentRequestDto,
                                     HttpServletRequest request) {
        return contentService.updateContent(id, contentRequestDto, request);
    }

    @RequestMapping(value = "/api/content/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteContent(@PathVariable Long id,
                                     HttpServletRequest request) {
        return contentService.deleteContent(id, request);
    }

}