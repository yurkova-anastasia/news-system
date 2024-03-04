package ru.clevertec.news_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.request.news.UpdateNewsRequest;
import ru.clevertec.news_service.controller.response.news.FullNewsResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.service.NewsService;
import ru.clevertec.news_service.util.Pagination;

/**
 * The NewsController class is a REST controller that provides endpoints for managing news.
 * It is responsible for handling requests to create, read, update, and delete news.
 *
 * @author Yurkova Anastasia
 */
@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News controller", description = "The News API")
public class NewsController {

    private final NewsService newsService;

    /**
     * Create a new News.
     *
     * @param createNews the details of the news to create
     * @return the created news
     */
    @Operation(description = "Create a new News")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the news"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('WRITE_NEWS')")
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid CreateNewsRequest createNews) {
        return new ResponseEntity<>(newsService.create(createNews), HttpStatus.CREATED);
    }

    /**
     * Get a list of all news.
     *
     * @param search   a search query
     * @param filter   a filter query
     * @param pageable pagination information
     * @return a page of news
     */
    @Operation(description = "Get a list of all news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping
    public ResponseEntity<Pagination<NewsResponse>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String filter,
            Pageable pageable) {
        Page<NewsResponse> news = newsService.findAll(search, filter, pageable);
        return new ResponseEntity<>(new Pagination<>(news), HttpStatus.OK);
    }

    /**
     * Get the details of news.
     *
     * @param id the ID of the news to retrieve
     * @return the news with the given ID
     */
    @Operation(description = "Get the details of news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the news"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(newsService.findById(id), HttpStatus.OK);
    }

    /**
     * Get the details of a news, including its comments.
     *
     * @param id the ID of the news to retrieve
     * @return the news with the given ID
     */
    @Operation(description = "Get the details of a news, including its comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the news with comments"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<FullNewsResponse> findNewsWithComments(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(newsService.findNewsWithComments(id), HttpStatus.OK);
    }

    /**
     * Update the details of an existing news.
     *
     * @param id         the ID of the news to update
     * @param updateNews the details of the news to update
     * @return no content
     */
    @Operation(description = "Update the details of an existing news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the news"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "403", description = "Access forbidden")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_NEWS')")
    public ResponseEntity<NewsResponse> update(@PathVariable(name = "id") Long id,
                                               @RequestBody @Valid UpdateNewsRequest updateNews) {
        return new ResponseEntity<>(newsService.update(id, updateNews), HttpStatus.OK);
    }

    /**
     * Delete an existing news.
     *
     * @param id the ID of the news to delete
     * @return no content
     */
    @Operation(description = "Delete an existing news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the news"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "403", description = "Access forbidden")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_NEWS')")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
