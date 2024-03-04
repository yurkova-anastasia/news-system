package ru.clevertec.news_service.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.request.news.UpdateNewsRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.controller.response.news.FullNewsResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.controller.response.user.UserResponse;
import ru.clevertec.news_service.integration.TestContainer;
import ru.clevertec.news_service.service.NewsService;
import ru.clevertec.news_service.util.Pagination;
import ru.clevertec.news_service.util.WithMockCustomUser;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class NewsControllerTest extends TestContainer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    private NewsService newsService;

    private final String path = "/api/v1/news";

    @Test
    void findByIdTest_shouldReturnNewsWithId1() throws Exception {
        NewsResponse expected = NewsResponse.builder()
                .id(1L)
                .build();
        doReturn(expected)
                .when(newsService).findById(1L);

        mockMvc.perform(get(path + "/1"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected)));
        verify(newsService).findById(1L);
    }

    @Test
    void findNewsWithComments_shouldReturnNewsWithComments() throws Exception {
        CommentResponse commentResponse = CommentResponse.builder().build();
        FullNewsResponse expected = FullNewsResponse.builder()
                .id(1L)
                .comments(List.of(commentResponse))
                .build();
        doReturn(expected)
                .when(newsService).findNewsWithComments(1L);

        mockMvc.perform(get(path + "/1/comments"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected)));
        verify(newsService).findNewsWithComments(1L);
    }

    @Test
    void findAllTest_shouldReturnNewsWithIdFrom3To4() throws Exception {
        NewsResponse newsResponse = NewsResponse.builder()
                .id(3L)
                .build();
        NewsResponse newsResponse2 = NewsResponse.builder()
                .id(4L)
                .build();
        Page<NewsResponse> news = new PageImpl<>(List.of(newsResponse, newsResponse2));
        Pagination<NewsResponse> expected = new Pagination<>(news);

        doReturn(news)
                .when(newsService).findAll(null, null, PageRequest.of(1, 2));


        mockMvc.perform(get(path + "?page=1&size=2"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected)));
        verify(newsService).findAll(null, null, PageRequest.of(1, 2));
    }

    @Nested
    @WireMockTest(httpPort = 8081)
    class WhenUserHasAccess {
        @Test
        @WithMockUser(value = "spring", authorities = "WRITE_NEWS")
        public void create_returnsCreatedAndResponse() throws Exception {
            CreateNewsRequest newsRequest = CreateNewsRequest
                    .builder()
                    .header("header")
                    .content("content")
                    .publishedBy("user")
                    .build();
            NewsResponse newsResponse = NewsResponse
                    .builder()
                    .header("header")
                    .content("content")
                    .publishedBy("user")
                    .build();

            when(newsService.create(newsRequest)).thenReturn(newsResponse);

            mockMvc.perform(MockMvcRequestBuilders.post(path)
                            .content(mapper.writeValueAsString(newsRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(status().isCreated(),
                            content().json(mapper.writeValueAsString(newsResponse)));
            verify(newsService).create(newsRequest);
        }

        @Test
        @WithMockCustomUser(username = "starostenko")
        public void update_returnsOkAndResponse(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
            mockUserService(wmRuntimeInfo);
            UpdateNewsRequest updateNewsRequest = UpdateNewsRequest
                    .builder()
                    .header("new header")
                    .build();
            NewsResponse newsResponse = NewsResponse
                    .builder()
                    .header("new header")
                    .content("Группа ученых представила результаты своего исследования в ИИ.")
                    .publishedBy("nst.yrk")
                    .build();

            when(newsService.update(1L, updateNewsRequest)).thenReturn(newsResponse);

            mockMvc.perform(MockMvcRequestBuilders.patch(path + "/{id}", 1)
                            .content(mapper.writeValueAsString(updateNewsRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(status().isOk(),
                            content().json(mapper.writeValueAsString(newsResponse)));
            verify(newsService).update(1L, updateNewsRequest);
        }

        @Test
        @WithMockCustomUser(username = "starostenko")
        public void delete_returnsNoContent(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
            mockUserService(wmRuntimeInfo);

            doNothing()
                    .when(newsService).delete(1L);

            mockMvc.perform(MockMvcRequestBuilders.delete(path + "/{id}", 1))
                    .andExpect(status().isNoContent());
            verify(newsService).delete(1L);
        }

        private void mockUserService(WireMockRuntimeInfo wmRuntimeInfo) throws JsonProcessingException {
            List<String> authorities = List.of("WRITE_COMMENTS", "WRITE_NEWS", "UPDATE_COMMENTS", "UPDATE_NEWS", "DELETE_COMMENTS", "DELETE_NEWS");
            UserResponse userResponse = UserResponse.builder()
                    .username("starostenko")
                    .role("ADMIN")
                    .authorities(authorities)
                    .build();
            WireMock wireMock = wmRuntimeInfo.getWireMock();
            wireMock.register(WireMock.post("/api/v1/users")
                    .willReturn(WireMock.okJson(mapper.writeValueAsString(userResponse))));
        }
    }

    @Nested
    @WireMockTest(httpPort = 8081)
    class WhenUserHasNoAccess {
        @Test
        @WithMockUser(value = "spring", authorities = "UPDATE_COMMENTS")
        public void create_shouldThrowAccessDeniedException() throws Exception {
            CreateNewsRequest createComment = CreateNewsRequest
                    .builder()
                    .header("header")
                    .content("content")
                    .publishedBy("user")
                    .build();

            mockMvc.perform(MockMvcRequestBuilders.post(path)
                            .content(mapper.writeValueAsString(createComment))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.timestamp").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Access Denied"))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }

        @Test
        @WithMockCustomUser(username = "starostenko", authorities = "UPDATE_COMMENTS")
        public void update_shouldThrowAccessDeniedException(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
            mockUserService(wmRuntimeInfo);
            UpdateNewsRequest updateCommentRequest = UpdateNewsRequest
                    .builder()
                    .header("new header")
                    .build();

            mockMvc.perform(MockMvcRequestBuilders.patch(path + "/{id}", 1)
                            .content(mapper.writeValueAsString(updateCommentRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.timestamp").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Access Denied"))
                    .andExpect(jsonPath("$.path").value(path + "/1"))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }

        @Test
        @WithMockCustomUser(username = "starostenko", authorities = "UPDATE_COMMENTS")
        public void delete_shouldThrowAccessDeniedException(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
            mockUserService(wmRuntimeInfo);

            mockMvc.perform(MockMvcRequestBuilders.delete(path + "/{id}", 1))
                    .andExpect(jsonPath("$.timestamp").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Access Denied"))
                    .andExpect(jsonPath("$.path").value(path + "/1"))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }

        private void mockUserService(WireMockRuntimeInfo wmRuntimeInfo) throws JsonProcessingException {
            List<String> authorities = List.of("UPDATE_COMMENTS");
            UserResponse userResponse = UserResponse.builder()
                    .username("starostenko")
                    .role("ADMIN")
                    .authorities(authorities)
                    .build();
            WireMock wireMock = wmRuntimeInfo.getWireMock();
            wireMock.register(WireMock.post("/api/v1/users")
                    .willReturn(WireMock.okJson(mapper.writeValueAsString(userResponse))));
        }
    }
}