package finance.life.monitoring.backend.Subscription;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finance.life.monitoring.backend.Subscription.mock.SubscriptionMockData;
import finance.life.monitoring.backend.feature.subscription.controller.SubscriptionController;
import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import finance.life.monitoring.backend.feature.subscription.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubscriptionCreateTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean
    private SubscriptionService subscriptionService;

    private Subscription subscription;
//    private NewsResponseDto newsResponseDto;
//    private CreateNewsRequestDto createNewsRequestDto;
//    private ResponseDto<NewsResponseDto> responseDto;
//    private ResponseEntity<ResponseDto<NewsResponseDto>> responseEntity;

    private String url;
    private String name;
    private String amount;
    private String startDate;
    private String endDate;


    @BeforeEach
    void init() {
        subscription = SubscriptionMockData.generateSubscription();
//
//        newsResponseDto = NewsMockData.generateNewsResponseDto(news);
//
//        createNewsRequestDto = NewsMockData.generateCreateNewsRequestDto(news);
//
//        responseDto = NewsMockData.generateResponseDtoResourceCreated(newsResponseDto);
//
//        responseEntity = NewsMockData.generateResponseEntityOk(responseDto);

        url = "/subscription";

        name = "name";
        amount = "1000";
        startDate = "2025-02-14";
        endDate = "2025-02-14";
    }


    @Test
    void createSubscription_shouldReturnCreated_whenAllParamsProvided() throws Exception {

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.multipart(url)
                                        .param(name, subscription.getName())
                                        .param(amount, subscription.getAmount().toString())
                                        .param(startDate, subscription.getStartDate().toString())
                                        .param(endDate, subscription.getEndDate().toString()))
                        .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void createSubscription_shouldReturnBadRequest_whenParamsMissing() throws Exception {
        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.multipart(url)
                                        .param(name, subscription.getName())
                                        .param(amount, subscription.getAmount().toString())
                                        .param(startDate, subscription.getStartDate().toString()))
                        .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }





}
