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
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.request.comment.UpdateCommentRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.service.CommentService;
import ru.clevertec.news_service.util.Pagination;

/**
 * The CommentController class is a REST controller that provides endpoints for managing comments.
 * It is responsible for handling requests to create, read, update, and delete comments.
 *
 * @author Yurkova Anastasia
 */
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comment controller", description = "The Comment API")
public class CommentController {

    private final CommentService commentService;

    /**
     * Creates a new comment.
     *
     * @param createComment the request body containing the details of the comment to be created
     * @return a response indicating whether the comment was created successfully
     */
    @Operation(description = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('WRITE_COMMENTS')")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CreateCommentRequest createComment) {
        return new ResponseEntity<>(commentService.create(createComment), HttpStatus.CREATED);
    }

    /**
     * Returns a list of all comments.
     *
     * @param search   a search query to filter the comments by
     * @param filter   a filter query to further refine the search results
     * @param pageable the pagination information
     * @return a response containing a list of comments and pagination information
     */
    @Operation(description = "Get a list of all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping
    public ResponseEntity<Pagination<CommentResponse>> findAll(@RequestParam(required = false) String search,
                                                               @RequestParam(required = false) String filter,
                                                               Pageable pageable) {
        Page<CommentResponse> news = commentService.findAll(search, filter, pageable);
        return new ResponseEntity<>(new Pagination<>(news), HttpStatus.OK);
    }

    /**
     * Returns the details of a comment.
     *
     * @param id the ID of the comment to be retrieved
     * @return a response containing the comment details
     */
    @Operation(description = "Get the details of a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    /**
     * Updates an existing comment.
     *
     * @param id            the ID of the comment to be updated
     * @param updateComment the request body containing the updated details of the comment
     * @return a response indicating whether the comment was updated successfully
     */
    @Operation(description = "Update an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Successfully updated the comment"),
            @ApiResponse(responseCode  = "404", description  = "Comment not found"),
            @ApiResponse(responseCode  = "403", description  = "Access forbidden")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_COMMENTS')")
    public ResponseEntity<CommentResponse> update(@PathVariable(name = "id") Long id,
                                                  @RequestBody @Valid UpdateCommentRequest updateComment) {
        return new ResponseEntity<>(commentService.update(id, updateComment), HttpStatus.OK);
    }

    /**
     * Deletes an existing comment.
     *
     * @param id the ID of the comment to be deleted
     * @return a response indicating whether the comment was deleted successfully
     */
    @Operation(description = "Delete an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the comment"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "403", description = "Access forbidden")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_COMMENTS')")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
